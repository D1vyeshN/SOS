package com.example.de;

public class Contact {
    int _id;
    String _name;
    String _phone_number;
    String _status;
    public Contact(){   }
    public Contact(int id, String name, String _phone_number, String status){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._status = status;
    }

    public Contact(String name, String _phone_number, String status){
        this._name = name;
        this._phone_number = _phone_number;
        this._status = status;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getPhoneNumber(){
        return this._phone_number;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }



}
