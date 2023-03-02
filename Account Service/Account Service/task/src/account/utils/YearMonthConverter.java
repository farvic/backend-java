package account.utils;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class YearMonthConverter {
    public static YearMonth convert(String input) {
        String formattedInput = input;
        if (input.matches("\\d{4}-\\d{2}")) {
            formattedInput = input.substring(5) + "-" + input.substring(0, 4);
        }
        return YearMonth.parse(formattedInput, DateTimeFormatter.ofPattern("MM-yyyy"));
    }
}
