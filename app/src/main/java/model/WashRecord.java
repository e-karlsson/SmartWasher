package model;

import java.util.List;

/**
 * Created by xxottosl on 2015-04-09.
 */
public class WashRecord {

    long endTime;
    long startTime;
    float totalEnergy;
    float price;
    List<EnergyPoint> points;

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public float getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(float totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<EnergyPoint> getPoints() {
        return points;
    }

    public void setPoints(List<EnergyPoint> points) {
        this.points = points;
    }
}
