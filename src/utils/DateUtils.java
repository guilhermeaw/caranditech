package utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    public static LocalDate getLocalDateByDate(Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        return localDate;
    }

    public static Date getDateByLocalDate(LocalDate localDate) {
       Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

       return date;
    }
}
