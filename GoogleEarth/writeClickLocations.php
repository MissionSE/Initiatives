<?php
    $lat = $_POST['lat'];
    
    $lon = $_POST['lon'];

    $alt = $_POST['alt'];
    
    $hdg = $_POST['hdg'];
    
    $tilt = $_POST['tilt'];
    
    $rng = $_POST['rng'];
    
    include("connectToDatabase.inc.php");

    $query = "INSERT INTO clickLocations (latitude,longitude,altitude,heading,tilt,rng) VALUES ('$lat','$lon','$alt','$hdg','$tilt','$rng')";
    
    $result = mysql_query($query) or die(mysql_error());

    echo $result;
?>