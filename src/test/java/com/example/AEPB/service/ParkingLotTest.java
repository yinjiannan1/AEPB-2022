package com.example.AEPB.service;

import com.example.AEPB.entity.ParkingLot;
import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ParkingLotTest.class)
public class ParkingLotTest {

    private static final int PARKING_LOT_MAX_SIZE = 200;
    ParkingLotService parkingLotService = new ParkingLotService();


    @Test
    void should_be_parking_success_when_try_to_park_given_an_vehicle_with_car_plate_number_not_empty() {
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = parkingLotService.parkingVehicle(vehicle);
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
        ParkingLotStatus parkingLotStatus = parkingLotService.parkingVehicle(vehicle);
        //then
        assertFalse(parkingLotStatus.isSuccess());
    }

    @Test
    void should_return_picking_success_when_try_to_picking_given_an_useful_ticket_and_vehicle_has_parked(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        ParkingLotStatus parkingLotStatus = parkingLotService.parkingVehicle(vehicle);
        //when
        ParkingLotStatus pickingStatus = parkingLotService.pickingVehicle(parkingLotStatus.getTicket());
        //then
        assertTrue(pickingStatus.isSuccess());
    }

    @Test
    void should_return_ticket_when_parked_vehicle_given_an_vehicle_parked(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = parkingLotService.parkingVehicle(vehicle);
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
            parkingLotService.parkingVehicle(vehicle1);
        }
        //when
        ParkingLotStatus parkingLotStatus = parkingLotService.parkingVehicle(vehicle);
        //then
        assertFalse(parkingLotStatus.isSuccess());
    }

    @Test
    void should_return_false_when_try_to_park_when_a_vehicle_with_same_plate_number_in_parking_lot(){
        //given
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPlateNumber("京A12345");
        parkingLotService.parkingVehicle(vehicle);
        Vehicle vehicleAnother = new Vehicle();
        vehicleAnother.setCarPlateNumber("京A12345");
        //when
        ParkingLotStatus parkingLotStatus = parkingLotService.parkingVehicle(vehicleAnother);
        //then
        assertFalse(parkingLotStatus.isSuccess());

    }


}
