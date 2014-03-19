<?php

$command = $_POST['command'];

$data1 = $_POST['data1'];

$data2 = $_POST['data2'];

$data3 = $_POST['data3'];

//$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');
//
//mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
    
include("connectToDatabase.inc.php");

$query = "INSERT INTO commands (text,data1,data2,data3) VALUES ('$command','$data1','$data2','$data3')";

$result = mysql_query($query) or die(mysql_error());

echo $result;
?>