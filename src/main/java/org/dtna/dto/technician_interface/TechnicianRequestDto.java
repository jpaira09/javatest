package org.dtna.dto.technician_interface;

import lombok.Data;

@Data
public class TechnicianRequestDto {
    private String technicianId;
    private String locationCode;
    private String technicianName;
    private String startDate;
    private String endDate;
    private String customerAccount;
    private String productsCertifiedOn;
    private String certificationType;
    private String certificationDate;
    private String certificationExpirationDate;



}
