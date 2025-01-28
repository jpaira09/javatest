package org.dtna.controller;

import org.dtna.auditlogs.service.InterfaceLogService;
import org.dtna.authoriser.dto.AuthDto;
import org.dtna.authoriser.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/dtna/middleware")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    InterfaceLogService interfaceLogService;
    @PostMapping(value = "/auth")
    public  String generateAuthToken(@RequestBody AuthDto authDto){
        return authService.generateToken(authDto);
    }

}
