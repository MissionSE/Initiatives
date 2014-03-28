<?php

$id = $_GET['id'];

header("Content-Type: application/json");

include("connectToDatabase.inc.php");

$query = "SELECT id from tours WHERE directionsRequested = 1";

$result = mysql_query($query) or die('Sorry, could not find any tours');

$json = array();

while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $json[] = $row;
}

echo json_encode($json);
?>