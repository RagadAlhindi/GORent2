package com.example.gorent;

public class VehicleModel {

    private int id;
    private String plateNo;
    private String model;
    private String type;
    private String description;
    private String location;
    private int rent;


    public VehicleModel(int id, String plateNo, String model, String type, String location ,String description, int rent) {
        this.id = id;
        this.plateNo = plateNo;
        this.model = model;
        this.location = location;
        this.type = type;
        this.description = description;
        this.rent = rent;
    }

    @Override
    public String toString() {
        return "VehicleModel{" +
                "id=" + id +
                ", plateNo='" + plateNo + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
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
    public String getType() {
        return type;
    }
    public String getLocation() {
        return location;
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



}
