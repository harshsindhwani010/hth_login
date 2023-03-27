package com.login.hth.beans;

import com.login.hth.dto.ClaimResponseDTO;
import com.login.hth.dto.DocumentDTO;
import com.login.hth.security.iSeries;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentImp {

    public static List<String[]> getDocumentData(String groupId) {
        List<String[]>resultList = null;
        String alias = "QTEMP.IMAGE";
        String file = "TESTDATA.IMAGE(TRT)";
        String sql = "SELECT IIMG,IDESC FROM QTEMP.IMAGE WHERE IGRPID ='" + groupId + "' or IBLKID = '" + groupId + "' or IBLKPL='" + groupId + "'";

        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

    public ResponseEntity<Object> documentImp(String groupId){

        List <String[]> document = getDocumentData(groupId);
        List<DocumentDTO> wholeDTOList = new ArrayList<DocumentDTO>();
        for (String[] doc: document) {
            DocumentDTO documentDTO = new DocumentDTO();
            documentDTO.setImages(doc[0]);
            documentDTO.setImageDescription(doc[1]);
            wholeDTOList.add(documentDTO);
        }
        return new ResponseEntity<>(wholeDTOList, HttpStatus.OK);
    }

}
