package org.dtna.service;

import org.dtna.dto.elitesupport.EliteSupportRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface EliteSupportService {
  String processEliteSupportFlag(EliteSupportRequestDto eliteSupportRequestDto);
}
