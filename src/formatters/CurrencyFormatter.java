package formatters;

import java.text.NumberFormat;

public class CurrencyFormatter {
    public static String format(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(value);
    }
}
