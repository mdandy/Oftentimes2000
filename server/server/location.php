<?php

require_once("dal.php");

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