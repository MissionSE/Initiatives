<?php
    session_start();
    require_once("twitteroauth/twitteroauth.php"); //Path to twitteroauth library
    $search = $_GET['search'];
    $notweets = 50;
    $consumerkey = "JIls85hnFXkYySf7Vz3nSA";
    $consumersecret = "NTU4MU5oPVmzkGVkuFL6tLtEGt1PVwvMLshgPISc42w";
    $accesstoken = "18257600-pFFShe1EdR2irFXIT6Y7Asw6ndx3eYOQeUMFsf4qw";
    $accesstokensecret = "ldb17xxMMjSQNhaAYEuj9fKyq3wUPd2USPgRsSUcSRYwI";
    function getConnectionWithAccessToken($cons_key, $cons_secret, $oauth_token, $oauth_token_secret) {
      $connection = new TwitterOAuth($cons_key, $cons_secret, $oauth_token, $oauth_token_secret);
      return $connection;
    }
    $connection = getConnectionWithAccessToken($consumerkey, $consumersecret, $accesstoken, $accesstokensecret);
    $search = str_replace("#", "%23", $search); 
    $tweets = $connection->get("https://api.twitter.com/1.1/search/tweets.json?q=".$search."&count=".$notweets);
    echo json_encode($tweets);
?>