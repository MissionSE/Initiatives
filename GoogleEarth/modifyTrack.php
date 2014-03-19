<?php

$num = $_POST['num'];

$id = $_POST['id'];

$plat = $_POST['platform'];

$lat = $_POST['lat'];

$lon = $_POST['lon'];

$alt = $_POST['alt'];

$cat = $_POST['cat'];

$crs = $_POST['crs'];

$spd = $_POST['spd'];

$tag = $_POST['tag'];

//$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');
//
//mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
    
include("connectToDatabase.inc.php");

$query =    "UPDATE tracks SET trackLat='$lat', trackLon = '$lon', trackAlt = '$alt', trackIdentity = '$id', trackPlatform = '$plat', trackCategory = '$cat', trackCourse = '$crs', trackSpeed = '$spd', trackTag = '$tag' WHERE trackNum = '$num'";

$result = mysql_query($query) or die(mysql_error());

echo $result;

?>