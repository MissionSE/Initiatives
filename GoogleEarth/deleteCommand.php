<?php

$id = $_POST['id'];

$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');

mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
    
$query = "DELETE from commands where id = '$id'";

$result = mysql_query($query) or die(mysql_error());

echo $result;
?>