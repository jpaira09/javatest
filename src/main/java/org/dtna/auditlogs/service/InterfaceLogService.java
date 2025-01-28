package org.dtna.auditlogs.service;

import org.dtna.auditlogs.dto.InterfaceRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface InterfaceLogService {
    public  String logAuditReport(InterfaceRequestDto interfaceRequestDto);




}
