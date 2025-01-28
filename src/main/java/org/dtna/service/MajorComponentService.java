package org.dtna.service;

import org.dtna.dto.major_component.subdtos.InventoryMC;
import org.dtna.model.MajorComponentInventoryIMSModel;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Service
public interface MajorComponentService {
     List<InventoryMC> processProductInventory(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException;
     String sendMajorComponentInventoryDataToIMS(List<MajorComponentInventoryIMSModel> majorComponentInventoryImsModel);
}
