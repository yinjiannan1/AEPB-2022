import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public ParkingLot findCorrectLot() throws Exception {
    List<ParkingLot> parkingLotList = new ArrayList<>();
    for (ParkingLot lot : parkingBoy.parkingLotList) {
        if (lot.getVehicleList().size() < lot.getSize()) {
            return lot;
        }
        parkingLotList.add(lot);
    }
    Collections.sort(parkingLotList, Comparator.comparing(ParkingLot::getEmptyRatio));
    return parkingLotList.stream().findFirst().orElse(null);
}
</snippet>

<<<<<<< APPEND (index=1)
public ParkingLot findCorrectLot() throws Exception { ... }
====
public ParkingLot findCorrectLot() throws Exception { ... }
====

    ParkingBoy parkingBoy = new ParkingBoy();

    public ParkingLotStatus parkingVehicle(Vehicle vehicle) {

        ParkingLotStatus parkingLotStatus = new ParkingLotStatus();
        if(!parkingBoy.isExistEmptyLot()){
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

    public ParkingLotStatus pickingVehicle(Ticket ticket){
       return parkingBoy.pickingVehicle(ticket);
    }

    public ParkingLot findCorrectLot() {
        parkingBoy.parkingLotList.sort(Comparator.comparing(ParkingLot::getVehicleNumber));
        for (ParkingLot lot : parkingBoy.parkingLotList) {
            if (lot.getVehicleList().size() < lot.getSize()) {
                return lot;
            }
        }
        return null;
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

    public Map<Ticket, Vehicle> getTicketVehicleHashMap() {
        return parkingBoy.getTicketVehicleHashMap();
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingBoy.getParkingLotList();
    }

    public void setTicketVehicleHashMap(Map<Ticket, Vehicle> ticketVehicleHashMap) {
        parkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
    }
}
