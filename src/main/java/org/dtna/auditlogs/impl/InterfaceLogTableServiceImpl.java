package org.dtna.auditlogs.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.dtna.auditlogs.dto.InterfaceRequestDto;
import org.dtna.auditlogs.model.InterfaceLogTable;
import org.dtna.auditlogs.repo.InterfaceLogTableRepo;
import org.dtna.auditlogs.service.InterfaceLogService;
import org.dtna.notifications.NotificationMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class InterfaceLogTableServiceImpl implements InterfaceLogService {
    @Autowired
    InterfaceLogTableRepo interfaceLogTableRepo;

    @Override
    public String logAuditReport(InterfaceRequestDto interfaceRequestDto) {
        InterfaceLogTable interfaceLogTable=new InterfaceLogTable();
        interfaceLogTable.setInterfaceName(interfaceRequestDto.getInterfaceName());
        interfaceLogTable.setSalesForceTransactionId(interfaceRequestDto.getSalesforceId());
        interfaceLogTable.setErrorMessageFromSalesforce(interfaceRequestDto.getErrorMsg());
        interfaceLogTable.setResponseCode(interfaceRequestDto.getResponseCode());
        interfaceLogTable.setStatus(interfaceRequestDto.getStatus());
        interfaceLogTable.setData(interfaceRequestDto.getData());
        interfaceLogTable.setRetryNumber(interfaceRequestDto.getRetry());
        interfaceLogTable.setTimestamp(new Timestamp(System.currentTimeMillis()));
        interfaceLogTableRepo.save(interfaceLogTable);
        log.info("Logs recorded for {}",interfaceLogTable.toString());
        return "Logs recorded for"+interfaceLogTable.toString();
    }



}
