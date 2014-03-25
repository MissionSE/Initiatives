<?php
    $id = $_POST['id'];

    //Connect to the database
    include("connectToDatabase.inc.php");

    $query = "DELETE from clickLocations WHERE id = $id";
    
    $result = mysql_query($query) or die('Could not DELETE clickLocation');

    echo $result;    
?>