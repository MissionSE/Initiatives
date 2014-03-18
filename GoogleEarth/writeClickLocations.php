<?php
    $lat = $_POST['lat'];
    
    $lon = $_POST['lon'];
    
    $con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');
    
    mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
        
    $query = "INSERT INTO clickLocations (latitude,longitude) VALUES ('$lat','$lon')";
    
    $result = mysql_query($query) or die(mysql_error());

    echo $result;
?>