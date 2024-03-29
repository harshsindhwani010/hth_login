package com.login.hth.beans;

import com.login.hth.security.iSeries;
import com.login.hth.dto.IdCardResponseDTO;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class IdCardData {

    public ResponseEntity<Object> showIdCard(Claims claims) {
        String nameS = "A";
        String nameE = "Z";
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("MMddyy");
        format.setTimeZone(TimeZone.getTimeZone("GWT"));
        String date = format.format(today);

        String parameter = "ART" + " " + claims.get("name").toString().trim() + " " + claims.get("group").toString().trim() + " " + nameS.trim()+ " " + nameE.trim() + " " + date.trim();
        StringBuilder parm = new StringBuilder(parameter);
        while (parm.length() < 60) {
            parm.append(" ");
        }
        System.out.println("param"+parm);
        String loadCardCL = "CALL pdlib/LOADGRPID PARM('" + parm + "')";
        iSeries.executeCL(loadCardCL);
        return startPrint(claims.get("name").toString(), claims.get("ssn").toString());
    }

    private ResponseEntity<Object> startPrint(String user, String ussn) {
        List<String[]> data = null;
        final String[] idList = IDWORK.getInQueueID(user, ussn); // new String[] {"485235826"}; // new String[] {"003565315", "012521333", "237359347", "036607230"};
        final String[] grpList = IDWORK.getGrpList(); // new String[] {"NAIFA"}; // new String[] {"ARCB", "ARCB", "FLT", "FLT"};
        System.out.println("ID_PrinterSelection: IDLIST from IDWORK: " + idList.length);

        if (idList.length >0) {
            StringBuilder idValue = new StringBuilder(idList[0]);
            String clCommand = "CALL DFLIB/LOADIDC PARM('" + "ART" + "' '" + "PRT" + " ' '%s')";
            iSeries.executeCL(String.format(clCommand, idValue));

            IdCardResponseDTO cardList = IDCPRV.generateIDCARD(grpList, idList, "PRT");
            return new ResponseEntity<>(cardList, HttpStatus.OK);
        } else if (idList.length == 0) {
            return new ResponseEntity<>("No id cards.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("No Data found.", HttpStatus.BAD_REQUEST);
        }
    }
}
