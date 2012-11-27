<?php

require_once("dal.php");

function ping_server($gcm_id, $latitude, $longitude)
{
	DAL::connect();
	$success = DAL::update_device_location($gcm_id, $latitude, $longitude);
	DAL::disconnect();
	
	$res = get_announcement_by_location($latitude, $longitude);

	if($res)
	{
		$message = json_decode($res);
		sendNotification(GCM_API_KEY, array($gcm_id) , array('message' => $message));
	}
	return $res;
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

function sendNotification( $apiKey, $registrationIdsArray, $messageData )
{   
    $headers = array("Content-Type:" . "application/json", "Authorization:" . "key=" . $apiKey);
    $data = array(
        'data' => $messageData,
        'registration_ids' => $registrationIdsArray
    );
 
    $ch = curl_init();
 
    curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers ); 
    curl_setopt( $ch, CURLOPT_URL, "https://android.googleapis.com/gcm/send" );
    curl_setopt( $ch, CURLOPT_SSL_VERIFYHOST, 0 );
    curl_setopt( $ch, CURLOPT_SSL_VERIFYPEER, 0 );
    curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
    curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode($data) );
 
    $response = curl_exec($ch);
    curl_close($ch);
 	echo $response;
    return $response;
}

?>
