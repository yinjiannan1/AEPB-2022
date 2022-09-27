package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLot;
import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SmartParkingBoy extends ParkingBoy{


    public SmartParkingBoy() {
        super();
    }

    public ParkingLotStatus smartParkingVehicle(Vehicle vehicle) {

        ParkingLotStatus parkingLotStatus = new ParkingLotStatus();
        if(!isExistEmptyLot()){
            System.out.println("车库已满！");
            parkingLotStatus.setSuccess(false);
            return parkingLotStatus;
        }
        ParkingLot parkingLot = findCorrectLot();
        List<Vehicle> vehicles = parkingLot.getVehicleList();
        if(Objects.isNull(vehicle) || vehicle.getCarPlateNumber() == null){
            parkingLotStatus.setSuccess(false);
            System.out.println("车牌不能为空！");
            return parkingLotStatus;
        }
        if(parkingLot.hasSameVehicle(vehicle)){
            parkingLotStatus.setSuccess(false);
            System.out.println("车牌重复！已报警");
            return parkingLotStatus;
        }
        Ticket ticket = new Ticket(vehicle, true);
        ticketVehicleHashMap.put(ticket, vehicle);
        vehicles.add(vehicle);
        parkingLotStatus.setSuccess(true);
        parkingLotStatus.setTicket(ticket);
        parkingLotStatus.setParkingLot(parkingLot);
        return parkingLotStatus;
    }

    public ParkingLot findCorrectLot() {
        this.parkingLotList.sort(Comparator.comparing(ParkingLot::getVehicleNumber));
        for (ParkingLot lot : this.parkingLotList) {
            if (lot.getVehicleList().size() < lot.getSize()) {
                return lot;
            }
        }
        return null;
    }
}
