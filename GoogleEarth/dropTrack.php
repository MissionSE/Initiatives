<?php
    $tn = $_POST['num'];
    
    //Connect to the database
    $con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');

    //Select the earth database
    mysql_select_db("earth", $con) or die('Sorry, could not select database');
        
    $query = "DELETE from tracks WHERE trackNum = '$tn'";
    
    $result = mysql_query($query) or die('Could not DELETE track');

    echo $result;    
?>