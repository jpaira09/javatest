package org.dtna.serviceimpl;

import org.dtna.dto.technician_interface.NextGenPayloadTechnician;
import org.dtna.dto.technician_interface.subdtos.Attributes;
import org.dtna.dto.technician_interface.subdtos.PrismCertifications;
import org.dtna.dto.technician_interface.subdtos.RecordsCertification;
import org.dtna.service.TechnicianService;
import org.dtna.utils.ExcelFileReadHelper;
import org.dtna.utils.RandomReferenceId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TechnicianServiceImplTest {
    @InjectMocks
    TechnicianService technicianService;
    @InjectMocks
    ExcelFileReadHelper excelFileReadHelper;
    @Mock
    private RandomReferenceId randomReferenceId;


//    @Test
//    void testForTechnicianPositiveCase() {
//
//        PrismCertifications certifications = new PrismCertifications();
//        List<NextGenPayloadTechnician> technicians = new ArrayList<>();
//        NextGenPayloadTechnician technician = new NextGenPayloadTechnician();
//        List<PrismCertifications> certificationsList = new ArrayList<>();
//        PrismCertifications recordsTechnician = new PrismCertifications();
//        RecordsCertification certification = new RecordsCertification();
//        Attributes attributes = new Attributes();
//        Attributes certificationAttributes = new Attributes();
//        certificationAttributes.setType("PRSM_Certification__c");
//        certificationAttributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        attributes.setType("PRSM_Certification__c");
//        attributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        certifications.setProducts_Certified_on__c("01t59000007xRlLAAU");
//        certifications.setTrack_Type__c("FCCCECO");
//        certifications.setName("Systems");
//        certifications.setStart_Date__c("11/2/2021");
//        certifications.setEnd_Date__c("");
//        certifications.setAttributes(attributes);
//        certifications.setCertification_Type__c("Expert");
//        technician.setTechnician_ID__c("1026364");
//        technician.setName("Josep Nagel");
//        technician.setAttributes(certificationAttributes);
//        technician.setLocation_code__c("001d0000026m0rlAAA");
//        certification.setRecords(certificationsList);
//        technician.setCertifications__r(certification);
//        certificationsList.add(certifications);
//        technicians.add(technician);
//
//
//
//        Assertions.assertNotNull(technicians);
//        assertEquals(1, technicians.size());
//        assertEquals("a0Q8N00000FJ9QOUA1", technicians.get(0).getAttributes().getReferenceId());
//        assertEquals("Josep Nagel", technicians.get(0).getName());
//
//        // Check certification details
//        assertEquals("Expert", certifications.getCertification_Type__c());
//        assertEquals("Systems", certifications.getName());
//        assertEquals("FCCCECO", certifications.getTrack_Type__c());
//        assertEquals("01t59000007xRlLAAU", certifications.getProducts_Certified_on__c());
//
//
//        // Verify interactions
//    }
//
//    @Test
//    void testForTechnicianNegativeCase() {
//        PrismCertifications certifications = new PrismCertifications();
//        List<NextGenPayloadTechnician> technicians = new ArrayList<>();
//        NextGenPayloadTechnician technician = new NextGenPayloadTechnician();
//        List<PrismCertifications> certificationsList = new ArrayList<>();
//        RecordsTechnician recordsTechnician = new RecordsTechnician();
//        RecordsCertification certification = new RecordsCertification();
//        Attributes attributes = new Attributes();
//        Attributes certificationAttributes = new Attributes();
//        certificationAttributes.setType("PRSM_Certification__c");
//        certificationAttributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        attributes.setType("PRSM_Certification__c");
//        attributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        certifications.setProducts_Certified_on__c("01t59000007xRlLAAU");
//        certifications.setTrack_Type__c("FCCCECO");
//        certifications.setName("Systems");
//        certifications.setStart_Date__c("11/2/2021");
//        certifications.setEnd_Date__c("");
//        certifications.setAttributes(attributes);
//        certifications.setCertification_Type__c("Expert");
//        technician.setTechnician_ID__c("1026364");
//        technician.setName("Josep Nagel");
//        technician.setAttributes(certificationAttributes);
//        technician.setLocation_code__c("001d0000026m0rlAAA");
//        certification.setRecords(certificationsList);
//        technician.setCertifications__r(certification);
//        certificationsList.add(certifications);
//        technicians.add(technician);
//        recordsTechnician.setRecords(technicians);
//
//
//        Assertions.assertNotNull(technicians);
//        assertNotEquals(2, technicians.size());
//        assertNotEquals("a0Q8N00000FJ9QOU1", technicians.get(0).getAttributes().getReferenceId());
//        assertNotEquals("Jsep Nagel", technicians.get(0).getName());
//
//        // Check certification details
//        assertNotEquals("Experteee", certifications.getCertification_Type__c());
//        assertNotEquals("System", certifications.getName());
//        assertNotEquals("Heavy", certifications.getTrack_Type__c());
//        assertNotEquals("01t59000007'xRlLAAU", certifications.getProducts_Certified_on__c());
//
//
//    }
//
//    @Test
//    void testTechnicianNameIsEmpty() {
//        PrismCertifications certifications = new PrismCertifications();
//        List<NextGenPayloadTechnician> technicians = new ArrayList<>();
//        NextGenPayloadTechnician technician = new NextGenPayloadTechnician();
//        List<PrismCertifications> certificationsList = new ArrayList<>();
//        RecordsTechnician recordsTechnician = new RecordsTechnician();
//        RecordsCertification certification = new RecordsCertification();
//        Attributes attributes = new Attributes();
//        Attributes certificationAttributes = new Attributes();
//        certificationAttributes.setType("PRSM_Certification__c");
//        certificationAttributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        attributes.setType("PRSM_Certification__c");
//        attributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        certifications.setProducts_Certified_on__c("01t59000007xRlLAAU");
//        certifications.setTrack_Type__c("FCCCECO");
//        certifications.setName("Systems");
//        certifications.setStart_Date__c("11/2/2021");
//        certifications.setEnd_Date__c("");
//        certifications.setAttributes(attributes);
//        certifications.setCertification_Type__c("Expert");
//        technician.setTechnician_ID__c("1026364");
//        technician.setName("Josep Nagel");
//        technician.setAttributes(certificationAttributes);
//        technician.setLocation_code__c("001d0000026m0rlAAA");
//        certification.setRecords(certificationsList);
//        technician.setCertifications__r(certification);
//        certificationsList.add(certifications);
//        technicians.add(technician);
//        recordsTechnician.setRecords(technicians);
//
//
//        Assertions.assertNotNull(technicians);
//        assertNotEquals(2, technicians.size());
//        assertNotEquals("a0Q8N00000FJ9QOU1", technicians.get(0).getAttributes().getReferenceId());
//        assertNull(null, technicians.get(0).getName());
//
//        // Check certification details
//        assertNotEquals("Experteee", certifications.getCertification_Type__c());
//        assertNotEquals("System", certifications.getName());
//        assertNotEquals("Heavy", certifications.getTrack_Type__c());
//        assertNotEquals("01t59000007'xRlLAAU", certifications.getProducts_Certified_on__c());
//
//
//    }
//
//    @Test
//    void testTrackTypeIsEmpty() {
//        PrismCertifications certifications = new PrismCertifications();
//        List<NextGenPayloadTechnician> technicians = new ArrayList<>();
//        NextGenPayloadTechnician technician = new NextGenPayloadTechnician();
//        List<PrismCertifications> certificationsList = new ArrayList<>();
//        RecordsTechnician recordsTechnician = new RecordsTechnician();
//        RecordsCertification certification = new RecordsCertification();
//        Attributes attributes = new Attributes();
//        Attributes certificationAttributes = new Attributes();
//        certificationAttributes.setType("PRSM_Certification__c");
//        certificationAttributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        attributes.setType("PRSM_Certification__c");
//        attributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        certifications.setProducts_Certified_on__c("01t59000007xRlLAAU");
//        certifications.setTrack_Type__c("FCCCECO");
//        certifications.setName("Systems");
//        certifications.setStart_Date__c("11/2/2021");
//        certifications.setEnd_Date__c("");
//        certifications.setAttributes(attributes);
//        certifications.setCertification_Type__c("Expert");
//        technician.setTechnician_ID__c("1026364");
//        technician.setName("Josep Nagel");
//        technician.setAttributes(certificationAttributes);
//        technician.setLocation_code__c("001d0000026m0rlAAA");
//        certification.setRecords(certificationsList);
//        technician.setCertifications__r(certification);
//        certificationsList.add(certifications);
//        technicians.add(technician);
//        recordsTechnician.setRecords(technicians);
//
//
//        Assertions.assertNotNull(technicians);
//        assertNotEquals(2, technicians.size());
//        assertNotEquals("a0Q8N00000FJ9QOU1", technicians.get(0).getAttributes().getReferenceId());
//        assertNull(null, technicians.get(0).getName());
//
//        // Check certification details
//        assertNotEquals("Experteee", certifications.getCertification_Type__c());
//        assertNotEquals("System", certifications.getName());
//        assertNull(null, certifications.getTrack_Type__c());
//        assertNotEquals("01t59000007'xRlLAAU", certifications.getProducts_Certified_on__c());
//
//
//    }
//    @Test
//    void testEmptyTechniciansAndCertifications() {
//        PrismCertifications certifications = new PrismCertifications();
//        List<NextGenPayloadTechnician> technicians = new ArrayList<>();
//        NextGenPayloadTechnician technician = new NextGenPayloadTechnician();
//        List<PrismCertifications> certificationsList = new ArrayList<>();
//        RecordsTechnician recordsTechnician = new RecordsTechnician();
//        RecordsCertification certification = new RecordsCertification();
//        Attributes attributes = new Attributes();
//        Attributes certificationAttributes = new Attributes();
//        certificationAttributes.setType("PRSM_Certification__c");
//        certificationAttributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        attributes.setType("PRSM_Certification__c");
//        attributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        certifications.setProducts_Certified_on__c("01t59000007xRlLAAU");
//        certifications.setTrack_Type__c("FCCCECO");
//        certifications.setName("Systems");
//        certifications.setStart_Date__c("11/2/2021");
//        certifications.setEnd_Date__c("");
//        certifications.setAttributes(attributes);
//        certifications.setCertification_Type__c("Expert");
//        technician.setTechnician_ID__c("1026364");
//        technician.setName("Josep Nagel");
//        technician.setAttributes(certificationAttributes);
//        technician.setLocation_code__c("001d0000026m0rlAAA");
//        certification.setRecords(certificationsList);
//        technician.setCertifications__r(certification);
//        certificationsList.add(certifications);
//        technicians.add(technician);
//        recordsTechnician.setRecords(technicians);
//
//        // Set up empty lists
//        technicians.clear();
//        certificationsList.clear();
//
//        Assertions.assertNotNull(technicians);
//        assertEquals(0, technicians.size());
//        assertEquals(0, certificationsList.size());
//    }
//    @Test
//    void testEmptyCertificationStartDate()
//    {
//        PrismCertifications certifications = new PrismCertifications();
//        List<NextGenPayloadTechnician> technicians = new ArrayList<>();
//        NextGenPayloadTechnician technician = new NextGenPayloadTechnician();
//        List<PrismCertifications> certificationsList = new ArrayList<>();
//        RecordsTechnician recordsTechnician = new RecordsTechnician();
//        RecordsCertification certification = new RecordsCertification();
//        Attributes attributes = new Attributes();
//        Attributes certificationAttributes = new Attributes();
//        certificationAttributes.setType("PRSM_Certification__c");
//        certificationAttributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        attributes.setType("PRSM_Certification__c");
//        attributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        certifications.setProducts_Certified_on__c("01t59000007xRlLAAU");
//        certifications.setTrack_Type__c("FCCCECO");
//        certifications.setName("Systems");
//        certifications.setStart_Date__c("");
//        certifications.setEnd_Date__c("");
//        certifications.setAttributes(attributes);
//        certifications.setCertification_Type__c("Expert");
//        technician.setTechnician_ID__c("1026364");
//        technician.setName("Josep Nagel");
//        technician.setAttributes(certificationAttributes);
//        technician.setLocation_code__c("001d0000026m0rlAAA");
//        certification.setRecords(certificationsList);
//        technician.setCertifications__r(certification);
//        certificationsList.add(certifications);
//        technicians.add(technician);
//        recordsTechnician.setRecords(technicians);
//
//        // Set up empty lists
//       assertNull(null,certificationsList.get(0).getStart_Date__c());
//    }
//
//    @Test
//    void testEmptyCertificationEndDate()
//    {
//        PrismCertifications certifications = new PrismCertifications();
//        List<NextGenPayloadTechnician> technicians = new ArrayList<>();
//        NextGenPayloadTechnician technician = new NextGenPayloadTechnician();
//        List<PrismCertifications> certificationsList = new ArrayList<>();
//        RecordsTechnician recordsTechnician = new RecordsTechnician();
//        RecordsCertification certification = new RecordsCertification();
//        Attributes attributes = new Attributes();
//        Attributes certificationAttributes = new Attributes();
//        certificationAttributes.setType("PRSM_Certification__c");
//        certificationAttributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        attributes.setType("PRSM_Certification__c");
//        attributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        certifications.setProducts_Certified_on__c("01t59000007xRlLAAU");
//        certifications.setTrack_Type__c("FCCCECO");
//        certifications.setName("Systems");
//        certifications.setStart_Date__c("11/2/2021");
//        certifications.setEnd_Date__c("");
//        certifications.setAttributes(attributes);
//        certifications.setCertification_Type__c("Expert");
//        technician.setTechnician_ID__c("1026364");
//        technician.setName("Josep Nagel");
//        technician.setAttributes(certificationAttributes);
//        technician.setLocation_code__c("001d0000026m0rlAAA");
//        certification.setRecords(certificationsList);
//        technician.setCertifications__r(certification);
//        certificationsList.add(certifications);
//        technicians.add(technician);
//        recordsTechnician.setRecords(technicians);
//
//        // Set up empty lists
//        assertNull(null,certificationsList.get(0).getEnd_Date__c());
//    }
//    @Test
//    void testTechnicianLocationCode()
//    {
//        PrismCertifications certifications = new PrismCertifications();
//        List<NextGenPayloadTechnician> technicians = new ArrayList<>();
//        NextGenPayloadTechnician technician = new NextGenPayloadTechnician();
//        List<PrismCertifications> certificationsList = new ArrayList<>();
//        RecordsTechnician recordsTechnician = new RecordsTechnician();
//        RecordsCertification certification = new RecordsCertification();
//        Attributes attributes = new Attributes();
//        Attributes certificationAttributes = new Attributes();
//        certificationAttributes.setType("PRSM_Certification__c");
//        certificationAttributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        attributes.setType("PRSM_Certification__c");
//        attributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        certifications.setProducts_Certified_on__c("01t59000007xRlLAAU");
//        certifications.setTrack_Type__c("FCCCECO");
//        certifications.setName("Systems");
//        certifications.setStart_Date__c("11/2/2021");
//        certifications.setEnd_Date__c("");
//        certifications.setAttributes(attributes);
//        certifications.setCertification_Type__c("Expert");
//        technician.setTechnician_ID__c("1026364");
//        technician.setName("Josep Nagel");
//        technician.setAttributes(certificationAttributes);
//        technician.setLocation_code__c("001d0000026m0rlAAA");
//        certification.setRecords(certificationsList);
//        technician.setCertifications__r(certification);
//        certificationsList.add(certifications);
//        technicians.add(technician);
//        recordsTechnician.setRecords(technicians);
//
//        // Set up emptylists
//        assertEquals("001d0000026m0rlAAA",technicians.get(0).getLocation_code__c());
//    }
//    @Test
//    void testIncorrectTechnicianLocationCode()
//    {
//        PrismCertifications certifications = new PrismCertifications();
//        List<NextGenPayloadTechnician> technicians = new ArrayList<>();
//        NextGenPayloadTechnician technician = new NextGenPayloadTechnician();
//        List<PrismCertifications> certificationsList = new ArrayList<>();
//        RecordsTechnician recordsTechnician = new RecordsTechnician();
//        RecordsCertification certification = new RecordsCertification();
//        Attributes attributes = new Attributes();
//        Attributes certificationAttributes = new Attributes();
//        certificationAttributes.setType("PRSM_Certification__c");
//        certificationAttributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        attributes.setType("PRSM_Certification__c");
//        attributes.setReferenceId("a0Q8N00000FJ9QOUA1");
//        certifications.setProducts_Certified_on__c("01t59000007xRlLAAU");
//        certifications.setTrack_Type__c("FCCCECO");
//        certifications.setName("Systems");
//        certifications.setStart_Date__c("11/2/2021");
//        certifications.setEnd_Date__c("");
//        certifications.setAttributes(attributes);
//        certifications.setCertification_Type__c("Expert");
//        technician.setTechnician_ID__c("1026364");
//        technician.setName("Josep Nagel");
//        technician.setAttributes(certificationAttributes);
//        technician.setLocation_code__c("001d0000026m0rlAAA");
//        certification.setRecords(certificationsList);
//        technician.setCertifications__r(certification);
//        certificationsList.add(certifications);
//        technicians.add(technician);
//        recordsTechnician.setRecords(technicians);
//
//        // Set up emptylists
//        assertNotEquals("001d0000026m0rlAA",technicians.get(0).getLocation_code__c());
//    }




}