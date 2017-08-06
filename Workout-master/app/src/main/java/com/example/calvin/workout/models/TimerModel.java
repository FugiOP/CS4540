package com.example.calvin.workout.models;

/**
 * Created by FugiBeast on 8/6/2017.
 */

public class TimerModel {
    String time;
    String name;
    long pos;

    public TimerModel(String time, String name, long pos) {
        this.time = time;
        this.name = name;
        this.pos = pos;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }
}
