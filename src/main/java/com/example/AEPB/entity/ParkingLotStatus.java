package com.example.AEPB.entity;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParkingLotStatus {
    private boolean success = false;

    private Ticket ticket;

    public static String getParkingLot() {
        return null;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
