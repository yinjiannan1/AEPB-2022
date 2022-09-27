package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLot;
import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SmartParkingBoy extends ParkingBoy{

    private List<ParkingLot> parkingLotList = new ArrayList<>();
    private HashMap<Ticket, Vehicle> ticketVehicleHashMap = new HashMap<>();

    public SmartParkingBoy() {
        this.parkingLotList.add(new ParkingLot("A", 100, new ArrayList<>(), 1));
        this.parkingLotList.add(new ParkingLot("B", 100, new ArrayList<>(), 2));
        this.parkingLotList.add(new ParkingLot("C", 100, new ArrayList<>(), 3));
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }

    public void setParkingLotList(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public HashMap<Ticket, Vehicle> getTicketVehicleHashMap() {
        return ticketVehicleHashMap;
    }

    public void setTicketVehicleHashMap(HashMap<Ticket, Vehicle> ticketVehicleHashMap) {
        this.ticketVehicleHashMap = ticketVehicleHashMap;
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

    public ParkingLotStatus pickingVehicle(Ticket ticket) {
        ParkingLotStatus parkingLotStatus = new ParkingLotStatus();
        if(ticketVehicleHashMap.containsKey(ticket) && ticket.isEnabled()){
            Vehicle vehicle = ticketVehicleHashMap.get(ticket);
            ParkingLot parkingLot = findVehicleInWhichLot(vehicle);
            parkingLot.pickUpVehicle(vehicle);
            ticket.setEnabled(false);
            ticketVehicleHashMap.remove(ticket);
            parkingLotStatus.setSuccess(true);
            return parkingLotStatus;
        }
        parkingLotStatus.setSuccess(false);
        System.out.println("取车失败");
        return parkingLotStatus;
    }

    private ParkingLot findVehicleInWhichLot(Vehicle vehicle) {
        for (ParkingLot lot : this.parkingLotList) {
            if(lot.getVehicleList().contains(vehicle)){
                return lot;
            }
        }
        return null;
    }
}
