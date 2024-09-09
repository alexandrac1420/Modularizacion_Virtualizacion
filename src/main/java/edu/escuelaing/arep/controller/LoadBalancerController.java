package edu.escuelaing.arep.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoadBalancerController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String[] instances = {
            "http://localhost:35001/log",
            "http://localhost:35002/log",
            "http://localhost:35003/log"
    };
    private int currentInstance = 0;

    @PostMapping("/submit")
    public String sendToLogService(@RequestBody String message) {
        String targetUrl = instances[currentInstance];
        System.out.println("Enviando mensaje a: " + targetUrl); // Imprime a qué instancia se está enviando el mensaje
        String response = restTemplate.postForObject(targetUrl, message, String.class);
        currentInstance = (currentInstance + 1) % instances.length; // Round Robin
        System.out.println(response.toString());
        return response;
    }

}
