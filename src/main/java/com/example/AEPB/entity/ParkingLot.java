package com.example.AEPB.entity;

import java.util.List;

public class ParkingLot {
    private List<Vehicle> parkingLotList;

    public List<Vehicle> getParkingLotList() {
        return parkingLotList;
    }

    public ParkingLot(List<Vehicle> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public void setParkingLotList(List<Vehicle> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public void pickUpVehicle(Vehicle vehicle) {
        for (int i = 0; i < parkingLotList.size(); i++) {
            if(parkingLotList.get(i).equals(vehicle)){
                parkingLotList.remove(i);
            }
        }
    }
}
