package com.arek.clock_utils;

@SuppressWarnings("unused")
public class Time {
    private long hours;
    private long minutes;
    private long seconds;

    public Time(){}

    public Time(long hours, long minutes, long seconds){
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public Time(long minutes){
        this.hours = minutes / 60;
        this.minutes = minutes % 60;
    }


    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getTimeConvertedToSeconds(){
        return hours * 3600 + minutes * 60 + seconds;
    }

    public void updateTimeAfterOneSecond(){

        if(seconds > 0){
            --seconds;
        } else {
            if(minutes > 0){
                --minutes;
                seconds = 59;
            } else{
                if(hours > 0){
                    --hours;
                    minutes = 59;
                    seconds = 59;
                }
            }
        }
    }

}
