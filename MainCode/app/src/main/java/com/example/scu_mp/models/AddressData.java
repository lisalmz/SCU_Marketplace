package com.example.scu_mp.models;

public class AddressData {

    private String firstName;
    private String phonenumber1;
    private String address;
    private String cityname;
    private String pincodeno;
    private String statename;
    private String country1;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    AddressData(){}

    public AddressData(String firstName, String phonenumber1, String address, String cityname, String pincodeno, String statename, String country1, String userid) {
        this.firstName = firstName;
        this.phonenumber1 = phonenumber1;
        this.address = address;
        this.cityname = cityname;
        this.pincodeno = pincodeno;
        this.statename = statename;
        this.country1 = country1;
        this.userid = userid;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public String getPhonenumber() {
        return phonenumber1;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber1 = phonenumber;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public void setPincodeno(String pincodeno) {
        this.pincodeno = pincodeno;
    }

    public String getCityname() {
        return cityname;
    }

    public String getStatename() {
        return statename;
    }

    public String getPincodeno() {
        return pincodeno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return
                  firstName  +'\n' +address+","+ cityname + '\n'+ statename  +","+ pincodeno  +'\n'+ phonenumber1;
    }
}
