package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLot;
import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ParkingLotTest.class)
public class ParkingLotTest {

    private static final int PARKING_LOT_MAX_SIZE = 300;
    ParkingBoy parkingBoy = new ParkingBoy();



    @Test
    void should_be_parking_success_when_try_to_park_given_an_vehicle_with_car_plate_number_not_empty() {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicle);
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
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicle);
        //then
        assertFalse(parkingLotStatus.isSuccess());
    }

    @Test
    void should_return_picking_success_when_try_to_picking_given_an_useful_ticket_and_vehicle_has_parked(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicle);
        //when
        ParkingLotStatus pickingStatus = parkingBoy.pickingVehicle(parkingLotStatus.getTicket());
        //then
        assertTrue(pickingStatus.isSuccess());
    }

    @Test
    void should_return_ticket_when_parked_vehicle_given_an_vehicle_parked(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicle);
        //then
        Ticket ticket = new Ticket(vehicle,true);
        assertEquals(ticket.getVehicle(), parkingLotStatus.getTicket().getVehicle());
    }

    @Test
    void should_return_false_when_try_to_park_given_a_vehicle_with_plate_number_and_plateLot_is_full(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        for (int i = 0; i < PARKING_LOT_MAX_SIZE; i++) {
            Vehicle vehicle1 = new Vehicle();
            vehicle1.setCarPlateNumber(String.valueOf(i));
            parkingBoy.parkingVehicle(vehicle1);
        }
        //when
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicle);
        //then
        assertFalse(parkingLotStatus.isSuccess());
    }

    @Test
    void should_return_false_when_try_to_park_when_a_vehicle_with_same_plate_number_in_parking_lot(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        parkingBoy.parkingVehicle(vehicle);
        Vehicle vehicleAnother = new Vehicle();
        vehicleAnother.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicleAnother);
        //then
        assertFalse(parkingLotStatus.isSuccess());

    }

    @Test
    void should_return_false_when_picking_vehicle_given_an_vehicle_parked_and_get_a_ticket(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        Ticket ticket = new Ticket(vehicle, true);
        HashMap<Ticket, Vehicle> ticketVehicleHashMap = parkingBoy.getTicketVehicleHashMap();
        List<ParkingLot> parkingLotList = parkingBoy.getParkingLotList();
        parkingLotList.get(0).getVehicleList().add(vehicle);
        ticketVehicleHashMap.put(ticket, vehicle);
        parkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
        //when
        parkingBoy.pickingVehicle(ticket);
        //then
        assertFalse(ticket.isEnabled());
    }

    @Test
    void should_return_false_when_try_to_pick_vehicle_given_an_unuseful_ticket() {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        Ticket ticket = new Ticket(vehicle, false);
        HashMap<Ticket, Vehicle> ticketVehicleHashMap = parkingBoy.getTicketVehicleHashMap();
        List<ParkingLot> parkingLotList = parkingBoy.getParkingLotList();
        parkingLotList.get(0).getVehicleList().add(vehicle);
        ticketVehicleHashMap.put(ticket, vehicle);
        parkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
        //when
        ParkingLotStatus parkingLotStatus = parkingBoy.pickingVehicle(ticket);
        assertFalse(parkingLotStatus.isSuccess());
    }

    @Test
    void should_return_parking_success_in_A_when_try_to_park_given_an_parking_boy_and_A_with_empty_parking_lot () {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicle);
        //then
        assertTrue(parkingLotStatus.isSuccess());
        assertEquals("A", parkingLotStatus.getParkingLot().getLotName());
    }

    @Test
    void should_return_parking_success_in_B_when_try_to_park_given_an_parking_boy_and_A_is_full_B_with_empty_parking_lot () {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        for (int i = 0; i < 100; i++) {
            Vehicle vehicle1 = new Vehicle();
            vehicle1.setCarPlateNumber(String.valueOf(i));
            Ticket ticket = new Ticket(vehicle, false);
            HashMap<Ticket, Vehicle> ticketVehicleHashMap = parkingBoy.getTicketVehicleHashMap();
            List<ParkingLot> parkingLotList = parkingBoy.getParkingLotList();
            parkingLotList.get(0).getVehicleList().add(vehicle);
            ticketVehicleHashMap.put(ticket, vehicle);
            parkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
        }
        //when
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicle);
        //then
        assertTrue(parkingLotStatus.isSuccess());
        assertEquals("B", parkingLotStatus.getParkingLot().getLotName());
    }

    @Test
    void should_return_parking_success_in_C_when_try_to_park_given_an_parking_boy_and_AB_is_full_C_with_empty_parking_lot () {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 100; i++) {
                Vehicle vehicle1 = new Vehicle();
                vehicle1.setCarPlateNumber(String.valueOf(i));
                Ticket ticket = new Ticket(vehicle1, false);
                HashMap<Ticket, Vehicle> ticketVehicleHashMap = parkingBoy.getTicketVehicleHashMap();
                List<ParkingLot> parkingLotList = parkingBoy.getParkingLotList();
                parkingLotList.get(j).getVehicleList().add(vehicle);
                ticketVehicleHashMap.put(ticket, vehicle1);
                parkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
            }
        }
        //when
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicle);
        //then
        assertTrue(parkingLotStatus.isSuccess());
        assertEquals("C", parkingLotStatus.getParkingLot().getLotName());
    }

    @Test
    void should_return_false_when_try_to_park_given_an_parking_boy_and_ABC_is_full () {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 100; i++) {
                Vehicle vehicle1 = new Vehicle();
                vehicle1.setCarPlateNumber(String.valueOf(i));
                Ticket ticket = new Ticket(vehicle1, false);
                HashMap<Ticket, Vehicle> ticketVehicleHashMap = parkingBoy.getTicketVehicleHashMap();
                List<ParkingLot> parkingLotList = parkingBoy.getParkingLotList();
                parkingLotList.get(j).getVehicleList().add(vehicle);
                ticketVehicleHashMap.put(ticket, vehicle1);
                parkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
            }
        }
        //when
        ParkingLotStatus parkingLotStatus = parkingBoy.parkingVehicle(vehicle);
        //then
        assertFalse(parkingLotStatus.isSuccess());
    }

    @Test
    void should_return_true_when_try_to_pick_given_an_valid_ticket () {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        Ticket ticket = new Ticket(vehicle, true);
        HashMap<Ticket, Vehicle> ticketVehicleHashMap = parkingBoy.getTicketVehicleHashMap();
        List<ParkingLot> parkingLotList = parkingBoy.getParkingLotList();
        parkingLotList.get(0).getVehicleList().add(vehicle);
        ticketVehicleHashMap.put(ticket, vehicle);
        parkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
        //when
        ParkingLotStatus pickingLotStatus = parkingBoy.pickingVehicle(ticket);
        //then
        assertTrue(pickingLotStatus.isSuccess());
        assertFalse(ticket.isEnabled());
    }

    @Test
    void should_return_false_when_try_to_pick_given_ticket_with_another_ticket() {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        Ticket ticket = new Ticket(vehicle, false);
        HashMap<Ticket, Vehicle> ticketVehicleHashMap = parkingBoy.getTicketVehicleHashMap();
        List<ParkingLot> parkingLotList = parkingBoy.getParkingLotList();
        parkingLotList.get(0).getVehicleList().add(vehicle);
        ticketVehicleHashMap.put(ticket, vehicle);
        parkingBoy.setTicketVehicleHashMap(ticketVehicleHashMap);
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setCarPlateNumber("京12345");
        Ticket ticket1 = new Ticket(vehicle1, true);
        //when
        ParkingLotStatus pickingLotStatus = parkingBoy.pickingVehicle(ticket1);
        //then
        assertFalse(pickingLotStatus.isSuccess());
    }


}
