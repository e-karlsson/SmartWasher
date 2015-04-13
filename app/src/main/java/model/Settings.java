package model;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by xxottosl on 2015-04-10.
 */
public class Settings {

    boolean push;
    int reminderTime;
    @JsonProperty("static")
    boolean staticPrice;
    float price;

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public int getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(int reminderTime) {
        this.reminderTime = reminderTime;
    }

    public boolean isStaticPrice() {
        return staticPrice;
    }

    public void setStaticPrice(boolean staticPrice) {
        this.staticPrice = staticPrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
