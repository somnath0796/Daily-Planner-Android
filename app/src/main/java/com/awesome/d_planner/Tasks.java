package com.awesome.d_planner;

/**
 * To create Tasks Objects
 */
public class Tasks {

    private int _id;
    private String _name;
    private String _date;
    private String _time;
    private String _type;
    private boolean _completion;

    public Tasks(){}

    public Tasks(String name, String date, String time,  String type) {
        this._name = name;
        this._time = time;
        this._date = date;
        this._type = type;
        this._completion = false;
    }

    public int get_completion() {
        if(_completion){
            return 1;
        }
        else{
            return 0;
        }
    }

    public boolean isChecked(){
        return _completion;
    }

    //getters
    public String get_date() {
        return _date;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_time() {
        return _time;
    }

    public String get_type() {
        return _type;
    }

    //setters
    public void set_completion(Boolean _completion) {
        this._completion = _completion;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public void toggleComp(){
        _completion = !_completion;
    }

    @Override
    public String toString() {
        return get_name();
    }
}
