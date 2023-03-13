package com.login.hth.beans;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ClaimData {

    public static ResponseEntity<Object> getDates(String email) {
        LocalDate startDate = java.time.LocalDate.parse(""), LocalDate, endDate = java.time.LocalDate.parse("");

            long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            return (ResponseEntity<Object>) IntStream.iterate(0, i -> i + 1)
                    .limit(numOfDaysBetween)
                    .mapToObj(i -> startDate.plusDays(i))
                    .collect(Collectors.toList());

    }
}

