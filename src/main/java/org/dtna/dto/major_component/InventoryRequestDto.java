package org.dtna.dto.major_component;

import lombok.Data;
import org.dtna.dto.major_component.subdtos.InventoryMC;

import java.util.List;

@Data
public class InventoryRequestDto {
    private List<InventoryMC> inventoryMC;
}
