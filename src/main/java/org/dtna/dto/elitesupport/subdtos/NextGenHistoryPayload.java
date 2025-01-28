package org.dtna.dto.elitesupport.subdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NextGenHistoryPayload {
    private String PRSM_IsActive__c;
    private String PRSM_Start_Date__c;
    private String PRSM_End_Date__c;
}
