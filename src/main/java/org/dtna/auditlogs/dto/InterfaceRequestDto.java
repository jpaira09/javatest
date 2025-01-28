package org.dtna.auditlogs.dto;

import lombok.Data;


@Data
public class InterfaceRequestDto {
    private  String interfaceName;
    private  String errorMsg;
    private  String data;
    private  String  retry;
    private String salesforceId;
    private  String status;
    private  String responseCode;


}
