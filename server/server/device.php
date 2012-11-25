<?php

require_once("dal.php");

function register_device($gcm_id)
{
	DAL::connect();
	$success = DAL::register_device($gcm_id);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function unregister_device($gcm_id)
{
	DAL::connect();
	$success = DAL::unregister_device($gcm_id);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function is_device_registered($gcm_id)
{
	DAL::connect();
	$success = DAL::is_device_registered($gcm_id);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function update_device_location($gcm_id, $latitude, $longitude)
{
	DAL::connect();
	$success = DAL::update_device_location($gcm_id, $latitude, $longitude);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}


?>