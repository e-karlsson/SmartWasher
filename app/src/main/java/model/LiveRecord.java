package model;

/**
 * Created by xxottosl on 2015-04-09.
 */
public class LiveRecord {

  int state;
  float energy;
  ProgramInfo programInfo;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public ProgramInfo getProgramInfo() {
        return programInfo;
    }

    public void setProgramInfo(ProgramInfo programInfo) {
        this.programInfo = programInfo;
    }
}
