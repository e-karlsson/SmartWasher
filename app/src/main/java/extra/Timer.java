package extra;

import java.util.Calendar;

/**
 * Created by xxottosl on 2015-04-16.
 */
public class Timer {


    public static TimeInfo translate(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        TimeInfo ti = new TimeInfo();
        ti.setMonth(getMonth(calendar.get(Calendar.MONTH)));
        ti.setDay(""+calendar.get(Calendar.DAY_OF_MONTH));
        ti.setHour(""+calendar.get(Calendar.HOUR_OF_DAY));
        ti.setMinute(""+calendar.get(Calendar.MINUTE));
        ti.setSecond(""+calendar.get(Calendar.SECOND));
        ti.monthNumber = calendar.get(Calendar.MONTH) + 1;
        ti.setWeek(calendar.get(Calendar.WEEK_OF_YEAR));
        ti.setDayName(getDay(calendar.get(Calendar.DAY_OF_WEEK)));

        return ti;

    }


    private static String getDay(int day){
        switch (day) {
            case 2:
                return "Måndag";
            case 3:
                return "Tisdag";
            case 4:
                return "Onsdag";
            case 5:
                return "Torsdag";
            case 6:
                return "Fredag";
            case 7:
                return "Lördag";
            case 1:
                return "Söndag";
        }
            return "?";


    }
    private static String getMonth(int month){
        switch (month) {
            case 0:
                return "jan";
            case 1:
                return "feb";
            case 2:
                return "mar";
            case 3:
                return "apr";
            case 4:
                return "maj";
            case 5:
                return "jun";
            case 6:
                return "jul";
            case 7:
                return "aug";
            case 8:
                return "sep";
            case 9:
                return "okt";
            case 10:
                return "nov";
            case 11:
                return "dec";
        }
        return "?";

    }

    public static String makeTwo(String digit){
        return (digit.length() == 1 ? "0"+digit : digit);
    }

    public static class TimeInfo{
        String month, day, hour, minute, second, dayName;
        int monthNumber;
        int week;

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = makeTwo(hour);
        }

        public String getMinute() {
            return minute;
        }

        public void setMinute(String minute) {
            this.minute = makeTwo(minute);
        }

        public int getMonthNumber() {
            return monthNumber;
        }

        public void setMonthNumber(int monthNumber) {
            this.monthNumber = monthNumber;
        }

        public String getSecond() {
            return second;
        }

        public String getDayName() {
            return dayName;
        }

        public void setDayName(String dayName) {
            this.dayName = dayName;
        }

        public void setSecond(String second) {
            this.second = makeTwo(second);
        }
        public String getWTDDate(){
            return dayName+" "+getDay()+"/"+(getMonthNumber()+1);
        }
        @Override
        public String toString() {
            return day+" "+month+" "+hour+":"+minute;
        }
    }

}
