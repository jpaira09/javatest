package org.dtna.utils;


import lombok.extern.slf4j.Slf4j;
import org.dtna.dto.product_inventory.subdtos.InventoryXMLDto;
import org.dtna.dto.product_inventory.subdtos.ProductAttribute;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class XMLReader {

    public List<InventoryXMLDto> readXML(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {

        List<InventoryXMLDto> list=new ArrayList<>();

        List<ProductAttribute>productAttributeList=new ArrayList<>();
        HashMap<String,String>map=new HashMap<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(inputStream);
        doc.getDocumentElement().normalize();
        log.info("Root element: {}", doc.getDocumentElement().getNodeName());
        NodeList productRegistrations = doc.getDocumentElement().getElementsByTagName("ProductRegistrationInfo");

        for(int i=0;i<productRegistrations.getLength();i++)
        {
            InventoryXMLDto inventoryXMLDto =new InventoryXMLDto();
            inventoryXMLDto.setBase(doc.getDocumentElement().getElementsByTagName("BaseModel").item(i).getTextContent());
            inventoryXMLDto.setMake(doc.getDocumentElement().getElementsByTagName("Make").item(i).getTextContent());
            inventoryXMLDto.setModel(doc.getDocumentElement().getElementsByTagName("Model").item(i).getTextContent());
            inventoryXMLDto.setBuildDate(doc.getDocumentElement().getElementsByTagName("BuildDate").item(i).getTextContent());
            inventoryXMLDto.setProductSerialNumber(doc.getDocumentElement().getElementsByTagName("ProductSerialNumber").item(i).getTextContent());
            inventoryXMLDto.setProductType(doc.getDocumentElement().getElementsByTagName("ProductType").item(i).getTextContent());
            inventoryXMLDto.setProviderCode(doc.getDocumentElement().getElementsByTagName("ProviderCode").item(i).getTextContent());
            inventoryXMLDto.setVocationCode(doc.getDocumentElement().getElementsByTagName("VocationCode").item(i).getTextContent());
            inventoryXMLDto.setSpecialWarrantyCode(doc.getDocumentElement().getElementsByTagName("SpecialWarrantyCode").item(i).getTextContent());

//            inventoryXMLDto.setInstallDate(doc.getDocumentElement().getElementsByTagName("InstallDate").item(i).getTextContent());
            inventoryXMLDto.setName(doc.getDocumentElement().getElementsByTagName("AssetName").item(i).getTextContent());
            inventoryXMLDto.setAxleRation(doc.getDocumentElement().getElementsByTagName("AxleRotation").item(i).getTextContent());
            inventoryXMLDto.setBusinessUnits(doc.getDocumentElement().getElementsByTagName("BusinessUnits").item(i).getTextContent());
            inventoryXMLDto.setOrderDate(doc.getDocumentElement().getElementsByTagName("OrderDate").item(i).getTextContent());
            inventoryXMLDto.setOrderingVocation(doc.getDocumentElement().getElementsByTagName("OrderingVocation").item(i).getTextContent());
            inventoryXMLDto.setRegisteredVocation(doc.getDocumentElement().getElementsByTagName("RegisteredVocation").item(i).getTextContent());
            inventoryXMLDto.setInServiceDate(doc.getDocumentElement().getElementsByTagName("InServiceDate").item(i).getTextContent());
            inventoryXMLDto.setOrderDealerCode(doc.getDocumentElement().getElementsByTagName("OrderDealerCode").item(i).getTextContent());

            NodeList productAttributes = doc.getDocumentElement().getElementsByTagName("ProductAttributes");

            log.info(productAttributes.toString());
            for (int j = 0; j < productAttributes.getLength(); j++) {

                map.put(doc.getDocumentElement().getElementsByTagName("Name").item(j).getTextContent(),doc.getDocumentElement().getElementsByTagName("Code").item(j).getTextContent());
            }
            NodeList timeLine=doc.getDocumentElement().getElementsByTagName("TimeLines");
            for(int time=0;time< timeLine.getLength();time++)
            {
                map.put(doc.getDocumentElement().getElementsByTagName("Type").item(time).getTextContent(),doc.getDocumentElement().getElementsByTagName("DateValue").item(time).getTextContent());
            }

            inventoryXMLDto.setProductAttributes(productAttributeList);

           inventoryXMLDto.setInServiceDate(map.get("INSERVICE_DATE"));
            inventoryXMLDto.setProductYear(map.get("PRODUCT_YEAR"));
            inventoryXMLDto.setPolicyCode(doc.getDocumentElement().getElementsByTagName("PolicyCode").item(i).getTextContent());
            inventoryXMLDto.setUnitOfMeasure(doc.getDocumentElement().getElementsByTagName("UnitOfMeasure").item(i).getTextContent());

             list.add(inventoryXMLDto);
        }
        log.info("List{}", list);

        return  list;
    }
    public  String getLastSixLettersInAscendingOrder(String input) {
        // Check if the input string is null or has fewer than 6 characters
        if (input == null || input.length() < 6) {
            throw new IllegalArgumentException("Input string must have at least 6 characters.");
        }

        // Extract the last 6 characters of the string
        String lastSix = input.substring(input.length() - 6);

        // Convert the string into a character array
        char[] charArray = lastSix.toCharArray();

        // Sort the character array in ascending order


        // Convert the sorted character array back into a string
        return new String(charArray);
    }
    public  String removeDashFromDates(String  input)
    {
        return  input.replace("-","");

    }

}
