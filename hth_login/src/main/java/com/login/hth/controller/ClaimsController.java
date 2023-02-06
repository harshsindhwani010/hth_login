package com.login.hth.controller;

import com.login.hth.security.JWTUtility;
import com.login.hth.beans.ClaimsData;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3")
public class ClaimsController {
    @Autowired
    ClaimsData claimsData;
    @Autowired
    JWTUtility jwtUtility;

    @GetMapping("/claims")
    public ResponseEntity<Object> getClaims(@RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        if (claims.get("ssn").toString() != "") {
            return claimsData.checkClaim(claims.get("ssn").toString());
        } else {
            return new ResponseEntity<>("SSN Not found.", HttpStatus.BAD_REQUEST);
        }
    }
}
