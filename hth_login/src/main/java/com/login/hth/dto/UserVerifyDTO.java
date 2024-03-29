package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerifyDTO {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String employPolicy;

    public void setDateOfBirth(String dateOfBirth) {
        //IN YYYY-MM-DD
        //OUT MDDYYYY
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Mddyyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dob = sdf.parse(dateOfBirth);
            this.dateOfBirth = simpleDateFormat.format(dob);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }
}
