package com.awesome.d_planner;

/**
 * To create Notes Objects
 */
public class Notes {
    private int _id;
    private String _title;
    private String _noteVal;

    public Notes(){ }

    public Notes(String title, String noteVal) {
        this._title = title;
        this._noteVal = noteVal;
    }

    //setters
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_noteVal(String _noteVal) {
        this._noteVal = _noteVal;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    //getters
    public int get_id() {
        return _id;
    }

    public String get_noteVal() {
        return _noteVal;
    }

    public String get_title() {
        return _title;
    }

    @Override
    public String toString() {
        return  get_title();
    }
}
