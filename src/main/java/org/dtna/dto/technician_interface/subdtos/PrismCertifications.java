package org.dtna.dto.technician_interface.subdtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrismCertifications {

    private  String trackType;
    private  String certificationType;
    private String certificationDate;
    private  String certificationExpirationDate;
    private String productCertifiedOn;
    private String externalId;



}
