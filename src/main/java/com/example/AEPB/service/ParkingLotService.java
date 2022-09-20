package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLot;
import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ParkingLotService {

    private static final int PARKING_LOT_MAX_SIZE = 200;
    private ParkingLot parkingLot = new ParkingLot(new ArrayList<>());
    private static final HashMap<Ticket, Vehicle> map = new HashMap<>();

    public ParkingLotStatus parkingVehicle(Vehicle vehicle) {
        ParkingLotStatus parkingLotStatus = new ParkingLotStatus();
        List<Vehicle> vehicles = parkingLot.getParkingLotList();
        if(Objects.isNull(vehicle) || vehicle.getCarPlateNumber() == null){
            parkingLotStatus.setSuccess(false);
            return parkingLotStatus;
        }
        vehicles.add(vehicle);
        if(vehicles.size() > PARKING_LOT_MAX_SIZE){
            parkingLotStatus.setSuccess(false);
            return parkingLotStatus;
        }
        Ticket ticket = new Ticket(vehicle, true);
        map.put(ticket, vehicle);
        parkingLotStatus.setSuccess(true);
        parkingLotStatus.setTicket(ticket);
        return parkingLotStatus;
    }

    public ParkingLotStatus pickingVehicle(Ticket ticket) {
        Vehicle vehicle = ticket.getVehicle();
        return null;
    }

}
