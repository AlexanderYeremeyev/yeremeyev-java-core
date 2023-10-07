package com.yeremeyev.java.core.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
G	Era designator	Text	AD
y	Year	Year	1996; 96
Y	Week year	Year	2009; 09
M	Month in year	Month	July; Jul; 07
w	Week in year	Number	27
W	Week in month	Number	2
D	Day in year	Number	189
d	Day in month	Number	10
F	Day of week in month	Number	2
E	Day name in week	Text	Tuesday; Tue
u	Day number of week (1 = Monday, ..., 7 = Sunday)	Number	1
a	Am/pm marker	Text	PM
H	Hour in day (0-23)	Number	0
k	Hour in day (1-24)	Number	24
K	Hour in am/pm (0-11)	Number	0
h	Hour in am/pm (1-12)	Number	12
m	Minute in hour	Number	30
s	Second in minute	Number	55
S	Millisecond	Number	978
z	Time zone	General time zone	Pacific Standard Time; PST; GMT-08:00
Z	Time zone	RFC 822 time zone	-0800
X	Time zone
* */
public class DateTimeTools {

    private static final String DATE_PATTERN = "yyyy-MM-dd-HH-mm-ss";

    public static String getCurrentDate(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String result = dateFormat.format(date);
        return result;
    }

    public static String getCurrentDate() {
        return getCurrentDate(DATE_PATTERN);
    }

    public static String getDate(long dateLong, String pattern) {
        Date date = new Date(dateLong);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String result = dateFormat.format(date);
        return result;
    }
}
