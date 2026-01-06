package io.github.tbarissatir.taskmanagement.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;

@Component
public class RuleEngineClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String RULE_ENGINE_URL = "http://localhost:8090/api/rules/evaluate";

    public RuleEngineResponse check(String input) {

        Map<String, String> payload = Map.of("input", input);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<RuleEngineResponse> response =
                restTemplate.postForEntity(RULE_ENGINE_URL, entity, RuleEngineResponse.class);

        return response.getBody();
    }
}
