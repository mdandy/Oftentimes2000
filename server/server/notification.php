<?php

require_once("dbconfig.php");

function sendNotification($registrationIdsArray, $messageData )
{   
	$apiKey = GCM_API_KEY;
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