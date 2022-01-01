package com.example.de;

public class User {
    int _id;
    String _name;
    String _phone_number;
    String _email;
    String _password;
    String _logStatus;
    public User(){   }
    public User(int id, String name, String _phone_number, String email, String password, String _logStatus){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._email = email;
        this._password = password;
        this._logStatus = _logStatus;
    }

    public User(String name, String _phone_number, String email, String password, String _logStatus){
        this._name = name;
        this._phone_number = _phone_number;
        this._email = email;
        this._password = password;
        this._logStatus = _logStatus;
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

    public String get_logStatus() {
        return _logStatus;
    }

    public void set_logStatus(String _logStatus) {
        this._logStatus = _logStatus;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getPhoneNumber(){
        return this._phone_number;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }
}
