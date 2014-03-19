<?php
    //Get data from posted parameters
    $json = $_POST['data'];

    echo "json = ".$json."\n";
    $tweets = json_decode($json);

    //Connect to the database
    //$con = mysql_connect("localhost", "test", "test") or die('Sorry, could not connect to database server');
    //
    //echo "selecting DB\n";
    //
    ////Select the earth database
    //mysql_select_db("earth", $con) or die('Sorry, could not select database');
        
    include("connectToDatabase.inc.php");
    //Get the number of json objects
    $count = count($tweets);
    
    echo "count = ".$count."\n";
    
    for ($i = 0; $i < $count; $i++) {
        $tweetid = (int)$tweets[$i]->tweetid;
        $username = $tweets[$i]->username;
        $text = strip_tags($tweets[$i]->status);
        $location = $tweets[$i]->location;
        $profileimage = $tweets[$i]->profileimage;
        
        echo "tweet id ".$tweetid."\n";
        //echo "tweet username ".$tweets[$i]->username."\n";
        //echo "tweet status ".$tweets[$i]->status."\n";
        echo "tweet location ".$location."\n";

        if ($tweetid > 0) {
            echo "querying DB\n";
            $query = "SELECT username from tweets where tweetid = $tweetid";
            
            echo "getting query result\n";
            $result = mysql_query($query);
            echo "result = ".$result."\n";
    
            echo "getting num rows\n";
            
            $rows = mysql_num_rows($result);
            echo "num rows = ".$rows."\n";
        
            //Tweet already exists
            if ($rows > 0) {
                echo "updating tweetid\n";
                $updateQuery = "UPDATE tweets SET tweetid = '$tweetid', location = '$location', username = $username, text = '$text', profileimage = $profileimage WHERE tweetid = $tweetid";
                
                $result = mysql_query($updateQuery) or die(mysql_error());        
            }
            //New tweet
            else {
                echo "inserting tweetid\n";
                $insertQuery = "INSERT INTO tweets (tweetid, location, username, text, profileimage) VALUES ('$tweetid','$location','$username','$text','$profileimage')";
                
                $result = mysql_query($insertQuery) or die(mysql_error());
            }
        }
    }
?>