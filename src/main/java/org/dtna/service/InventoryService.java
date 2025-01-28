package org.dtna.service;

import org.dtna.dto.product_inventory.ParentInventory;
import org.dtna.dto.product_inventory.subdtos.Inventory;
import org.dtna.model.ProductInventoryImsModel;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public interface InventoryService {
    public List<Inventory> createInventory(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException;
    public   String sendDataOfInventoryInBatches(ParentInventory parentInventory);
    public String  sendProductInventoryToIms(List<ProductInventoryImsModel> productInventoryImsModel);
}
