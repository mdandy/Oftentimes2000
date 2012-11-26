<?php

require_once("dal.php");

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

function get_announcement_by_location($latitude, $longitude, $radius=0)
{
	DAL::connect();

	// TODO: geopoint calculation
	$from_latitude = $latitude;
	$to_latitude = $latitude;
	$from_longitude = $longitude;
	$to_longitude = $longitude;

	$announcements = DAL::get_announcement_by_location($from_latitude, $to_latitude, $from_longitude, $to_longitude);
	DAL::disconnect();

	if ($announcements != NULL)
		return json_encode(array ("res" => "TRUE", "data" => $announcements));
	return json_encode(array ("res" => "FALSE"));
}

?>