package org.dtna.dto.product_inventory;

import lombok.Data;
import org.dtna.dto.product_inventory.subdtos.*;

import java.util.List;

@Data
public class ParentInventory {
    private List<Inventory> inventory;

}
