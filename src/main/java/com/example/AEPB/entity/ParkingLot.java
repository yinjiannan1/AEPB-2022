package com.example.AEPB.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private List<Vehicle> ParkingLotList;

    public List<Vehicle> getParkingLotList() {
        return ParkingLotList;
    }

    public ParkingLot(List<Vehicle> parkingLotList) {
        ParkingLotList = parkingLotList;
    }

    public void setParkingLotList(List<Vehicle> parkingLotList) {
        ParkingLotList = parkingLotList;
    }
}
