package org.dtna.salesforceauthroization;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SalesForceLoginResponse {
    private  String access_token;
    private String instance_url;
    private String id;
    private String token_type;
    private String issued_at;
    private String signature;


}
