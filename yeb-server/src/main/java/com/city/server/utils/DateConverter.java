package com.city.server.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class DateConverter {

    public static LocalDate[] dateParse(String source){

        if (StringUtils.isBlank(source)){
            return null;
        }

        if(source.contains(",")){
            String[] strings = source.split(",");
            LocalDate[] dates = new LocalDate[strings.length];
            int index = 0;
            for (String string : strings) {
                dates[index] = LocalDate.parse(string);
                index ++;
            }
            return dates;
        }else{
            LocalDate[] dates = new LocalDate[1];
            dates[0] = LocalDate.parse(source);
            return dates;
        }
    }

    public static void main(String[] args) {
        LocalDate[] dates = dateParse("2018-05-05,2020-11-11");
        for (LocalDate date : dates) {
            System.out.print(date + " ");
        }
    }

}
