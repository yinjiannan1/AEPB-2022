package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class ParkingLotService {

    private static final int PARKING_LOT_MAX_SIZE = 200;

    public ParkingLotStatus parkingVehicle(Vehicle vehicle) {
        ParkingLotStatus parkingLotStatus = new ParkingLotStatus();
        if(Objects.isNull(vehicle) || vehicle.getCarPlateNumber() == null){
            parkingLotStatus.setSuccess(false);
            return parkingLotStatus;
        }
        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEnabled(true);
        parkingLotStatus.setSuccess(true);
        parkingLotStatus.setTicket(ticket);
        return parkingLotStatus;
    }

    public ParkingLotStatus pickingVehicle(Ticket ticket) {
        return null;
    }

}
