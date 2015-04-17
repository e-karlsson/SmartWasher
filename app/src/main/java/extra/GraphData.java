package extra;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.WashRecord;

/**
 * Created by xxottosl on 2015-04-17.
 */
public class GraphData {


    float totalEnergy;
    float totalPrice;
    ArrayList<DateData> dates;
    float scale;
    float highestEnergy, highestPrice;



    public GraphData(List<WashRecord> records, long startTime, int days){
        dates = generateDates(startTime, days);

        int dateIndex = 0;
        int len = records.size();

        for(int i = 0; i < len; ++i){
            WashRecord record = records.get(i);
            for(int j = dateIndex; j < days; ++j){
                Date date = dates.get(j).date;
                Timer.TimeInfo dateInfo = Timer.translate(date.getTime());
                Timer.TimeInfo recordInfo = Timer.translate(record.getProgramInfo().getStartTime());

                Log.d("DATE:", dateInfo.toString());
                Log.d("RECORD:", recordInfo.toString());

                if(sameDay(date, record.getProgramInfo().getStartTime())){
                    Log.d("EQUALS:","YES");
                    break;
                }else{
                    Log.d("EQUALS:","NO");
                    dateIndex++;//FIX DTHIS #TODO
                }
            }
            DateData dd = dates.get(dateIndex);

            float energy = record.getKiloWattHours();
            float price = record.getCost();

            if(energy > highestEnergy) highestEnergy = energy;
            if(price > highestPrice) highestPrice = price;

            totalEnergy += energy;
            totalPrice += price;

            dd.totalEnergy += energy;
            dd.totalPrice += price;
        }

        scale = totalEnergy/totalPrice;

    }

    public String getDateAt(float percent){
        Date date = dates.get((int) ((dates.size()-1)*percent)).date;
        Timer.TimeInfo ti = Timer.translate(date.getTime());
        return ti.getDay()+"/"+ti.getMonthNumber();
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getHighestEnergy() {
        return highestEnergy;
    }

    public void setHighestEnergy(float highestEnergy) {
        this.highestEnergy = highestEnergy;
    }

    public float getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(float highestPrice) {
        this.highestPrice = highestPrice;
    }

    public ArrayList<DateData> getDates() {
        return dates;
    }

    public void setDates(ArrayList<DateData> dates) {
        this.dates = dates;
    }

    public float getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(float totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    private boolean sameDay(Date date, long millis){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTimeInMillis(millis);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private ArrayList<DateData> generateDates(long startTime, int days){
        ArrayList<DateData> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startTime);
        for(int i = 0; i < days; ++i){
            list.add(new DateData(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }
        return list;
    }

    public static GraphData weekData(List<WashRecord> records, long startTime){
        return new GraphData(records, startTime, 7);
    }

    public static GraphData monthData(List<WashRecord> records, long startTime){
        return new GraphData(records, startTime, 30);
    }

    public static GraphData yearData(List<WashRecord> records, long startTime){
        return new GraphData(records, startTime, 365);
    }

    public boolean isWeek(){
        return dates.size() == 7;
    }

    public boolean isMonth(){
        return dates.size() == 30;
    }

    public boolean isYear(){
        return dates.size() == 365;
    }

    public static class DateData{
        public Date date;
        float totalEnergy = 0;
        float totalPrice = 0;

        public  DateData(Date date){
            this.date = date;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public float getTotalEnergy() {
            return totalEnergy;
        }

        public void setTotalEnergy(float totalEnergy) {
            this.totalEnergy = totalEnergy;
        }

        public float getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(float totalPrice) {
            this.totalPrice = totalPrice;
        }
    }
}
