<?php

$incrementVar = $_POST['incrementVar'];

$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');

mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
    
$selectQuery = "SELECT trackNum,trackLat,trackLon,trackAlt,trackCourse,trackSpeed from tracks";

$selectResult = mysql_query($selectQuery) or die('Sorry, could not find any tracks');

$delta = 0.1;

while($row = mysql_fetch_array($selectResult, MYSQL_ASSOC)) {

    $trackNum = $row['trackNum'];

    $trackLat = $row['trackLat'];

    $trackLon = $row['trackLon'];

    $trackAlt = $row['trackAlt'];
    
    $trackCourse = $row['trackCourse'];
    
    $trackSpeed = $row['trackSpeed'];
    
    //Flip the track course into opposite direction
    //if ("true" == $incrementVar) {
    //    if ($trackCourse < 180) {
    //        $trackCourse = $trackCourse + 180;    
    //    }
    //    else {
    //        $trackCourse = $trackCourse - 180;
    //    }
    //}
    
    //Determine R in meters based on speed
    //R = xxx mi/hr x 1609.333 m/mi x 1/1800 hr/2sec
    $R = $trackSpeed * 1609.333 * (1/1800);
    
    //Calculate new position based on course and speed
    $dx = $R*sin(deg2rad($trackCourse));
    $dy = $R*cos(deg2rad($trackCourse));

    $deltaLon = $dx/(111320*cos(deg2rad($trackLat)));
    $deltaLat = $dy/110540;    

    $newLon = $trackLon + $deltaLon;

    $newLat = $trackLat + $deltaLat;
    
    $updateQuery =  "UPDATE tracks SET trackLat='$newLat', trackLon = '$newLon', " .
                    "trackAlt = '$trackAlt', trackCourse = '$trackCourse' WHERE trackNum = $trackNum";

    $updateResult = mysql_query($updateQuery) or die(mysql_error());
}
?>