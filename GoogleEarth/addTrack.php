<?php

$id = $_POST['id'];

$plat = $_POST['platform'];

$lat = $_POST['lat'];

$lon = $_POST['lon'];

$alt = $_POST['alt'];

$cat = $_POST['cat'];

$crs = $_POST['crs'];

$spd = $_POST['spd'];

$tag = $_POST['tag'];

$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');

mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
    
$query =    "INSERT INTO tracks (trackIdentity,trackPlatform,trackLat,trackLon,trackAlt,trackCategory,trackCourse,trackSpeed,trackTag)".
            "VALUES ('$id','$plat','$lat','$lon','$alt','$cat','$crs','$spd','$tag')";

$result = mysql_query($query) or die(mysql_error());

echo $result;
?>