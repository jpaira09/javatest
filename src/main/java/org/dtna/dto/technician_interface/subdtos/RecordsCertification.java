package org.dtna.dto.technician_interface.subdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecordsCertification {
    private List<PrismCertifications> records;
}
