package org.dtna.controller;

import org.dtna.dto.elitesupport.EliteSupportRequestDto;
import org.dtna.service.EliteSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dtna/middleware")
public class EliteSupportController {
   @Autowired
    private  EliteSupportService eliteSupportService;



    @PostMapping("/elite-support")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String handleEliteSupportFlag(@RequestBody EliteSupportRequestDto eliteSupportRequestDto){
      return eliteSupportService.processEliteSupportFlag(eliteSupportRequestDto);

    }
}
