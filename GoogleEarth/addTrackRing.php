<?php
    $lat = $_POST['lat'];
    
    $lon = $_POST['lon'];
    
    $radius = $_POST['radius'];
    
    $num = $_POST['num'];
    
    $id = $_POST['id'];
    
    $plat = $_POST['plat'];
    
    $alt = $_POST['alt'];
    
    $cat = $_POST['cat'];
    
    $spd = $_POST['spd'];
    
    //$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');
    //
    //mysql_select_db("earth", $con) or die('Sorry, could not connect to database');
        
    include("connectToDatabase.inc.php");

    $query =    "INSERT INTO trackRings (latitude,longitude,radius,numTracks,identity,platform,altitude,category,speed)".
                "VALUES ('$lat','$lon','$radius','$num','$id','$plat','$alt','$cat','$spd')";
    
    $result = mysql_query($query) or die(mysql_error());
    
    echo $result;
?>