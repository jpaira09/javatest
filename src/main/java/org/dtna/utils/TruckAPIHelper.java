package org.dtna.utils;

import lombok.extern.slf4j.Slf4j;
import org.dtna.dto.technician_interface.Certifications;
import org.dtna.dto.technician_interface.subdtos.Technicians;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class TruckAPIHelper {

    public List<Technicians> getTechnicianInfo() {
        RestTemplate restTemplate = new RestTemplate();
        List<Technicians> techniciansList= new ArrayList<>();

        // URL to call
        String url = "https://c388xoxgi3.execute-api.us-west-2.amazonaws.com/dev/test/getSample";

        try {
            ResponseEntity<List<Technicians>> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Technicians>>() {}
            );


            log.info(response.getBody().toString());
            techniciansList=response.getBody();





        } catch (Exception e) {
            // Handle exceptions
            System.err.println("Error occurred while making GET request: " + e.getMessage());
        }
        return techniciansList;
    }
}
