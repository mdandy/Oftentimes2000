<?php

require_once("dal.php");

function login($username, $password)
{
	DAL::connect();
	$user = DAL::login($username, $password);
	DAL::disconnect();
	
	$res = array ("res" => $user);
	return json_encode($res);
}

function update_user_account($username, $password)
{
	DAL::connect();
	$success = DAL::register_user($username, $password);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function update_user_profile($username, $name, $street_address, $city, 
							 $state, $zipcode, $website="", $email, $about)
{
	DAL::connect();
	$success = DAL::update_user_profile($username, $name, $street_address, $city, 
							 			$state, $zipcode, $website, $email, $about);
							 
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function get_user_profile($username)
{
	DAL::connect();
	$user = DAL::get_user_profile($username);
	DAL::disconnect();
	
	return json_encode($user);
}

?>