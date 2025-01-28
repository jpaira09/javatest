package org.dtna.dto.technician_interface.subdtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Certifications {


 private  String Name;
 private  String Certification_Type__c;

 private  String Start_Date__c;
 private  String End_Date__c;
 private  String Products_Certified_on__c;
 private  String External_Id__c;
 private  String Track_Type__c;
 private  Attributes attributes;


}
