package com.docduck.application.data;

public class Machine {

    private String name;
    private String room;
    private String status;
    
    public Machine(String name, String room, String status) {
        this.name = name;
        this.room = room;
        if (status.equals("ONLINE") || status.equals("MAINTENANCE") || status.equals("OFFLINE")) {
            this.status = status;
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }
    
    @Override
    public String toString() {
        return ("Machine name: " + this.name + ", Room: " + this.room + ", Status: " + this.status);

    }
    
}
