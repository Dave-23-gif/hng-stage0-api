package com.dave.hngstage0.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ClassifyController {

    @GetMapping("/classify")
    public ResponseEntity<?> classify(@RequestParam(required = false) String name) {

        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", "error", "message", "Name parameter is required")
            );
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.genderize.io?name=" + name;

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map body = response.getBody();

            String gender = (String) body.get("gender");

            Double probability = body.get("probability") != null
                    ? Double.valueOf(body.get("probability").toString())
                    : 0.0;

            Integer count = body.get("count") != null
                    ? Integer.valueOf(body.get("count").toString())
                    : 0;

            if (gender == null || count == 0) {
                return ResponseEntity.status(422).body(
                        Map.of("status", "error", "message", "No prediction available for the provided name")
                );
            }

            boolean isConfident = probability >= 0.7 && count >= 100;

            String processedAt = java.time.Instant.now().toString();

            Map<String, Object> data = Map.of(
                    "name", name.toLowerCase(),
                    "gender", gender,
                    "probability", probability,
                    "sample_size", count,
                    "is_confident", isConfident,
                    "processed_at", processedAt
            );

            return ResponseEntity.ok(
                    Map.of("status", "success", "data", data)
            );

        } catch (Exception e) {
            return ResponseEntity.status(502).body(
                    Map.of("status", "error", "message", "Failed to fetch data from external API")
            );
        }
    }
}