<?php

header("Content-Type: application/json");

include("connectToDatabase.inc.php");

$query = "SELECT id,latitude,longitude,altitude,heading,tilt,rng from clickLocations";

$result = mysql_query($query) or die('Sorry, could not find any clickLocations');

$json = array();

while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $json[] = $row;
}

echo json_encode($json);
?>