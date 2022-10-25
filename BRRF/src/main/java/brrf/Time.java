package brrf;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Time {
    private int hours;
    private int minutes;
    private int seconds;
    Logger logger = Logger.getLogger(Time.class.getName());
    private static final String ERRORMESSAGE = "Something went wrong with time formatting :s";

    public Time(String timeInText) {
        String[] values = timeInText.split(":");

        try {
            hours = Integer.parseInt(values[0]);
            minutes = Integer.parseInt(values[1]);
            seconds = Integer.parseInt(values[2]);
        } catch (NumberFormatException exc) {
            logger.log(Level.SEVERE, ERRORMESSAGE);
        }
    }

    // ----------- Method used to add time ------------
    public String addTime(String extraTime) {
        int extraHours = 0;
        int extraMinutes = 0;
        int extraSeconds = 0;

        // --------- Extract data from string ---------
        String[] values = extraTime.split(":");

        try {
            extraHours = Integer.parseInt(values[0]);
            extraMinutes = Integer.parseInt(values[1]);
            extraSeconds = Integer.parseInt(values[2]);
        } catch (NumberFormatException exc) {
            logger.log(Level.SEVERE, ERRORMESSAGE);
        }

        // --------- Modulo adding part ---------
        hours += extraHours;
        minutes += extraMinutes;
        seconds += extraSeconds;

        if (seconds >= 60) {
            minutes += 1;
        } else if (minutes >= 60) {
            hours += 1;
        }

        hours = hours % 24;
        minutes = minutes % 60;
        seconds = seconds % 60;

        // --------- Returning string ----------
        return getTime();
    }


    // ----------- Method used to subtract time e.g. when calculating time of journey ------------
    public String subtractTime(String diffTime) {
        int diffHours = 0;
        int diffMinutes = 0;
        int diffSeconds = 0;

        // --------- Extract data from string ---------
        String[] values = diffTime.split(":");

        try {
            diffHours = Integer.parseInt(values[0]);
            diffMinutes = Integer.parseInt(values[1]);
            diffSeconds = Integer.parseInt(values[2]);
        } catch (NumberFormatException exc) {
            logger.log(Level.SEVERE, ERRORMESSAGE);
        }

        // --------- Modulo adding part ---------
        hours -= diffHours;
        minutes -= diffMinutes;
        seconds -= diffSeconds;

        if (hours < 0) {
            hours += 23;
        } else if (minutes < 0) {
            hours--;
            minutes += 60;
        }

        //seconds += 60;
        if (seconds >= 60) {
            minutes += Math.floorDiv(seconds, 60);
            seconds = seconds % 60;
        }

        // --------- Returning string ----------
        return getTime();
    }

    // ------------ Method used to compare times ------------
    public boolean isGreater(String comparisonTime) {
        int compareHours = 0;
        int compareMinutes = 0;
        int compareSeconds = 0;

        // --------- Extract data from string ---------
        String[] values = comparisonTime.split(":");

        try {
            compareHours = Integer.parseInt(values[0]);
            compareMinutes = Integer.parseInt(values[1]);
            compareSeconds = Integer.parseInt(values[2]);
        } catch (NumberFormatException exc) {
            logger.log(Level.SEVERE, ERRORMESSAGE);
        }

        return compareHours >= hours && compareMinutes >= minutes && compareSeconds >= seconds;
    }

    public String getTime() {
        String hoursString = String.valueOf(hours);
        String minutesString = String.valueOf(minutes);
        String secondsString = String.valueOf(seconds);
        if (hours < 10) {
            hoursString = "0" + hours;
        }
        if (minutes < 10) {
            minutesString = "0" + minutes;
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        }

        return hoursString + ":" + minutesString + ":" + secondsString;
    }

    public static boolean checkIfFirstValueOfTimeIsHigher(String high, String low) {
        Time highTime = new Time(high);
        Time lowTime = new Time(low);

        if (highTime.hours > lowTime.hours) {
            return true;
        } else if (highTime.hours == lowTime.hours) {
            return highTime.minutes > lowTime.minutes;
        } else {
            return false;
        }
    }
}
