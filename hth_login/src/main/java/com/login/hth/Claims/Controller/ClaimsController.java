package com.login.hth.Claims.Controller;

import com.login.hth.Claims.Beans.ClaimsData;
import com.login.hth.utility.JWTUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ClaimsController {
    @Autowired
    private ClaimsData claimsData;

    @Autowired
    private JWTUtility jwtUtility;

    @GetMapping("/claims")
    public ResponseEntity<Object> getClaims(@RequestHeader ("Authrozation") String bearerToken) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        if (claims.get("ssn").toString() != "") {
            return claimsData.checkClaim(claims.get("ssn").toString());
        } else {
            return new ResponseEntity<>("SSN Not found.", HttpStatus.BAD_REQUEST);
        }
    }


}
