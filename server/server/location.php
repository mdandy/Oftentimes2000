<?php

require_once("dal.php");
require_once("notification.php");

function ping_server($gcm_id, $latitude, $longitude)
{
	DAL::connect();
	$success = DAL::update_device_location($gcm_id, $latitude, $longitude);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function get_announcement_by_location($latitude, $longitude)
{
	$radius = 5;
	$r = convert_miles_to_degrees($radius);
 	$add = 1 / cos((float)($r * 2)); 
	$r = (int)($r * 1000000);
  
	// TODO: geopoint calculation
	$from_latitude = $latitude - $r;
	$to_latitude = $latitude + $r;
	$from_longitude = $longitude - ($add * ($r*2));
	$to_longitude = $longitude + ($add * ($r*2));
	
	DAL::connect();
	$announcements = DAL::get_announcement_by_location($from_latitude, $to_latitude, $from_longitude, $to_longitude);
	DAL::disconnect();

	if ($announcements != NULL)
		return json_encode(array ("res" => "TRUE", "data" => $announcements));
	return json_encode(array ("res" => "FALSE"));
}

function convert_miles_to_degrees($radius) 
{
	$r = $radius / 69.047;
	return $r;
}

?>
