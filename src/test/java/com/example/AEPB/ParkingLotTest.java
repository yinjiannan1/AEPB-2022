package com.example.AEPB;

import com.example.AEPB.entity.ParkingLotStatus;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.entity.Vehicle;
import com.example.AEPB.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ParkingLotTest {
    @Autowired
    ParkingLotService parkingLotService;

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
}
