package formatters;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateFormatter {
    public static String format(Date date) {
        String datePattern = "dd/MM/yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(datePattern);

        return outputFormat.format(date);
    }
}
