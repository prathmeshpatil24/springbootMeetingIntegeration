package com.example.ZoomMeeting.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomMeetingService {

    @Autowired
    private ZoomTokenService tokenService;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> createMeeting(String topic, int duration) {

        //get the access token
        String accessToken = tokenService.getAccessToken();

        //create the meeting url
        String url = "https://api.zoom.us/v2/users/me/meetings";

        //create the headers
        HttpHeaders headers = new HttpHeaders();
        //set the access token in bearer
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("topic", topic);
        //types are 1,2,3,8 for different meeting types
        body.put("type", 2); // Scheduled meeting
        body.put("duration", duration);

        Map<String, Object> settings = new HashMap<>();
        settings.put("host_video", true);
        settings.put("participant_video", true);
        body.put("settings", settings);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        //send the post request to create the meeting
        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, entity, Map.class);

        return response.getBody(); // Contains start_url and join_url
          //start_url (admin/host)
         //join_url (teacher & students)
    }
}
