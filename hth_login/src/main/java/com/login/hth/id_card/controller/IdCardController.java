package com.login.hth.id_card.controller;

import com.login.hth.id_card.beans.GRPMST;
import com.login.hth.id_card.beans.IdCardData;
import com.login.hth.user.beans.UserLogin;
import com.login.hth.utility.JWTUtility;
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
@RequestMapping("/api/id_card")
public class IdCardController {
    @Autowired
    IdCardData idCardData;
    @Autowired
    UserLogin userLogin;
    @Autowired
    JWTUtility jwtUtility;

    @GetMapping("/show")
    public ResponseEntity<Object> showIdCard(@RequestHeader("Authorization") String bearerToken) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        bearerToken = bearerToken.substring(7, bearerToken.length());
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);

        System.out.println(claims.get("ssn"));
        System.out.println(claims.get("group"));
       // String[] userDetails = userLogin.getUserDetail(auth.getName());

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
