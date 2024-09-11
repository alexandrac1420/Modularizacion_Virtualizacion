package edu.escuelaing.arep.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoadBalancerController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String[] instances = {
            "http://logservice1:6000/log",
            "http://logservice2:6000/log",
            "http://logservice3:6000/log"
    };
    private int currentInstance = 0;

    @PostMapping("/submit")
    public String sendToLogService(@RequestBody String message) {
        String targetUrl = instances[currentInstance];
        String response = restTemplate.postForObject(targetUrl, message, String.class);
        currentInstance = (currentInstance + 1) % instances.length;
        System.out.println(response);
        return response;
    }

}
