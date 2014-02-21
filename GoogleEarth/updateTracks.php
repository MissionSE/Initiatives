<?php

$incrementVar = $_POST['incrementVar'];

$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');

mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
    
$selectQuery = "SELECT trackNum,trackLat,trackLon,trackAlt from tracks";

$selectResult = mysql_query($selectQuery) or die('Sorry, could not find any tracks');

$delta = 0.1;

while($row = mysql_fetch_array($selectResult, MYSQL_ASSOC)) {

    $trackNum = $row['trackNum'];

    $trackLat = $row['trackLat'];

    $trackLon = $row['trackLon'];

    $trackAlt = $row['trackAlt'];
    
    if ("true" == $incrementVar) {
        //Increment the lat and lon of each track
        $trackLat += $delta;
    }
    else {
        $trackLat -= $delta;
    }
    
    $updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                    "trackAlt = $trackAlt WHERE trackNum = $trackNum";

    $updateResult = mysql_query($updateQuery) or die(mysql_error());
    
    //echo "trackNum:$trackNum trackLat:$trackLat trackLon:$trackLon increment:$incrementVar<br>\n";
}
?>