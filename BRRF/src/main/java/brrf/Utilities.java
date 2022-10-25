package brrf;


import java.util.ArrayList;
import java.util.List;

public class Utilities {

    private Utilities() {
        throw new IllegalStateException("Utility class");
    }

    // ------ e.g: ARTUS (88110) -> ^ARTUS\s\(8811.*$ -------
    public static String nameRegexBuilder(String nameParam, String carrier) {
        String nameHandler;
        if (carrier.equals("IC") || carrier.equals("TLK")) {
            nameHandler = nameParam.substring(0, nameParam.length() - 2);

            for (int i = 0; i < nameHandler.length(); i++ ) {
                if (nameHandler.charAt(i) == '(') {
                    nameHandler = nameHandler.substring(0, i - 1) + "\\s\\" + nameHandler.substring(i);

                    break;
                }
            }

            return " ~ '^" + nameHandler + ".*$'";
        } else {
            nameHandler = nameParam;
            return " = '" + nameHandler + "'";
        }
    }

    // --------- Checks if any element of both lists is in common --------
    public static boolean matchAny(List<String> one, List<String> two) {
        boolean findAny = false;
        for (String element : one) {
            if (two.contains(element)) {
                findAny = true;
                break;
            }
        }
        return findAny;
    }

    // --------- Removes duplicates and divides trains into travel schedules ----------
    public static List<Train> scheduler(List<Train> mess) {
        List<Train> scheduleHolder = new ArrayList<>(mess);

        for (int i = 1; i < scheduleHolder.size(); i++) {
            if (scheduleHolder.get(i).getRegex().equals(scheduleHolder.get(i - 1).getRegex())) {
                scheduleHolder.remove(scheduleHolder.get(i));
            }
        }

        return scheduleHolder;
    }
}
