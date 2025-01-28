package org.dtna.dto.technician_interface;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dtna.dto.technician_interface.subdtos.PrismCertifications;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NextGenPayloadTechnician {
   private  String technicianId;
   private  String technicianName;
   private  String primaryCode;
   private  String startDate;
   private String endDate;
   private List<PrismCertifications> certificationsList;



}
