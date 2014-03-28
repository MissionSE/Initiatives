<?php
    //Get data from posted parameters
    $tourID = $_POST['id'];
    $json = $_POST['data'];

    $directionsData = json_decode($json);
        
    include("connectToDatabase.inc.php");

    //Need to add the direction points to the table

    //Get the number of json objects
    $count = count($directionsData);
    
    echo "count = ".$count."\n";
    
    for ($i = 0; $i < $count; $i++) {
        $lat = $directionsData[$i]->lat;
        $lon = $directionsData[$i]->lon;
        $insertQuery = "INSERT INTO directionsPoints (tourId,latitude,longitude) VALUES ('$tourID','$lat','$lon')";
        $result = mysql_query($insertQuery) or die(mysql_error());
    }

    echo "result ".$result."\n";
?>