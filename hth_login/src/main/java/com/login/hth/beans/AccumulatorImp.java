package com.login.hth.beans;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AccumulatorImp {
    public ResponseEntity<Object> accumulatorProfile(String group){
        List<String[]> accum = Accumulator.accumulatorData( group);

        return null;
    }
}
