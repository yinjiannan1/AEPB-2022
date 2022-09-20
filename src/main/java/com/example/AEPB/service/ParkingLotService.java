package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotService {
    private static final int PARKING_LOT_MAX_SIZE = 200;


    public ParkingLotStatus parkingVehicle(Vehicle vehicle) {
        return null;
    }

    public ParkingLotStatus pickingVehicle(Ticket ticket) {
        return null;
    }

}
