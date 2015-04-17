package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import extra.Timer;

/**
 * Created by xxottosl on 2015-04-09.
 */
public class WashRecord {

    float totalEnergy;
    float price;
    List<EnergyPoint> points;
    ProgramInfo programInfo;

    public ProgramInfo getProgramInfo() {
        return programInfo;
    }

    public void setProgramInfo(ProgramInfo programInfo) {
        this.programInfo = programInfo;
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

    @JsonIgnore
    public float getCost(){
       return getTotalEnergy() * getPrice() / 1000 / 3600;
    }

    @JsonIgnore
    public float getKiloWattHours(){
        return getTotalEnergy() / 1000/ 3600;
    }

    @JsonIgnore
    public String getRunDateString(){
        Timer.TimeInfo ti = Timer.translate(programInfo.getStartTime());

        return ti.getDay()+" "+ti.getMonth()+"    "+ti.getHour()+":"+ti.getMinute();
    }

}
