<?php

require_once("dal.php");

function update_subscription($gcm_id, $subscription)
{
	DAL::connect();
	$success = DAL::upsert_subscription($gcm_id, $subscription);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function delete_subscription($gcm_id, $subscription)
{
	DAL::connect();
	$success = DAL::delete_subscription($gcm_id, $subscription);
	DAL::disconnect();
	
	$res = array ("res" => "FALSE");
	if ($success)
		$res = array ("res" => "TRUE");
	return json_encode($res);
}

function get_subscriptions_by_id($gcm_id)
{
	DAL::connect();
	$subscriptions = DAL::get_subscriptions_by_id($gcm_id);
	DAL::disconnect();
	
	if ($subscriptions != NULL)
		return json_encode(array ("res" => "TRUE", "data" => $subscriptions));
	return json_encode(array ("res" => "FALSE"));
}

function get_subscriptions($subscription)
{
	DAL::connect();
	$subs = DAL::get_subscriptions($subscription);
	DAL::disconnect();
	
	if ($subs != NULL)
		return json_encode(array ("res" => "TRUE", "data" => $subs));
	return json_encode(array ("res" => "FALSE"));
}

?>