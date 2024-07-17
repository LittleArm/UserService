package com.example.userservice.clients;

import com.example.userservice.dtos.EmployeeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeServiceClient {
    @Value("${application.service.url}")
    private String employeeServiceUrl;

    private final RestTemplate restTemplate;

    public EmployeeServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmployeeDTO getEmployeeByEmail(String email, String jwtToken) {
        String url = employeeServiceUrl + "/employees/" + email;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<EmployeeDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                EmployeeDTO.class
        );
        return response.getBody();
    }

    public void deleteEmployee(EmployeeDTO employeeDTO, String jwtToken) {
        String url = employeeServiceUrl + "/employees/" + employeeDTO.getEmail();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }
}
