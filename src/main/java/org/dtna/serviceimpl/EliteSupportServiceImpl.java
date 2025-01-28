package org.dtna.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.dtna.dto.elitesupport.EliteSupportRequestDto;
import org.dtna.dto.elitesupport.NextGenPayloadEliteSupport;
import org.dtna.salesforceauthroization.SalesForceLoginResponse;
import org.dtna.salesforceauthroization.SalesforceLoginToken;
import org.dtna.service.EliteSupportService;
import org.dtna.utils.SalesforceDataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EliteSupportServiceImpl implements EliteSupportService {

    @Value("${aws.api.gateway.url}")
    private String apiUrl;
    @Autowired
    private  SalesforceDataHelper salesforceDataHelper;
@Autowired
    private  SalesforceLoginToken salesforceLoginToken;




    public String processEliteSupportFlag(EliteSupportRequestDto eliteSupportRequestDto){
        log.info("Transforming the input data for elite support");
        NextGenPayloadEliteSupport payload = new NextGenPayloadEliteSupport();
        payload.setLocationCode(eliteSupportRequestDto.getLocationCode());
        payload.setEliteSupport(eliteSupportRequestDto.getEliteSupport());
        payload.setIsActive(eliteSupportRequestDto.getIsActive());
        payload.setStartdate(eliteSupportRequestDto.getStartDate());
        payload.setEnddate(eliteSupportRequestDto.getEndDate());
        payload.setName(eliteSupportRequestDto.getName());


//        SalesForceLoginResponse salesForceLoginResponse=salesforceLoginToken.getLoginAuthorizationToken();
//        log.info(salesForceLoginResponse.getAccess_token());
//        String token=salesForceLoginResponse.getToken_type()+" "+salesForceLoginResponse.getAccess_token();
//        log.info(token);
//
//        salesforceDataHelper.sendEliteSupportDataToSalesforce(payload,token );

        return  "Submitted";
    }
}
