<?php
include("connectToDatabase.inc.php");

 // Selects all the rows in the markers table.
 $query = 'SELECT * FROM markers WHERE 1';
 $result = mysql_query($query);
 if (!$result) 
 {
  die('Invalid query: ' . mysql_error());
 }

// Creates an array of strings to hold the lines of the KML file.
$kml = array('<?xml version="1.0" encoding="UTF-8"?>');
$kml[] = '<kml xmlns="http://earth.google.com/kml/2.1">';
$kml[] = ' <Folder>';
$kml[] = ' <Placemark id="linestring1">';
$kml[] = ' <name>PSU Bar Tour</name>';
$kml[] = ' <description>Fraternity bar tour</description>';
$kml[] = ' <LineString>';
$kml[] = ' <extrude>3</extrude>';
$kml[] = ' <tessellate>1</tessellate>';
$kml[] = ' <altitudeMode>clampToGround</altitudeMode>';
$kml[] = ' <coordinates>'; 

// Iterates through the rows, printing a node for each row.
while ($row = @mysql_fetch_assoc($result)) 
{
  $kml[] = $row['lng'] . ','  . $row['lat'].' ';
} 

// End XML file
$kml[] = ' </coordinates>'; 
$kml[] = ' </LineString>'; 
$kml[] = ' </Placemark>';
$kml[] = ' </Folder>';
$kml[] = '</kml>';
$kmlOutput = join("\n", $kml);
header('Content-type: application/vnd.google-earth.kml+xml');
echo $kmlOutput;
?>