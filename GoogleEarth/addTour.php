<?php
    //Get data from posted parameters
    $json = $_POST['data'];

    $tourData = json_decode($json);
        
    include("connectToDatabase.inc.php");

    $name = $tourData->name;
    $desc = $tourData->desc;
    $query = "INSERT INTO tours (name,description) VALUES ('$name','$desc')";                
    $result = mysql_query($query) or die(mysql_error());
    $id = mysql_insert_id();
    echo $id;
?>