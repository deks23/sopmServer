package pl.sopmproject.sopmserver.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateConverter {

    private static int milliToNanoConst = 1000000;

    public static LocalDateTime jodaToJavaTime(org.joda.time.LocalDateTime dateTime) {
        return LocalDateTime.of(
                dateTime.getYear(),
                dateTime.getMonthOfYear(),
                dateTime.getDayOfMonth(),
                dateTime.getHourOfDay(),
                dateTime.getMinuteOfHour(),
                dateTime.getSecondOfMinute(),
                dateTime.getMillisOfSecond() * milliToNanoConst);
    }

    public static org.joda.time.LocalDateTime javaTimeToJoda(LocalDateTime dateTime) {
        return new org.joda.time.LocalDateTime(
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute(),
                dateTime.getSecond(),
                dateTime.getNano() / milliToNanoConst);
    }

    public static LocalDateTime milisToLocalDateTime(Long milis){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milis), ZoneId.systemDefault());
    }
}
