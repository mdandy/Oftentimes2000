<?php

require_once("dal.php");
require_once("notification.php");
require_once("location.php");

function upsert_advertisement($id=-1, $username, $title, $highlights, $fine_print="", 
							  $street_address, $city, $state, $zipcode, $radius,
							  $regular_price=-1, $promotional_price=-1, 
							  $from, $to, $url="", $category)
{
	$geopoint = get_geopoint($street_address, $city, $state, $zipcode);
	$latitude = $geopoint["latitude"];
	$longitude = $geopoint["longitude"];
	$type = 1;
	
	return upsert_announcement($id, $username, $title, $type, $highlights, $fine_print, 
							   $street_address, $city, $state, $zipcode, $radius,
							   $latitude, $longitude,
							   $regular_price, $promotional_price, 
							   $from, $to, $url, $category);
}

function upsert_psa($id=-1, $username, $title, $highlights, 
					$street_address, $city, $state, $zipcode, $radius,
					$from, $to, $url="", $category)
{
	$geopoint = get_geopoint($street_address, $city, $state, $zipcode);
	$latitude = $geopoint["latitude"];
	$longitude = $geopoint["longitude"];
	$type = 2;
	
	return upsert_announcement($id, $username, $title, $type, $highlights, $fine_print, 
							   $street_address, $city, $state, $zipcode, $radius,
							   $latitude, $longitude,
							   $regular_price, $promotional_price, 
							   $from, $to, $url, $category);
}

function upsert_event($id=-1, $username, $title, $highlights,
					  $street_address, $city, $state, $zipcode, $radius,
					  $from, $to, $url="", $category)
{
	$geopoint = get_geopoint($street_address, $city, $state, $zipcode);
	$latitude = $geopoint["latitude"];
	$longitude = $geopoint["longitude"];
	$type = 3;
	
	return upsert_announcement($id, $username, $title, $type, $highlights, $fine_print, 
							   $street_address, $city, $state, $zipcode, $radius,
							   $latitude, $longitude,
							   $regular_price, $promotional_price, 
							   $from, $to, $url, $category);
}

function get_type($type)
{
	if (strcmp($type, "advertisement") == 0)
		return 1;
	
	if (strcmp($type, "psa") == 0)
		return 2;
	
	if (strcmp($type, "event") == 0)
		return 3;
	
	return -1;	
}

function get_type_string($type)
{
	switch ($type)
	{
		case 1 : return "advertisement";
		case 2 : return "psa";
		case 3 : return "event";
	}

	return type;	
}

function get_geopoint($street_address, $city, $state, $zipcode)
{
	$latitude = 0;
  $longitude = 0;

  $address = urlencode($street_address . " " . $city . ", " .$state . " " . $zipcode);

 

  $json = file_get_contents("http://maps.google.com/maps/api/geocode/json?address=$address&sensor=false");
  $json = json_decode($json);

  

  $latitude = $json->{'results'}[0]->{'geometry'}->{'location'}->{'lat'};
  $longitude = $json->{'results'}[0]->{'geometry'}->{'location'}->{'lng'};

  $latitude = (int)($latitude * 1000000);
  $longitude = (int)($longitude * 1000000);
	
	return array("latitude" => $latitude, "longitude" => $longitude);
}

function upsert_announcement($id=-1, $username, $title, $type, $highlights, $fine_print="", 
							 $street_address, $city, $state, $zipcode, $radius,
							 $latitude, $longitude,
							 $regular_price=-1, $promotional_price=-1, 
							 $from, $to, $url="", $category)
{
	DAL::connect();
	if ($id == -1)
	{

		$id = DAL::insert_announcement($username, $title, $type, $highlights, $fine_print, 
										    $street_address, $city, $state, $zipcode, $radius,
										    $latitude, $longitude,
										    $regular_price, $promotional_price, 
										    $from, $to, $url, $category);
	}
	else
	{
		$id = DAL::update_announcement($id, $username, $title, $type, $highlights, $fine_print, 
										    $street_address, $city, $state, $zipcode, $radius,
										    $latitude, $longitude,
										    $regular_price, $promotional_price, 
										    $from, $to, $url, $category);
	}
	
	if($id != -1)
	{
		$type_string = get_type_string($type);
		$idsOfType = DAL::get_subscriptions($type_string);
		$gcmIDS = array();
		for($i = 0; $i < count($idsOfType); $i++)
		{
			array_push($gcmIDS, $idsOfType[$i]["gcm_id"]);
		}
		
		$deviceData = get_device_by_location($latitude, $longitude);
		$deviceIDS = array();
		for($i = 0; $i < count($deviceData); $i++)
		{
			array_push($deviceIDS, $deviceData[$i]["gcm_id"]);
		}

		$intersection = array_intersect($gcmIDS, $deviceIDS);
		
		$announcement = DAL::get_announcement_by_id($id);
		$message = $announcement[0];
		sendNotification($intersection , $message);
	}

	DAL::disconnect();

	$res = array ("res" => "FALSE");
	if ($id != -1)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function get_device_by_location($latitude, $longitude)
{
	$radius = 5;
	$r = convert_miles_to_degrees($radius);
 	$add = 1 / cos((float)($r * 2)); 
	$r = (int)($r * 1000000);
  
	$from_latitude = (int)($latitude - $r);
	$to_latitude = (int)($latitude + $r);
	$from_longitude = (int)($longitude - ($add * ($r*2)));
	$to_longitude = (int)($longitude + ($add * ($r*2)));
	
	//$announcements = DAL::get_device_by_location($from_latitude, $to_latitude, $from_longitude, $to_longitude);
	$announcements = DAL::get_device_by_location(0,0,0,0);
	return $announcements;
}

function delete_announcement($id)
{
	DAL::connect();
	$success = DAL::delete_announcement($id);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function get_announcement($username)
{
	DAL::connect();
	$annoucements = DAL::get_announcement($username);
	DAL::disconnect();
	
	if ($annoucements != NULL)
		return json_encode(array ("res" => "TRUE", "data" => $annoucements));
	return json_encode(array ("res" => "FALSE"));
}

function get_announcement_by_type($username="", $type)
{
	DAL::connect();
	$type = get_type($type);
	$annoucements = DAL::get_announcement_by_type($username, $type);
	DAL::disconnect();
	
	if ($annoucements != NULL)
		return json_encode(array ("res" => "TRUE", "data" => $annoucements));
	return json_encode(array ("res" => "FALSE"));
}

function get_announcement_by_id($id)
{
	DAL::connect();
	$annoucement = DAL::get_announcement_by_id($id);
	DAL::disconnect();
	
	if ($annoucement != NULL)
		return json_encode(array ("res" => "TRUE", "data" => $annoucement[0]));
	return json_encode(array ("res" => "FALSE"));
}

?>
