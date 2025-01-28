package org.dtna.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.dtna.auditlogs.dto.CustomResponseDto;
import org.dtna.auditlogs.dto.InterfaceRequestDto;
import org.dtna.auditlogs.dto.ResponseDto;

import org.dtna.auditlogs.dto.subdtos.Detail;
import org.dtna.auditlogs.service.InterfaceLogService;
import org.dtna.dto.elitesupport.NextGenPayloadEliteSupport;
import org.dtna.dto.product_inventory.subdtos.Inventory;
import org.dtna.dto.major_component.subdtos.InventoryMC;
import org.dtna.dto.technician_interface.NextGenPayloadTechnician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class SalesforceDataHelper {

    @Value("${salesforce.technician.url}")
    private String url;

    @Value("${salesforce.elite_support.url}")
    private String eliteSupportUrl;

    @Value("${salesforce.asset.url}")
    private  String inventoryUrl;

    @Value("${salesforce.major_component.url}")
    private String majorComponentUrl;


    @Autowired
    private InterfaceLogService interfaceLogService;
    @Retryable(
            value = {RuntimeException.class,HttpServerErrorException.class, HttpClientErrorException.class
            }, // Retry for RuntimeException or specific exceptions
            maxAttempts = 3,                  // Number of retry attempts
            backoff = @Backoff(delay = 2000)  // Delay between retries (in milliseconds)
    )
    public String sendDataToSalesforceTechnician(List<NextGenPayloadTechnician>nextGenPayloadTechnicianList, String bearerToken) throws JsonProcessingException {
        // Validate input
        if (nextGenPayloadTechnicianList == null || bearerToken == null || bearerToken.trim().isEmpty()) {
            throw new IllegalArgumentException("RecordsTechnician or Bearer Token cannot be null or empty");
        }

// Initialize RestTemplate and ObjectMapper
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

// Serialize RecordsTechnician to JSON
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(nextGenPayloadTechnicianList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize RecordsTechnician to JSON", e);
        }

// Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

// Create HTTP entity with request body
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        InterfaceRequestDto interfaceLogTable = new InterfaceRequestDto();
        try {
            ResponseEntity<CustomResponseDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    CustomResponseDto.class
            );

            CustomResponseDto customResponseDto = response.getBody();
            assert customResponseDto != null;
//            interfaceLogTable.setSalesforceId(customResponseDto.getResults().getId());
            interfaceLogTable.setErrorMsg(customResponseDto.getHasErrors());
            interfaceLogTable.setInterfaceName("Technician");
            interfaceLogTable.setResponseCode(response.getStatusCode().toString());
            interfaceLogTable.setData(nextGenPayloadTechnicianList.toString());

            log.info("Response Status Code: {}", response.getStatusCode());
            log.info("Response Body: {}", response.getBody());
        } catch (HttpClientErrorException ex) {
            // Capture exception details
            interfaceLogTable.setResponseCode(ex.getStatusCode().toString());
            interfaceLogTable.setErrorMsg( ex.getResponseBodyAsString());
            log.error("Client error: {}", ex.getStatusCode());
            log.error("Response Body: {}", ex.getResponseBodyAsString());

            throw new RuntimeException("Client error occurred during the request", ex);
        } catch (HttpServerErrorException ex) {
            // Capture exception details
            interfaceLogTable.setResponseCode(ex.getStatusCode().toString());
            interfaceLogTable.setErrorMsg(ex.getResponseBodyAsString());
            log.error("Server error: {}", ex.getStatusCode());
            log.error("Response Body: {}", ex.getResponseBodyAsString());

            throw new RuntimeException("Server error occurred during the request", ex);
        } catch (Exception ex) {
            // Handle other exceptions
            interfaceLogTable.setResponseCode("500"); // Default to 500 for unexpected errors
            interfaceLogTable.setErrorMsg( ex.getMessage());
            log.error("An error occurred: {}", ex.getMessage());

            throw new RuntimeException("Failed to send data to Salesforce", ex);
        } finally {
            // Log the audit report in any scenario
            interfaceLogTable.setData(nextGenPayloadTechnicianList.toString());
            interfaceLogService.logAuditReport(interfaceLogTable);
        }

        return requestBody;
    }

    public String sendEliteSupportDataToSalesforce(NextGenPayloadEliteSupport payload, String token) {
        // Validate input
        if (payload == null || token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Payload or Bearer Token cannot be null or empty");
        }

// Initialize RestTemplate and ObjectMapper
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

// Serialize Payload to JSON
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload to JSON", e);
        }

// Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",  token);
        headers.setContentType(MediaType.APPLICATION_JSON);

