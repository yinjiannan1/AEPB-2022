package com.example.AEPB.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class parkingLot {
    private List<Vehicle> ParkingLotList;

    public List<Vehicle> getParkingLotList() {
        return ParkingLotList;
    }

    public void setParkingLotList(List<Vehicle> parkingLotList) {
        ParkingLotList = parkingLotList;
    }
}
