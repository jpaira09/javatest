package org.dtna.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.dtna.dto.product_inventory.ParentInventory;
import org.dtna.dto.product_inventory.subdtos.Inventory;
import org.dtna.dto.product_inventory.subdtos.InventoryXMLDto;
import org.dtna.dto.product_inventory.subdtos.MajorComponent;
import org.dtna.model.ProductInventoryImsModel;
import org.dtna.repository.ProductInventoryIMSRepo;
import org.dtna.salesforceauthroization.SalesForceLoginResponse;
import org.dtna.salesforceauthroization.SalesforceLoginToken;
import org.dtna.service.InventoryService;
import org.dtna.utils.SalesforceDataHelper;
import org.dtna.utils.XMLReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private XMLReader xmlReader;
    @Autowired
    private ProductInventoryIMSRepo   productInventoryIMSRepo;
    @Autowired
    private SalesforceDataHelper salesforceDataHelper;
    @Autowired
    private SalesforceLoginToken salesforceLoginToken;

    @Override
    public List<Inventory> createInventory(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        log.info("Inventory creation ");
        List<InventoryXMLDto> inventoryXMLDtoList=xmlReader.readXML(inputStream);
        SalesForceLoginResponse salesForceLoginResponse = salesforceLoginToken.getLoginAuthorizationToken();
        String token = salesForceLoginResponse.getToken_type() + " " + salesForceLoginResponse.getAccess_token();
        ParentInventory parentInventory=new ParentInventory();
        List<MajorComponent>list=new ArrayList<>();
        List<Inventory>inventoryList=new ArrayList<>();
        for(InventoryXMLDto inventoryXMLDto:inventoryXMLDtoList)
        {
            Inventory inventory=new Inventory();
            MajorComponent majorComponent=new MajorComponent();
            inventory.setName(xmlReader.getLastSixLettersInAscendingOrder(inventoryXMLDto.getProductSerialNumber()));
            inventory.setProductSerialNumber(inventoryXMLDto.getProductSerialNumber());
            inventory.setModel(inventoryXMLDto.getModel());
            inventory.setBuildDate(xmlReader.removeDashFromDates(inventoryXMLDto.getInServiceDate()));
            inventory.setInServiceDate(xmlReader.removeDashFromDates(inventoryXMLDto.getInServiceDate()));
            inventory.setUnitOfMeasure(inventoryXMLDto.getUnitOfMeasure());
            inventory.setProductYear("2025");
            inventory.setOrderDealerCode("0018N00000SHPqbQAH");
//            inventory.setBaseModel(inventoryXMLDto.getModel());
//            inventory.setMake(inventoryXMLDto.getMake());
//            inventory.setVocationCode(inventoryXMLDto.getVocationCode());
//            inventory.setProductSerialNumber(inventoryXMLDto.getProductSerialNumber());
//            inventory.setProviderCode(inventoryXMLDto.getProviderCode());
//            inventory.setSpecialWarrantyCode(inventoryXMLDto.getSpecialWarrantyCode());
//            inventory.setSaleDate(inventoryXMLDto.getBuildDate());
//            inventory.setProductType(inventoryXMLDto.getProductType());
//            inventory.setPolicyCode(inventoryXMLDto.getPolicyCode());
            inventory.setUnitOfMeasure(inventoryXMLDto.getUnitOfMeasure());
            inventory.setUsageValue("424242");
            if(inventoryXMLDto.getProductType()!=null)
            {
                majorComponent.setMajorComponentName(inventoryXMLDto.getProductType());
//                majorComponent.setComponent(inventoryXMLDto.getProductType());
                majorComponent.setSerialNumber(inventoryXMLDto.getProductSerialNumber());
                majorComponent.setManufacturingDate(xmlReader.removeDashFromDates(inventoryXMLDto.getBuildDate()));
                list.add(majorComponent);

            }
            inventory.setMajorComponent(list);
            inventoryList.add(inventory);




        }
        log.info(inventoryList.toString());

        log.info("Sending data to Sf");
        salesforceDataHelper.sendDataToSalesforceInventoryMajorComponent(inventoryList,token);

       return inventoryList;
    }

    @Override
    public String sendDataOfInventoryInBatches(ParentInventory parentInventory) {
        int batchSize = 50;


        return "";
    }

    @Override
    public String sendProductInventoryToIms(List<ProductInventoryImsModel> productInventoryImsModel) {
      productInventoryIMSRepo.saveAll(productInventoryImsModel);
      return "Data Submitted";
    }
}