// Create HTTP entity with request body
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Make the POST request
            ResponseEntity<String> response = restTemplate.exchange(
                    eliteSupportUrl, // Replace with the correct URL
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Log response details
            log.info("Response Status Code: " + response.getStatusCode());
            log.info("Response Body: " + response.getBody());
        } catch (HttpClientErrorException ex) {
            // Handle 4xx errors
            log.info("Client error: {}", ex.getStatusCode());
            log.info("Response Body: " + ex.getResponseBodyAsString());
            throw new RuntimeException("Client error occurred during the request", ex);
        } catch (HttpServerErrorException ex) {
            // Handle 5xx errors
            log.info("Server error: " + ex.getStatusCode());
            log.info("Response Body: " + ex.getResponseBodyAsString());
            throw new RuntimeException("Server error occurred during the request", ex);
        } catch (Exception ex) {
            // Handle other errors
            log.info("An error occurred: " + ex.getMessage());
            throw new RuntimeException("Failed to send data to Salesforce", ex);
        }

        return requestBody;
    }

    public String sendDataToSalesforceInventoryMajorComponent(List<Inventory>list, String bearerToken) throws JsonProcessingException {
        // Validate input
        if (list.isEmpty() || bearerToken == null || bearerToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Parent InventoryMC or Bearer Token cannot be null or empty");
        }

// Initialize RestTemplate and ObjectMapper
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

// Serialize RecordsTechnician to JSON
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Parent InventoryMC to JSON", e);
        }

// Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",  bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

// Create HTTP entity with request body
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Make the POST request
            ResponseEntity<ResponseDto> response = restTemplate.exchange(
                    inventoryUrl, // Replace with the correct URL
                    HttpMethod.POST,
                    entity,
                    ResponseDto.class
            );
            log.info("Response Body: " + response.getBody());
            InterfaceRequestDto interfaceRequestDto=new InterfaceRequestDto();
            interfaceRequestDto.setInterfaceName(Objects.requireNonNull(response.getBody()).getInterfaceName());
            interfaceRequestDto.setResponseCode(response.getStatusCode().toString());
            interfaceRequestDto.setData(list.toString());

            interfaceRequestDto.setStatus(response.getBody().getStatus());
            for (Detail detail : response.getBody().getDetail()) {

                interfaceRequestDto.setErrorMsg(detail.getError());
                interfaceRequestDto.setSalesforceId(detail.getSalesforceId());
            }
            interfaceLogService.logAuditReport(interfaceRequestDto);


            // Log response details
            log.info("Response Status Code: " + response.getStatusCode());
            log.info("Response Body: " + response.getBody());
        } catch (HttpClientErrorException ex) {
            // Handle 4xx errors
            log.info("Client error: {}", ex.getStatusCode());
            log.info("Response Body: " + ex.getResponseBodyAsString());
            throw new RuntimeException("Client error occurred during the request", ex);
        } catch (HttpServerErrorException ex) {
            // Handle 5xx errors
            log.info("Server error: " + ex.getStatusCode());
            log.info("Response Body: " + ex.getResponseBodyAsString());
            throw new RuntimeException("Server error occurred during the request", ex);
        } catch (Exception ex) {
            // Handle other errors
            log.info("An error occurred: " + ex.getMessage());
            throw new RuntimeException("Failed to send data to Salesforce", ex);
        }


        return requestBody;
    }

    public String sendDataToSalesforceMajorComponent(List<InventoryMC> inventory, String bearerToken) throws JsonProcessingException {
        // Validate input
        if (inventory.isEmpty() || bearerToken == null || bearerToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Parent InventoryMC or Bearer Token cannot be null or empty");
        }

// Initialize RestTemplate and ObjectMapper
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

// Serialize InventoryRequestDto to JSON
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(inventory);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize InventoryMC Request DTO to JSON", e);
        }

// Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",  bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

// Create HTTP entity with request body
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Make the POST request
            ResponseEntity<ResponseDto> response = restTemplate.exchange(
                    majorComponentUrl, // Replace with the correct URL
                    HttpMethod.POST,
                    entity,
                    ResponseDto.class
            );
            log.info("Response Body: " + response.getBody());
            InterfaceRequestDto interfaceRequestDto=new InterfaceRequestDto();
            interfaceRequestDto.setInterfaceName(Objects.requireNonNull(response.getBody()).getInterfaceName());
            interfaceRequestDto.setResponseCode(response.getStatusCode().toString());
            interfaceRequestDto.setData(inventory.toString());

            interfaceRequestDto.setStatus(response.getBody().getStatus());
            for (Detail detail : response.getBody().getDetail()) {

                interfaceRequestDto.setErrorMsg(detail.getError());
                interfaceRequestDto.setSalesforceId(detail.getSalesforceId());
            }
            interfaceLogService.logAuditReport(interfaceRequestDto);


            // Log response details
            log.info("Response Status Code: " + response.getStatusCode());
            log.info("Response Body: " + response.getBody());
        } catch (HttpClientErrorException ex) {
            // Handle 4xx errors
            log.info("Client error: {}", ex.getStatusCode());
            log.info("Response Body: " + ex.getResponseBodyAsString());
            throw new RuntimeException("Client error occurred during the request", ex);
        } catch (HttpServerErrorException ex) {
            // Handle 5xx errors
            log.info("Server error: " + ex.getStatusCode());
            log.info("Response Body: " + ex.getResponseBodyAsString());
            throw new RuntimeException("Server error occurred during the request", ex);
        } catch (Exception ex) {
            // Handle other errors
            log.info("An error occurred: " + ex.getMessage());
            throw new RuntimeException("Failed to send data to Salesforce", ex);
        }


        return requestBody;
    }

}



