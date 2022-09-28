package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLot;
import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SmartParkingBoyTest.class)
class SmartParkingBoyTest {

    SmartParkingBoy smartParkingBoy = new SmartParkingBoy();



    @Test
    void should_be_parking_success_when_try_to_park_given_an_vehicle_with_car_plate_number_not_empty() {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = smartParkingBoy.parkingVehicle(vehicle);
        //then
        assertTrue(parkingLotStatus.isSuccess());
        assertTrue(parkingLotStatus.getTicket().isEnabled());
        assertEquals("京A12345", parkingLotStatus.getTicket().getVehicle().getCarPlateNumber());
    }

    @Test
    void should_return_parking_failed_when_try_to_parking_given_an_vehicle_without_car_plate_number_and_parking_lot_exist() {
        //given
        Vehicle vehicle = new Vehicle();
        //when
        ParkingLotStatus parkingLotStatus = smartParkingBoy.parkingVehicle(vehicle);
        //then
        assertFalse(parkingLotStatus.isSuccess());
    }

    @Test
    void should_return_picking_success_when_try_to_picking_given_an_useful_ticket_and_vehicle_has_parked(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        Ticket ticket = new Ticket(vehicle, true);
        Map<Ticket, Vehicle> ticketVehicleHashMap = smartParkingBoy.getTicketVehicleHashMap();
        List<ParkingLot> parkingLotList = smartParkingBoy.getParkingLotList();
        parkingLotList.get(0).getVehicleList().add(vehicle);
        ticketVehicleHashMap.put(ticket, vehicle);
        smartParkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
        //when
        ParkingLotStatus pickingStatus = smartParkingBoy.pickingVehicle(ticket);
        //then
        assertTrue(pickingStatus.isSuccess());
    }

    @Test
    void should_return_ticket_when_parked_vehicle_given_an_vehicle_parked(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = smartParkingBoy.parkingVehicle(vehicle);
        //then
        Ticket ticket = new Ticket(vehicle,true);
        assertEquals(ticket.getVehicle(), parkingLotStatus.getTicket().getVehicle());
    }

    @Test
    void should_return_false_when_try_to_park_given_a_vehicle_with_plate_number_and_plateLot_is_full(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 100; i++) {
                Vehicle vehicle1 = new Vehicle();
                vehicle1.setCarPlateNumber(String.valueOf(i));
                Ticket ticket = new Ticket(vehicle1, false);
                Map<Ticket, Vehicle> ticketVehicleHashMap = smartParkingBoy.getTicketVehicleHashMap();
                List<ParkingLot> parkingLotList = smartParkingBoy.getParkingLotList();
                parkingLotList.get(j).getVehicleList().add(vehicle);
                ticketVehicleHashMap.put(ticket, vehicle1);
                smartParkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
            }
        }
        //when
        ParkingLotStatus parkingLotStatus = smartParkingBoy.parkingVehicle(vehicle);
        //then
        assertFalse(parkingLotStatus.isSuccess());
    }

    @Test
    void should_return_false_when_try_to_park_when_a_vehicle_with_same_plate_number_in_parking_lot(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        Ticket ticket = new Ticket(vehicle, true);
        Map<Ticket, Vehicle> ticketVehicleHashMap = smartParkingBoy.getTicketVehicleHashMap();
        List<ParkingLot> parkingLotList = smartParkingBoy.getParkingLotList();
        parkingLotList.get(0).getVehicleList().add(vehicle);
        ticketVehicleHashMap.put(ticket, vehicle);
        smartParkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
        Vehicle vehicleAnother = new Vehicle();
        vehicleAnother.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = smartParkingBoy.parkingVehicle(vehicleAnother);
        //then
        assertFalse(parkingLotStatus.isSuccess());

    }

    @Test
    void should_return_false_when_picking_vehicle_given_an_vehicle_parked_and_get_a_ticket(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        Ticket ticket = new Ticket(vehicle, true);
        Map<Ticket, Vehicle> ticketVehicleHashMap = smartParkingBoy.getTicketVehicleHashMap();
        List<ParkingLot> parkingLotList = smartParkingBoy.getParkingLotList();
        parkingLotList.get(0).getVehicleList().add(vehicle);
        ticketVehicleHashMap.put(ticket, vehicle);
        smartParkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
        //when
        smartParkingBoy.pickingVehicle(ticket);
        //then
        assertFalse(ticket.isEnabled());
    }

    @Test
    void should_return_false_when_try_to_pick_vehicle_given_an_unuseful_ticket() {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        Ticket ticket = new Ticket(vehicle, false);
        Map<Ticket, Vehicle> ticketVehicleHashMap = smartParkingBoy.getTicketVehicleHashMap();
        List<ParkingLot> parkingLotList = smartParkingBoy.getParkingLotList();
        parkingLotList.get(0).getVehicleList().add(vehicle);
        ticketVehicleHashMap.put(ticket, vehicle);
        smartParkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
        //when
        ParkingLotStatus parkingLotStatus = smartParkingBoy.pickingVehicle(ticket);
        assertFalse(parkingLotStatus.isSuccess());
    }

    @Test
    void should_return_parking_success_in_A_when_try_to_park_given_an_parking_boy_and_A_with_empty_parking_lot () {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = smartParkingBoy.parkingVehicle(vehicle);
        //then
        assertTrue(parkingLotStatus.isSuccess());
        assertEquals("A", parkingLotStatus.getParkingLot().getLotName());
    }

    @Test
    void should_return_parking_success_in_B_when_try_to_park_given_an_parking_boy_and_A_is_full_B_with_empty_parking_lot () {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setCarPlateNumber("京A2345");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setCarPlateNumber("京A1245");

                Ticket ticket = new Ticket(vehicle, false);
                Ticket ticket1 = new Ticket(vehicle, false);
                Map<Ticket, Vehicle> ticketVehicleHashMap = smartParkingBoy.getTicketVehicleHashMap();
                List<ParkingLot> parkingLotList = smartParkingBoy.getParkingLotList();
                parkingLotList.get(0).getVehicleList().add(vehicle);
                parkingLotList.get(1).getVehicleList().add(vehicle1);
                ticketVehicleHashMap.put(ticket, vehicle);
                ticketVehicleHashMap.put(ticket1, vehicle1);
                smartParkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);


        //when
        ParkingLotStatus parkingLotStatus = smartParkingBoy.parkingVehicle(vehicle2);
        //then
        assertTrue(parkingLotStatus.isSuccess());
        assertEquals("C", parkingLotStatus.getParkingLot().getLotName());
    }

    @Test
    void should_return_parking_success_when_try_to_parking_given_B_parking_lot_has_largest_site() {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setCarPlateNumber("京A2345");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setCarPlateNumber("京A1245");

        Ticket ticket = new Ticket(vehicle, false);
        Ticket ticket1 = new Ticket(vehicle, false);
        Map<Ticket, Vehicle> ticketVehicleHashMap = smartParkingBoy.getTicketVehicleHashMap();
        List<ParkingLot> parkingLotList = smartParkingBoy.getParkingLotList();
        parkingLotList.get(0).getVehicleList().add(vehicle);
        parkingLotList.get(2).getVehicleList().add(vehicle1);
        ticketVehicleHashMap.put(ticket, vehicle);
        ticketVehicleHashMap.put(ticket1, vehicle1);
        smartParkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);


        //when
        ParkingLotStatus parkingLotStatus = smartParkingBoy.parkingVehicle(vehicle2);
        //then
        assertTrue(parkingLotStatus.isSuccess());
        assertEquals("B", parkingLotStatus.getParkingLot().getLotName());
    }

}
