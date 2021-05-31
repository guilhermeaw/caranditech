package validators;

public class PhoneValidator {
    public static boolean validate(String phone) {
        if (phone == null || phone.length() < 14) {
            return false;
        }

        return true;
    }
}
