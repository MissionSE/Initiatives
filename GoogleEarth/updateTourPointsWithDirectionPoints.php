<?php
    //Get data from posted parameters
    $tourID = $_POST['id'];
    $json = $_POST['data'];

    $directionsData = json_decode($json);
        
    include("connectToDatabase.inc.php");

    //Need to get the altitude,heading,tilt,range from the current tour points
    $origAltitude = 0;
    $origHeading = 0;
    $origTilt = 0;
    $origRange = 0;
    $query = "SELECT * from tourPoints WHERE tourId = $tourID";
    $result = mysql_query($query) or die(mysql_error);
    while ($row = @mysql_fetch_assoc($result)) 
    {
        $origAltitude = $row['altitude'];
        $origHeading = $row['heading'];
        $origTilt = $row['tilt'];
        $origRange = $row['rng'];
    }
    
    echo "origAltitude ".$origAltitude." origHeading ".$origHeading." origTilt ".$origTilt." origRange ".$origRange."\n";
    //Need to delete the current tour points
    $query = "DELETE from tourPoints WHERE tourId = $tourID";
    $result = mysql_query($query) or die(mysql_error);
    
    //Need to add the direction points to the tour

    //Get the number of json objects
    $count = count($directionsData);
    
    echo "count = ".$count."\n";
    
    for ($i = 0; $i < $count; $i++) {
        $lat = $directionsData[$i]->lat;
        $lon = $directionsData[$i]->lon;
        $alt = $origAltitude;
        $hdg = $origHeading;
        $tilt = $origTilt;
        $rng = $origRange;
        $insertQuery = "INSERT INTO tourPoints (tourId,latitude,longitude,altitude,heading,tilt,rng) VALUES ('$tourID','$lat','$lon','$alt','$hdg','$tilt','$rng')";
        //$insertQuery = "INSERT INTO testPoints (tourId,latitude,longitude) VALUES ('$tourID','$lat','$lon')";
        $result = mysql_query($insertQuery) or die(mysql_error());
    }

    echo "result ".$result."\n";
?>