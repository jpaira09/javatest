package org.dtna.dto.technician_interface.subdtos;

import lombok.Data;
import org.dtna.dto.technician_interface.Certifications;

import java.util.List;
@Data
public class Technicians {
    private String endDate;
    private String startDate;
    private String primaryCode;
    private String technicianId;
    private String technicianName;
    private List<Certifications> certifications;
}
