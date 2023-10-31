package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLot;
import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;

import java.util.*;

public class ParkingRobot{

    ParkingBoy parkingBoy = new ParkingBoy();

    public ParkingLotStatus parkingVehicle(Vehicle vehicle){
        ParkingLotStatus parkingLotStatus = new ParkingLotStatus();
        if(!parkingBoy.isExistEmptyLot()){
            System.out.println("车库已满！");
            parkingLotStatus.setSuccess(false);
            return parkingLotStatus;
        }
        ParkingLot parkingLot = findCorrectLot();
        List<Vehicle> vehicles = parkingLot.getVehicleList();
        if(Objects.isNull(vehicle)){
            parkingLotStatus.setSuccess(false);
            System.out.println("车牌不能为空！");
            return parkingLotStatus;
        }
        if(hasSameVehicle(vehicle)){
            parkingLotStatus.setSuccess(false);
            System.out.println("车牌重复！已报警");
            return parkingLotStatus;
        }
        Ticket ticket = new Ticket(vehicle, true);
        parkingBoy.ticketVehicleHashMap.put(ticket, vehicle);
        vehicles.add(vehicle);
        parkingLotStatus.setSuccess(true);
        parkingLotStatus.setTicket(ticket);
        parkingLotStatus.setParkingLot(parkingLot);
        return parkingLotStatus;
    }

    public Map<Ticket, Vehicle> getTicketVehicleHashMap() {
        return parkingBoy.getTicketVehicleHashMap();
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingBoy.getParkingLotList();
    }

    public void setTicketVehicleHashMap(Map<Ticket, Vehicle> ticketVehicleHashMap) {
        parkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
    }


    public boolean hasSameVehicle(Vehicle vehicle) {
        List<ParkingLot> parkingLotList = parkingBoy.getParkingLotList();
        for (ParkingLot lot: parkingLotList) {
            if(lot.hasSameVehicle(vehicle)){
                return true;
            }
        }
        return false;
    }

    public ParkingLot findCorrectLot() {
        parkingBoy.getParkingLotList().sort(Comparator.comparing(ParkingLot::getEmptyRatio));
        for (ParkingLot lot : parkingBoy.getParkingLotList()) {
            if (lot.getVehicleList().size() < lot.getSize()) {
                return lot;
            }
        }
        return null;
    }

}
