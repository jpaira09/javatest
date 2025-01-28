package org.dtna.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.jsse.PEMFile;
import org.dtna.dto.technician_interface.*;
import org.dtna.dto.technician_interface.subdtos.PrismCertifications;
import org.dtna.dto.technician_interface.subdtos.Technicians;
import org.dtna.salesforceauthroization.SalesForceLoginResponse;
import org.dtna.salesforceauthroization.SalesforceLoginToken;
import org.dtna.service.TechnicianService;
import org.dtna.utils.ExcelFileReadHelper;
import org.dtna.utils.RandomReferenceId;
import org.dtna.utils.SalesforceDataHelper;
import org.dtna.utils.TruckAPIHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.*;

@Slf4j
@Service
public class TechnicianServiceImpl implements TechnicianService {
    @Autowired
    private ExcelFileReadHelper excelFileReadHelper;

    @Autowired
    private SalesforceDataHelper salesforceDataHelper;
    @Autowired
    private SalesforceLoginToken salesforceLoginToken;
    @Autowired
    private RandomReferenceId randomReferenceId;

    @Autowired
    TruckAPIHelper truckAPIHelper;
    @Override
    public List<NextGenPayloadTechnician> convertFormatForTechnician() {
       List<Technicians>techniciansList= truckAPIHelper.getTechnicianInfo();
       List<PrismCertifications>prismCertificationsList=new ArrayList<>();
       List<NextGenPayloadTechnician>nextGenPayloadTechniciansList=new ArrayList<>();

       for(Technicians technicians:techniciansList)
       {
           NextGenPayloadTechnician nextGenPayloadTechnician=new NextGenPayloadTechnician();
           List<Certifications> certificationsList=technicians.getCertifications();
           for(Certifications certifications:certificationsList)
           {
               PrismCertifications prismCertification=new PrismCertifications();
               prismCertification.setCertificationDate(certifications.getCertificationDate());
               prismCertification.setTrackType(certifications.getTrackType());
               prismCertification.setCertificationType(certifications.getCertificationType());
               prismCertification.setExternalId("23463");
               prismCertification.setProductCertifiedOn("01t8N000005qsLMQAY");
               prismCertificationsList.add(prismCertification);
           }
           nextGenPayloadTechnician.setTechnicianId(technicians.getTechnicianId());
           nextGenPayloadTechnician.setTechnicianName(technicians.getTechnicianName());
           nextGenPayloadTechnician.setStartDate(technicians.getStartDate());
           nextGenPayloadTechnician.setPrimaryCode(technicians.getPrimaryCode());
           nextGenPayloadTechnician.setCertificationsList(prismCertificationsList);
           nextGenPayloadTechnician.setEndDate(technicians.getEndDate());
           nextGenPayloadTechniciansList.add(nextGenPayloadTechnician);

       }
       return nextGenPayloadTechniciansList;
    }

    @Override

    public String sendDataInBatches() throws FileNotFoundException, JsonProcessingException {
        SalesForceLoginResponse salesForceLoginResponse = salesforceLoginToken.getLoginAuthorizationToken();
        String token = salesForceLoginResponse.getToken_type() + " " + salesForceLoginResponse.getAccess_token();
        List<NextGenPayloadTechnician> recordsTechnician=convertFormatForTechnician();
        int batchSize = 50;
        int totalRecords = recordsTechnician.size();
        for (int i = 0; i < totalRecords; i += batchSize) {
            int end = Math.min(totalRecords, i + batchSize);
            List<NextGenPayloadTechnician> batch = recordsTechnician.subList(i, end);



            // Get the Salesforce token


            // Send the batch to Salesforce
            log.info("Sending batch from record " + i + " to " + (end - 1));

            salesforceDataHelper.sendDataToSalesforceTechnician(batch, token);
        }


        return "Submitted";
    }
}


