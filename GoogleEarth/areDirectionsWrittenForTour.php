<?php
    //Get data from posted parameters
    $tourID = $_POST['id'];

    include("connectToDatabase.inc.php");

    $query = "SELECT * from directionsPoints WHERE tourId = $tourID";

    $result = mysql_query($query);

    $count = mysql_num_rows ($result);

    echo $count;
?>