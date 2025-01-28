package org.dtna.salesforceauthroization;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.file.LinkOption;

@Component
@EnableScheduling
@Slf4j
public class SalesforceLoginToken {

    @Value("${salesforce.login.url}")
    private String loginUrl ;
    @Value("${salesforce.username}")
    private  String userName;
    @Value("${salesforce.password}")
    private String password;
    @Value("${grant_type}")
    private  String grantType;
    @Value("${client_id}")
    private String clientId;
    @Value("${client_secret}")
    private  String clientSecret;
    @PostConstruct
    public  void generateToken()
    {
        getLoginAuthorizationToken();
    }
    @Scheduled(fixedRate = 7200000)
    public SalesForceLoginResponse getLoginAuthorizationToken()
    {
        log.info("Scheduled");

        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username",userName);
        formData.add("password",password);
        formData.add("grant_type",grantType);
        formData.add("client_id",clientId);
        formData.add("client_secret",clientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<SalesForceLoginResponse> response = restTemplate.postForEntity(loginUrl, request, SalesForceLoginResponse.class);

        log.info("Response: {}", response.getBody());
        return  response.getBody();

    }
}
