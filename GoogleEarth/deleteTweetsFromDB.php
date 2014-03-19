<?php
    //Connect to the database
    //$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');
    //
    ////Select the earth database
    //mysql_select_db("earth", $con) or die('Sorry, could not select database');
        
    include("connectToDatabase.inc.php");
    $query = "DELETE from tweets";
    
    $result = mysql_query($query) or die('Could not DELETE tweets');
    
    //if ($result)
    //    echo "deleted tweets successfully\n";
    //else
    //    echo "could not delete tweets\n";
?>