package com.awesome.d_planner;

/**
 * To create Tasks Objects
 */
public class Reminders {

    private int _id;
    private String _val;
    private String _date;
    private String _time;

    public Reminders(){}

    public Reminders(String val, String date, String time) {
        this._val = val;
        this._date = date;
        this._time = time;
    }

    //getters
    public int get_id() {
        return _id;
    }

    public String get_time() {
        return _time;
    }

    public String get_val() {
        return _val;
    }

    public String get_date() {
        return _date;
    }


    //setters
    public void set_date(String _date) {
        this._date = _date;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public void set_val(String _val) {
        this._val = _val;
    }

    @Override
    public String toString() {
        return get_val();
    }
}
