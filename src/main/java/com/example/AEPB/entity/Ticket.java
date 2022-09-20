package com.example.AEPB.entity;


public class Ticket {
    private Vehicle vehicle;

    private boolean enabled;

    public Ticket(Vehicle vehicle, boolean enabled) {
        this.vehicle = vehicle;
        this.enabled = enabled;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
