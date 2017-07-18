package com.example.gkudva.fsquare.Model;

/**
 * Created by gkudva on 16/07/17.
 */

final public class Person {
    private int id;
    private String name;
    private long arriveTime;
    private long leaveTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(int arriveTime) {
        this.arriveTime = arriveTime;
    }

    public long getLeaveTime() {
        return leaveTime;
    }

    public Person( String name, long arriveTime, long leaveTime) {
        this.id = 0;
        this.name = name;
        this.arriveTime = arriveTime;
        this.leaveTime = leaveTime;
    }

    public void setLeaveTime(int leaveTime) {
        this.leaveTime = leaveTime;
    }
}
