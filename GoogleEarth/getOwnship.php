<?php

header("Content-Type: application/json");

$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');

mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
    
$query = "SELECT trackNum,trackLat,trackLon,trackAlt from tracks";

$result = mysql_query($query) or die('Sorry, could not find any tracks');

while($row = mysql_fetch_array($result, MYSQL_ASSOC)) {

    $trackNum = $row['trackNum'];

    $trackLat = $row['trackLat'];

    $trackLon = $row['trackLon'];

    $trackAlt = $row['trackAlt'];

    $trackData = '{ "trackLat":"'.$trackLat.'", "trackLon":"'.$trackLon.'"}';
    
    //Only return first track for now...
    break;
}

echo $trackData;
?>