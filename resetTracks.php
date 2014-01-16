<?php

$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');

mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
//1	39.980261	-74.90117
//2	41.480261	-74.90117
//3	39.980261	-73.40117
//4	38.480261	-74.90117
//5	39.980261	-76.40117
//6	42.980261	-74.90117
//7	39.980261	-71.90117
//8	36.980261	-74.90117
//9	39.980261	-77.90117
//10	42.230261	-74.90117
//11	39.980261	-72.65117
//12	37.730261	-74.90117
//13	39.980261	-77.15117
//14	43.730261	-74.90117
//15	39.980261	-71.15117
//16	36.230261	-74.90117
//17	39.980261	-78.65117    

$trackAlt = 0;

$trackLat = 39.980261;
$trackLon = -74.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 1";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 41.480261;
$trackLon = -74.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 2";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 39.980261;
$trackLon = -73.40117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 3";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 38.480261;
$trackLon = -74.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 4";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 39.980261;
$trackLon = -76.40117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 5";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 42.980261;
$trackLon = -74.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 6";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 39.980261;
$trackLon = -71.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 7";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 36.980261;
$trackLon = -74.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 8";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 39.980261;
$trackLon = -77.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 9";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 42.230261;
$trackLon = -74.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 10";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 39.980261;
$trackLon = -72.65117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 11";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 37.730261;
$trackLon = -74.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 12";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 39.980261;
$trackLon = -77.15117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 13";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 43.730261;
$trackLon = -74.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 14";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 39.980261;
$trackLon = -71.15117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 15";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 36.230261;
$trackLon = -74.90117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 16";

$updateResult = mysql_query($updateQuery) or die(mysql_error());

$trackLat = 39.980261;
$trackLon = -78.65117;
$updateQuery =  "UPDATE tracks SET trackLat='$trackLat', trackLon = '$trackLon', " .
                "trackAlt = $trackAlt WHERE trackNum = 17";

$updateResult = mysql_query($updateQuery) or die(mysql_error());


?>