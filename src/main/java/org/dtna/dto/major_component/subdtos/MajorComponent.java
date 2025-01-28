package org.dtna.dto.major_component.subdtos;

import lombok.Data;

@Data
public class MajorComponent {
    private String majorComponentName;
    private String serialNumber;
    private String manufacturingDate;
    private String installDate;
    private String component;
    private String position;
    private String mcpType;
    private String productCode;
    private String unitUsage;
}
