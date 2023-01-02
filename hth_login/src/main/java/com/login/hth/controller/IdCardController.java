package com.login.hth.controller;

import com.login.hth.security.JWTUtility;
import com.login.hth.beans.GRPMST;
import com.login.hth.beans.IdCardData;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class IdCardController {
    @Autowired
    IdCardData idCardData;
    @Autowired
    JWTUtility jwtUtility;

    @GetMapping("/idCard")
    public ResponseEntity<Object> showIdCard(@RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);

        String group = claims.get("group").toString();
        if (!group.isEmpty()) {
            if (GRPMST.groupExists(group)) {
                return idCardData.showIdCard(claims);
            } else {
                return new ResponseEntity<>("Group Not found.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Invalid Token.", HttpStatus.BAD_REQUEST);
        }
    }
}
