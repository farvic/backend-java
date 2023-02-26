package account.utils;

import java.util.ArrayList;
import java.util.List;

public class SecurityChecker {

    private static final List<String> breachedPasswords = new ArrayList<>();

    static {
        breachedPasswords.addAll(
                List.of(
                        "PasswordForJanuary",
                        "PasswordForFebruary",
                        "PasswordForMarch",
                        "PasswordForApril",
                        "PasswordForMay",
                        "PasswordForJune",
                        "PasswordForJuly",
                        "PasswordForAugust",
                        "PasswordForSeptember",
                        "PasswordForOctober",
                        "PasswordForNovember",
                        "PasswordForDecember"
                )
        );
    }

    public static boolean isBreached(String password) {
        return breachedPasswords.contains(password);
    }

}
