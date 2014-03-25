<?php
    //Get data from posted parameters
    $id = $_POST['id'];
        
    include("connectToDatabase.inc.php");

    //Delete the tour
    $query = "DELETE from tours where id = $id";                
    $result = mysql_query($query) or die(mysql_error());
    
    //Delete the points associated with the tour
    $query = "DELTE from tourPoints where tourId = $id";
    $result = mysql_query($query) or die(mysql_error());
?>