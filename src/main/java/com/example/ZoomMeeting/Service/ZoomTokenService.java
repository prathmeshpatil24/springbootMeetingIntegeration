package com.example.ZoomMeeting.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Service
public class ZoomTokenService {

    @Value("${zoom.account.id}")
    private String accountId;

    @Value("${zoom.client.id}")
    private String clientId;

    @Value("${zoom.client.secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    public String getAccessToken() {

        //create the url to get the access token
        String url = "https://zoom.us/oauth/token"
                + "?grant_type=account_credentials" // tell zoom we are using the server-to-server oauth
                + "&account_id=" + accountId;

        HttpHeaders headers = new HttpHeaders();

        //create the auth header
        headers.set("Authorization", "Basic " +
                Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
        // admin's client id and secret
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // make post request to zoom to get the access token
        ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, Map.class
        );

        System.out.println("Access token response:- " + response.getBody());
        System.out.println("==========");

        System.out.println("Access_token:- " + (String) response.getBody().get("access_token"));
        return (String) response.getBody().get("access_token");
    }
}
