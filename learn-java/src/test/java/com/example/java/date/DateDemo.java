package com.example.java.date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @date 2018/3/26
 */
public class DateDemo {

    @Test
    public void dateAdd() throws ParseException {

        /**
         * 日期按月加的话，如果是当月比下月长，那么对应关系如下。
         * 2018-01-31 - 2018-02-28
         * 2018-01-30 - 2018-02-28
         * 2018-01-29 - 2018-02-28
         * 2018-01-28 - 2018-02-28
         * 2018-01-27 - 2018-02-27
         */

        SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
        Date date = yyyy_MM_dd.parse("2018-01-31");
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH, 1);
        System.out.println(yyyy_MM_dd.format(instance.getTime()));
        date = yyyy_MM_dd.parse("2018-01-30");
        instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH, 1);
        System.out.println(yyyy_MM_dd.format(instance.getTime()));
        date = yyyy_MM_dd.parse("2018-01-29");
        instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH, 1);
        System.out.println(yyyy_MM_dd.format(instance.getTime()));
        date = yyyy_MM_dd.parse("2018-01-28");
        instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH, 1);
        System.out.println(yyyy_MM_dd.format(instance.getTime()));
        date = yyyy_MM_dd.parse("2018-01-27");
        instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH, 1);
        System.out.println(yyyy_MM_dd.format(instance.getTime()));

    }

    @Test
    public void testFormat(){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date()));
    }
}
