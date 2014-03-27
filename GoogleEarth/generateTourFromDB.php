<?php
include("connectToDatabase.inc.php");

$id = $_GET['id'];

 // Selects all the rows in the markers table.
 $query = "SELECT latitude,longitude,altitude,heading,tilt,rng from tourPoints WHERE tourId = $id";
$result = mysql_query($query);
 if (!$result) 
 {
  die('Invalid query: ' . mysql_error());
 }

// Creates an array of strings to hold the lines of the KML file.
$kml = array('<?xml version="1.0" encoding="UTF-8"?>');
$kml[] = '<kml xmlns="http://www.opengis.net/kml/2.2" xmlns:gx="http://www.google.com/kml/ext/2.2" xmlns:kml="http://www.opengis.net/kml/2.2" xmlns:atom="http://www.w3.org/2005/Atom">';
$kml[] = '<gx:Tour>';
$kml[] = '<name>TEST</name>';
$kml[] = '<gx:Playlist>';
$kml[] = '<gx:Wait><gx:duration>1.0</gx:duration>';
$kml[] = '</gx:Wait>';
$i = 1;
while ($row = @mysql_fetch_assoc($result)) 
{
    $kml[] = '<gx:FlyTo>';
    if (1 == $i) {
    $kml[] = '<gx:duration>5.0</gx:duration>';
    $kml[] = '<gx:flyToMode>smooth</gx:flyToMode>';
    }
    else {
    $kml[] = '<gx:duration>1.5</gx:duration>';
    $kml[] = '<gx:flyToMode>smooth</gx:flyToMode>';        
    }
    $kml[] = '<LookAt>';
    $kml[] = '<gx:horizFov>59.9</gx:horizFov>';
    $kml[] = '<gx:ViewerOptions>';
    $kml[] = '<gx:option enabled="0" name="historicalimagery"></gx:option>';
    $kml[] = '<gx:option enabled="0" name="sunlight"></gx:option>';
    $kml[] = '<gx:option enabled="0" name="streetview"></gx:option>';
    $kml[] = '</gx:ViewerOptions>';
    $kml[] = '<longitude>'.$row['longitude'].'</longitude>';
    $kml[] = '<latitude>'.$row['latitude'].'</latitude>';
    $kml[] = '<altitude>'.$row['altitude'].'</altitude>';
    $kml[] = '<heading>'.$row['heading'].'</heading>';
    $kml[] = '<tilt>'.$row['tilt'].'</tilt>';
    $kml[] = '<range>'.$row['rng'].'</range>';
    $kml[] = '<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>';
    $kml[] = '</LookAt>';
    $kml[] = '</gx:FlyTo>';
    $kml[] = '<gx:Wait><gx:duration>0.75</gx:duration>';
    $kml[] = '</gx:Wait>';
    $i = $i + 1;
}

// End XML file
$kml[] = '</gx:Playlist>';
$kml[] = '</gx:Tour>';
$kml[] = '</kml>';
$kmlOutput = join("\n", $kml);
header('Content-type: application/vnd.google-earth.kml+xml');
echo $kmlOutput;
?>