package com.example.ZoomMeeting.Controller;


import com.example.ZoomMeeting.Service.ZoomMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ZoomController {


    @Autowired
    private ZoomMeetingService meetingService;

    @GetMapping("/create-meeting")
    public Map<String, Object> createMeeting(
            @RequestParam String topic,
            @RequestParam(defaultValue = "30") int duration) {

        return meetingService.createMeeting(topic, duration);
    }
}
