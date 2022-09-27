package com.example.AEPB.entity;

import java.util.List;

public class ParkingLot {
    private List<Vehicle> vehicleList;

    private int size = 100;

    private int order;

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOrder() {
        return order;
    }

    public int getVehicleNumber() {
        return this.vehicleList.size();
    }

    public void setOrder(int order) {
        this.order = order;
    }

    private String lotName;

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public ParkingLot(String lotName, int size, List<Vehicle> vehicleList, int order) {
        this.vehicleList = vehicleList;
        this.lotName = lotName;
        this.size = size;
        this.order = order;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public void pickUpVehicle(Vehicle vehicle) {
        for (int i = 0; i < vehicleList.size(); i++) {
            if(vehicleList.get(i).equals(vehicle)){
                vehicleList.remove(i);
            }
        }
    }

    public boolean hasSameVehicle(Vehicle vehicle) {
        for (int i = 0; i < vehicleList.size(); i++) {
            if(vehicleList.get(i).getCarPlateNumber().equals(vehicle.getCarPlateNumber())){
                return true;
            }
        }
        return false;
    }
}
