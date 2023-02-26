package com.login.hth.controller;

import com.login.hth.beans.*;
import com.login.hth.dto.*;
import com.login.hth.security.JWTUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.login.hth.beans.ClaimsData.formattedDate;
import static java.lang.System.err;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserLogin userLogin;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserVerify userValidation;
    @Autowired
    JWTUtility jwtUtility;
    @Autowired
    SendEmail sendEmail;
    @Autowired
    OtpValidate otpValidate;
    @Autowired
    SignupUser signupUser;
    @Autowired
    ClaimsData claimsData;
    @Autowired
    IdCardData idCardData;
    @Autowired
    SecurityQue securityQue;
    @Autowired
    UserProfile userProfile;
    @Autowired
    SecurityDetails securityDetails;
    @Autowired
    CoverageImp coverageImp;

    


    @PostMapping("/userLogin")
    public ResponseEntity<Object> userLogin(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getEmail(),
                            userDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            MessageDTO er = new MessageDTO();
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            er.setMessage("INVALID CREDENTIALS");
            return new ResponseEntity<>(er, httpStatus);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userLogin.checkUser(userDTO);
    }

    @PostMapping("/otpValidate")
    public ResponseEntity<Object> otpValidate(@RequestBody OtpValidateDTO otpValidateDTO) {
        return otpValidate.otpValidate(otpValidateDTO);
    }

    @PostMapping("/validation")
    public ResponseEntity<Object> userValidation(@RequestBody UserVerifyDTO userValidationDTO) {
        return userValidation.checkValidation(userValidationDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signupUser(@RequestBody SignupRequestDTO signupRequestDTO) {
        MessageDTO er = new MessageDTO();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<String[]> userExist = signupUser.checkUserName(signupRequestDTO);
        if (userExist.size() > 0) {
            if (userExist.get(0)[0].trim().equals(signupRequestDTO.getUserName())) {
                er.setMessage("Username Already in use");
            } else if (userExist.get(0)[1].trim().equals(signupRequestDTO.getEmail())) {
                er.setMessage("Email Already in use");
            } else if (userExist.get(0)[2].trim().equals(signupRequestDTO.getPhoneNo())) {
                er.setMessage("Phone No. Already in use");
            } else {
                er.setMessage("User Already Registered");
            }
            return new ResponseEntity<>(er, httpStatus);
        } else if (!signupRequestDTO.getPassword().equals(signupRequestDTO.getConfirmPassword())) {
            er.setMessage("Password Mismatched.");
            return new ResponseEntity<>(er, httpStatus);
        } else {
            List<String[]> result = signupUser.insertUserDetails(signupRequestDTO);
            if (result == null)
                er.setMessage("Invalid Data");
            else {
                httpStatus = HttpStatus.OK;
                er.setMessage("User Created Successfully");
            }
            return new ResponseEntity<>(er, httpStatus);
        }
    }

    @GetMapping("/forgetPassword/{email}")
    public ResponseEntity<Object> sendEmail(@PathVariable("email") String email) {
        return sendEmail.checkUser(email);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Object> forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO, @RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        MessageDTO er = new MessageDTO();
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        String email = claims.get("email").toString();
        if (userLogin.getUserDetail(email).length > 0) {
            if (!forgetPasswordDTO.getNewPassword().equals(forgetPasswordDTO.getConfirmPassword())) {
                er.setMessage("Incorrect Values"); // Changes Done For Testing According to QA FEEDBACK
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            } else {
                return sendEmail.updatePassword(forgetPasswordDTO.getNewPassword(), email);
            }
        } else {
            er.setMessage("Bad Request");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @RequestHeader("Authorization") String bearerToken) {
        MessageDTO er = new MessageDTO();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        String email = claims.get("email").toString();
        if (userLogin.getUserDetail(email).length > 0) {
            if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
                er.setMessage("Password not Matched");
                return new ResponseEntity<>(er, httpStatus);
            } else {
                return sendEmail.changePassword(changePasswordDTO, email);
            }
        } else {
            er.setMessage("Bad Request");
            return new ResponseEntity<>(er, httpStatus);
        }
    }

    @GetMapping("/claims")
    public ResponseEntity<Object> getClaims(@RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        MessageDTO er = new MessageDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        if (claims.get("ssn").toString() != " ") {
            return claimsData.checkClaim(claims.get("ssn").toString());
        } else {
            er.setMessage("Invalid User");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/userProfile")
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
            userProfileDTO.setPolicy(result.get(0)[6].trim());
            return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
        } else {
            er.setMessage("Invalid User");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/idCard")
    public ResponseEntity<Object> showIdCard(@RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        MessageDTO er = new MessageDTO();
        String group = claims.get("group").toString();
        if (!group.isEmpty()) {
            if (GRPMST.groupExists(group)) {
                return idCardData.showIdCard(claims);
            } else {
                er.setMessage("Group Not found.");
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }
        } else {
            er.setMessage("Invalid Token.");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/securityQuestions")
    public ResponseEntity<Object> securityQue(@RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        MessageDTO err = new MessageDTO();
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        if (claims.get("email").toString() != "") {
            return securityQue.getSecurityQuestions(claims.get("email").toString());
        } else {
            err.setMessage("User Not found.");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/securityAnswers")
    public ResponseEntity<Object> securityAns(@RequestHeader("Authorization") String bearerToken, @RequestBody SecurityQuestionDTO securityQuestionDTO) {
        MessageDTO err = new MessageDTO();
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        if (claims.get("email").toString() != " ") {
            return securityQue.securityAns(securityQuestionDTO, claims.get("email").toString());
        } else {
            err.setMessage("User Not found.");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/securityDetails")
    public ResponseEntity<Object> changeSecurity(@RequestBody SecurityQuestionDTO securityQuestionDTO, @RequestHeader("Authorization") String bearerToken) {
        MessageDTO err = new MessageDTO();
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        String email = claims.get("email").toString();
        if (userLogin.getUserDetail(email).length > 0) {
            return securityDetails.updateSecurity(securityQuestionDTO, email);

        } else {
            err.setMessage("Incorrect Data");
        }
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/questions")
    public ResponseEntity<Object> Questions(QuestionsDTO questionsDTO) {
        return new ResponseEntity<>(questionsDTO, HttpStatus.OK);
    }

    @GetMapping("/coverageProfile")
    public ResponseEntity<Object> medicalCoverage(@RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        MessageDTO er = new MessageDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        //String email = claims.get("ssn").toString();
        if (claims.get("ssn").toString() != " ") {
            return coverageImp.coverageProfile(claims.get("ssn").toString());
        } else {
            er.setMessage("Invalid User");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/securityAnswers")
    public ResponseEntity<Object> securityAnswer(@RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        MessageDTO err = new MessageDTO();
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        if (claims.get("email").toString() != "") {
            return securityQue.securityAnswers(claims.get("email").toString());
        } else {
            err.setMessage("User Not found.");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }
}




