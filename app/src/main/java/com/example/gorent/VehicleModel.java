package com.example.gorent;

public class VehicleModel {
//
    private int id;
    private String plateNo;
    private String model;
    private int year;
    private String type;
    private String city;
    private String description;
    private int rent;


    public VehicleModel(int id, String plateNo, String model, int year, String type, String city, String description, int rent) {
        this.id = id;
        this.plateNo = plateNo;
        this.model = model;
        this.year = year;
        this.type = type;
        this.city = city;
        this.description = description;
        this.rent = rent;
    }

    @Override
    public String toString() {
        return "VehicleModel{" +
                "id=" + id +
                ", plateNo='" + plateNo + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", type='" + type + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", rent=" + rent +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }


    public int getRent() {
        return rent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setRent(int rent) {
        this.rent = rent;
    }

}
