<?php
//Get data from posted parameters
$tourID = $_GET['id'];

include("connectToDatabase.inc.php");

// Selects all the rows in the markers table.
$query = "SELECT * FROM directionsPoints WHERE tourId = '$tourID'";
 
$result = mysql_query($query);
if (!$result) 
{
 die('Invalid query: ' . mysql_error());
}

// Creates an array of strings to hold the lines of the KML file.
$kml = array('<?xml version="1.0" encoding="UTF-8"?>');
$kml[] = '<kml xmlns="http://www.opengis.net/kml/2.2" xmlns:gx="http://www.google.com/kml/ext/2.2" xmlns:kml="http://www.opengis.net/kml/2.2" xmlns:atom="http://www.w3.org/2005/Atom">';
$kml[] = '<Folder>';
$kml[] = '<name>Line Styling</name>';
$kml[] = '<Style id="directions">';
$kml[] = '<LineStyle>';
$kml[] = '<color>ff235523</color>';
$kml[] = '<gx:physicalWidth>75</gx:physicalWidth>';
$kml[] = '<gx:outerColor>ff55ff55</gx:outerColor>';
$kml[] = '<gx:outerWidth>0.25</gx:outerWidth>';
$kml[] = '</LineStyle>';
$kml[] = '</Style>';
$kml[] = '<Placemark>';
$kml[] = '<styleUrl>#directions</styleUrl>';
$kml[] = '<LineString>';
$kml[] = '<extrude>3</extrude>';
$kml[] = '<tessellate>1</tessellate>';
$kml[] = '<altitudeMode>clampToGround</altitudeMode>';
$kml[] = '<coordinates>'; 

// Iterates through the rows, printing a node for each row.
while ($row = @mysql_fetch_assoc($result)) 
{
  $kml[] = $row['longitude'] . ','  . $row['latitude'].' ';
} 

// End XML file
$kml[] = '</coordinates>'; 
$kml[] = '</LineString>';
$kml[] = '</Placemark>';
$kml[] = '</Folder>';
$kml[] = '</kml>';
$kmlOutput = join("\n", $kml);
header('Content-type: application/vnd.google-earth.kml+xml');
echo $kmlOutput;
?>