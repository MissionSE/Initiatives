<?php

header("Content-Type: application/json");

$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');

mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
    
$query = "SELECT targetTN,weapon,weaponTN,status from engagements";

$result = mysql_query($query) or die('Sorry, could not find any engagements');

$json = array();

while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $json[] = $row;
}

echo json_encode($json);
?>