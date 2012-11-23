<?php

require_once("dal.php");

function upsert_advertisement($username, $title, $highlights, $fine_print="", 
							  $street_address, $city, $state, $zipcode, $radius,
							  $regular_price=-1, $promotional_price=-1, 
							  $from, $to, $url="", $category)
{
	$geopoint = get_geopoint();
	$latitude = $geopoint["latitude"];
	$longitude = $geopoint["longitude"];
	$type = 1;
	
	return upsert_announcement($username, $title, $type, $highlights, $fine_print="", 
							   $street_address, $city, $state, $zipcode, $radius,
							   $latitude, $longitude,
							   $regular_price=-1, $promotional_price=-1, 
							   $from, $to, $url="", $category);
}

function upsert_psa($username, $title, $highlights, 
					$street_address, $city, $state, $zipcode, $radius,
					$from, $to, $url="", $category)
{
	$geopoint = get_geopoint();
	$latitude = $geopoint["latitude"];
	$longitude = $geopoint["longitude"];
	$type = 2;
	
	return upsert_announcement($username, $title, $type, $highlights, $fine_print="", 
							   $street_address, $city, $state, $zipcode, $radius,
							   $latitude, $longitude,
							   $regular_price=-1, $promotional_price=-1, 
							   $from, $to, $url="", $category);
}

function upsert_event($username, $title, $highlights,
					  $street_address, $city, $state, $zipcode, $radius,
					  $from, $to, $url="", $category)
{
	$geopoint = get_geopoint();
	$latitude = $geopoint["latitude"];
	$longitude = $geopoint["longitude"];
	$type = 3;
	
	return upsert_announcement($username, $title, $type, $highlights, $fine_print="", 
							   $street_address, $city, $state, $zipcode, $radius,
							   $latitude, $longitude,
							   $regular_price=-1, $promotional_price=-1, 
							   $from, $to, $url="", $category);
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

function get_geopoint()
{
	$latitude = 0;
	$longitude = 0;
	
	return array("latitude" => $latitude, "longitude" => $longitude);
}

function upsert_announcement($username, $title, $type, $highlights, $fine_print="", 
							 $street_address, $city, $state, $zipcode, $radius,
							 $latitude, $longitude,
							 $regular_price=-1, $promotional_price=-1, 
							 $from, $to, $url="", $category)
{
	DAL::connect();
	$sucess = DAL::upsert_announcement($username, $title, $type, $highlights, $fine_print="", 
							 		   $street_address, $city, $state, $zipcode, $radius,
							 		   $latitude, $longitude,
							 		   $regular_price=-1, $promotional_price=-1, 
							 		   $from, $to, $url="", $category);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
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
	
	return json_encode($annoucements);
}

function get_announcement_by_type($username, $type)
{
	DAL::connect();
	$type = get_type($type);
	$annoucements = DAL::get_announcement_by_type($username, $type);
	DAL::disconnect();
	
	return json_encode($annoucements);
}

?>