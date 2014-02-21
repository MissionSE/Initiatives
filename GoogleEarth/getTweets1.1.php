<?php
    session_start();
    //echo "before require_once\n";
    require_once("twitteroauth/twitteroauth.php"); //Path to twitteroauth library
    //echo "after require_once\n";
    $search = "#superbowl";
    $twitteruser = "wganley";
    $notweets = 10;
    $consumerkey = "JIls85hnFXkYySf7Vz3nSA";
    $consumersecret = "NTU4MU5oPVmzkGVkuFL6tLtEGt1PVwvMLshgPISc42w";
    $accesstoken = "18257600-pFFShe1EdR2irFXIT6Y7Asw6ndx3eYOQeUMFsf4qw";
    $accesstokensecret = "ldb17xxMMjSQNhaAYEuj9fKyq3wUPd2USPgRsSUcSRYwI";
    //echo "calling getConnectionWithAccessToken\n";     
    function getConnectionWithAccessToken($cons_key, $cons_secret, $oauth_token, $oauth_token_secret) {
      $connection = new TwitterOAuth($cons_key, $cons_secret, $oauth_token, $oauth_token_secret);
      return $connection;
    }
    //echo "calling getConnectionWithAccessToken\n";
    $connection = getConnectionWithAccessToken($consumerkey, $consumersecret, $accesstoken, $accesstokensecret);
    //echo "calling connection->get\n";     
    //$tweets = $connection->get("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=".$twitteruser."&count=".$notweets);
    $search = str_replace("#", "%23", $search); 
    $tweets = $connection->get("https://api.twitter.com/1.1/search/tweets.json?q=".$search."&count=".$notweets);
    echo json_encode($tweets);
?>