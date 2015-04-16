package model;

/**
 * Created by xxkarlue on 2015-04-15.
 */
public class ProgramInfo {

    long startTime;
    long doneTime;
    boolean wind;
    boolean lowPrice;
    String name;
    String degree;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(long doneTime) {
        this.doneTime = doneTime;
    }

    public boolean isWind() {
        return wind;
    }

    public void setWind(boolean wind) {
        this.wind = wind;
    }

    public boolean isLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(boolean lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
