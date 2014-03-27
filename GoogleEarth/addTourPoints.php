<?php
    //Get data from posted parameters
    $tourID = $_POST['id'];
    $json = $_POST['data'];

    $tourData = json_decode($json);
        
    include("connectToDatabase.inc.php");

    //Get the number of json objects
    $count = count($tourData);
    
    echo "count = ".$count."\n";
    
    for ($i = 0; $i < $count; $i++) {
        $lat = $tourData[$i]->lat;
        $lon = $tourData[$i]->lon;
        $alt = $tourData[$i]->alt;
        $hdg = $tourData[$i]->hdg;
        $tilt = $tourData[$i]->tilt;
        $rng = $tourData[$i]->rng;
        $insertQuery = "INSERT INTO tourPoints (tourId,latitude,longitude,altitude,heading,tilt,rng) VALUES ('$tourID','$lat','$lon','$alt','$hdg','$tilt','$rng')";                
        $result = mysql_query($insertQuery) or die(mysql_error());
    }
    
    $id = mysql_insert_id();
    echo $id;
?>