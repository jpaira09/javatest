package org.dtna.authoriser.service;

import io.jsonwebtoken.Claims;
import org.dtna.authoriser.dto.AuthDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService{
    public  String generateToken(AuthDto authDto);
    public Claims validateToken(String token);


}
