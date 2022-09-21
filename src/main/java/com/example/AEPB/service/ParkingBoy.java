package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLot;
import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParkingBoy {

    private List<ParkingLot> parkingLotList = new ArrayList<>();
    private static final HashMap<Ticket, Vehicle> map = new HashMap<>();

    public ParkingBoy() {
        this.parkingLotList.add(new ParkingLot("A", 100, new ArrayList<>(), 1));
        this.parkingLotList.add(new ParkingLot("B", 100, new ArrayList<>(), 2));
        this.parkingLotList.add(new ParkingLot("C", 100, new ArrayList<>(), 3));
    }

    public ParkingLotStatus parkingVehicle(Vehicle vehicle) {
        ParkingLotStatus parkingLotStatus = new ParkingLotStatus();
        if(!isExistEmptyLot()){
            System.out.println("车库已满！");
            parkingLotStatus.setSuccess(false);
            return parkingLotStatus;
        }
        ParkingLot parkingLot = findFirstHasEmptyLot();
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
        map.put(ticket, vehicle);
        vehicles.add(vehicle);
        parkingLotStatus.setSuccess(true);
        parkingLotStatus.setTicket(ticket);
        parkingLotStatus.setParkingLot(parkingLot);
        return parkingLotStatus;
    }

    public ParkingLotStatus pickingVehicle(Ticket ticket) {
        ParkingLotStatus parkingLotStatus = new ParkingLotStatus();
        if(map.containsKey(ticket)){
            Vehicle vehicle = map.get(ticket);
            ParkingLot parkingLot = findVehicleInWhichLot(vehicle);
            parkingLot.pickUpVehicle(vehicle);
            ticket.setEnabled(false);
            map.remove(ticket);
            parkingLotStatus.setSuccess(true);
            return parkingLotStatus;
        }
        parkingLotStatus.setSuccess(false);
        System.out.println("取车失败");
        return parkingLotStatus;
    }

    private ParkingLot findFirstHasEmptyLot() {
        this.parkingLotList.sort(Comparator.comparing(ParkingLot::getOrder));
        for (ParkingLot lot : this.parkingLotList) {
            if (lot.getVehicleList().size() < lot.getSize()) {
                return lot;
            }
        }
        return null;
    }

    private boolean isExistEmptyLot() {
        for (ParkingLot lot : this.parkingLotList) {
            if (lot.getVehicleList().size() >= lot.getSize()) {
                return false;
            }
        }
        return true;
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
