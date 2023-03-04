package com.login.hth.beans;

import com.login.hth.dto.MessageDTO;
import com.login.hth.dto.UserProfileDTO;
import com.login.hth.security.JWTUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import static com.login.hth.beans.ClaimsData.formattedDate;
@Component
public class UserSignUp {

    @Autowired
    JWTUtility jwtUtility;
    @Autowired
    UserProfile userProfile;


    public ResponseEntity<Object> userProfile(@RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        MessageDTO er = new MessageDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        if (claims.get("ssn").toString() != "") {
            UserProfileDTO userProfileDTO = new UserProfileDTO();
            List<String[]> result = userProfile.getuserProfile(String.valueOf(claims.get("ssn")));
            userProfileDTO.setFirstName(result.get(0)[0].trim());
            userProfileDTO.setLastName(result.get(0)[1].trim());
            userProfileDTO.setDateOfBirth(formattedDate(result.get(0)[2].trim()));
            userProfileDTO.setPhoneNo(result.get(0)[3].trim());
            userProfileDTO.setUsername(result.get(0)[4].trim());
            userProfileDTO.setEmail(result.get(0)[5].trim());
            if (!result.get(0)[6].trim().equals("")){
                userProfileDTO.setPolicy(result.get(0)[6].trim());
            }else if (!result.get(0)[7].trim().equals("")){
                userProfileDTO.setPolicy(result.get(0)[7].trim());
            }else {
                userProfileDTO.setPolicy(result.get(0)[4].trim());
            }
            return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
        } else {
            er.setMessage("Invalid User");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

}
