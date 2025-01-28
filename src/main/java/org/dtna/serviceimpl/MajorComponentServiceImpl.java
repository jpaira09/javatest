package org.dtna.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.dtna.dto.major_component.InventoryRequestDto;
import org.dtna.dto.major_component.subdtos.InventoryMC;
import org.dtna.dto.product_inventory.subdtos.InventoryXMLDto;
import org.dtna.dto.major_component.subdtos.MajorComponent;
import org.dtna.model.MajorComponentInventoryIMSModel;
import org.dtna.repository.MajorComponentInventoryIMSRepo;
import org.dtna.salesforceauthroization.SalesForceLoginResponse;
import org.dtna.salesforceauthroization.SalesforceLoginToken;
import org.dtna.service.MajorComponentService;
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
public class MajorComponentServiceImpl implements MajorComponentService {

    @Autowired
    private XMLReader xmlReader;

    @Autowired
    private SalesforceLoginToken salesforceLoginToken;

    @Autowired
    private SalesforceDataHelper salesforceDataHelper;

    @Autowired
    private MajorComponentInventoryIMSRepo majorComponentInventoryIMSRepo;

    @Override
    public List<InventoryMC> processProductInventory(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        log.info("Major Component Creation");
        List<InventoryXMLDto> inventoryXMLDtoList = xmlReader.readXML(inputStream);
        InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();
        List<MajorComponent> list = new ArrayList<>();
        List<InventoryMC> inventoryMCList = new ArrayList<>();
        for (InventoryXMLDto inventoryXMLDto : inventoryXMLDtoList) {
            InventoryMC inventoryMC = new InventoryMC();
            MajorComponent majorComponent = new MajorComponent();

            inventoryMC.setName(inventoryXMLDto.getName());
            inventoryMC.setAxleRation(inventoryXMLDto.getAxleRation());
            inventoryMC.setProductSerialNumber(inventoryXMLDto.getProductSerialNumber());
            inventoryMC.setModel(inventoryXMLDto.getModel());
            inventoryMC.setBusinessUnits(inventoryXMLDto.getBusinessUnits());
            inventoryMC.setOrderDate(xmlReader.removeDashFromDates(inventoryXMLDto.getOrderDate()));
            inventoryMC.setOrderingVocation(inventoryXMLDto.getOrderingVocation());
            inventoryMC.setRegisteredVocation(inventoryXMLDto.getRegisteredVocation());
            inventoryMC.setInServiceDate(xmlReader.removeDashFromDates(inventoryXMLDto.getInServiceDate()));
            inventoryMC.setOrderDealerCode(inventoryXMLDto.getOrderDealerCode());
            inventoryMC.setProductYear("2025");
            inventoryMC.setUnitNumber("4871");
            inventoryMC.setUnitUsage("4871.0");
            inventoryMC.setBuildDate(xmlReader.removeDashFromDates(inventoryXMLDto.getBuildDate()));
            inventoryMC.setUnitOfMeasure(inventoryXMLDto.getUnitOfMeasure());
            if (inventoryXMLDto.getProductType() != null) {
                majorComponent.setMajorComponentName(inventoryXMLDto.getProductType());
                majorComponent.setSerialNumber(inventoryXMLDto.getProductSerialNumber());
                majorComponent.setManufacturingDate(xmlReader.removeDashFromDates(inventoryXMLDto.getBuildDate()));
                majorComponent.setProductCode("Philips");
                majorComponent.setUnitUsage("15");
                majorComponent.setMcpType("Original");
                majorComponent.setPosition("Front");
                list.add(majorComponent);
            }
            inventoryMC.setMajorComponent(list);
            inventoryMCList.add(inventoryMC);
        }

        if (!inventoryMCList.isEmpty()) {
            SalesForceLoginResponse salesForceLoginResponse = salesforceLoginToken.getLoginAuthorizationToken();
            log.info(salesForceLoginResponse.getAccess_token());
            String token = salesForceLoginResponse.getToken_type() + " " + salesForceLoginResponse.getAccess_token();
            log.info(token);
            log.info("Sending Data to SF");

            salesforceDataHelper.sendDataToSalesforceMajorComponent(inventoryMCList, token);
        }
        return inventoryMCList;
    }


    @Override
    public String sendMajorComponentInventoryDataToIMS(List<MajorComponentInventoryIMSModel> majorComponentInventoryImsModel){
        if (majorComponentInventoryImsModel.isEmpty()){
            return "No Data to Submit";
        }
        majorComponentInventoryIMSRepo.saveAll(majorComponentInventoryImsModel);
        return "Data Submitted";
    }
}
