package com.pro.propertymanagepro.util;

import java.util.Calendar;

public class DateFormat {

    Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private int day = calendar.get(Calendar.DAY_OF_MONTH);
    private int hour = calendar.get(Calendar.HOUR);
    private int minute = calendar.get(Calendar.MINUTE);
    private int second = calendar.get(Calendar.SECOND);
    public String getFormatTime(){
        String now = year + "年" + day + "日" + hour + "时"
                + minute + "分" + second + "秒";
        return now;
    }
}
