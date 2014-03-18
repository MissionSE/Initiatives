<?php

header("Content-Type: application/json");

$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');

mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
    
$query = "SELECT trackNum,trackCategory,trackIdentity,trackPlatform,trackLat,trackLon,trackAlt,trackCourse,trackSpeed,trackTag from tracks WHERE trackIdentity = 'hostile'";

$result = mysql_query($query) or die('Sorry, could not find any tracks');

$json = array();

while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $json[] = $row;
}

echo json_encode($json);
?>