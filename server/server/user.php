<?php

require_once("dal.php");

function login($username, $password)
{
	DAL::connect();
	$user = DAL::login($username, $password);
	DAL::disconnect();
	return array ("res" => $user);
}

function update_user_account($username, $password)
{
	DAL::connect();
	$success = DAL::register_user($username, $password);
	DAL::disconnect();
	
	if ($success)
		return array ("res" => "TRUE");
	array ("res" => "FALSE");
}

function update_user_profile($username, $name, $street_address, $city, 
							 $state, $zipcode, $website="", $email, $about)
{
	DAL::connect();
	$success = DAL::update_user_profile($username, $name, $street_address, $city, 
							 			$state, $zipcode, $website, $email, $about);
							 
	DAL::disconnect();
	
	if ($success)
		return array ("res" => "TRUE");
	array ("res" => "FALSE");
}

function get_user_profile($username)
{
	DAL::connect();
	$user = DAL::get_user_profile($username);
	DAL::disconnect();
	
	return $user;
}

?>