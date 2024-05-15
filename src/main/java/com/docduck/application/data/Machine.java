package com.docduck.application.data;

public class Machine {

    private String name;
    private String room;
    private String status;
    private String serialNumber;
    private String hyperlink;
    
    public Machine(String name, String room, String status, String serialNumber, String hyperlink) {
        this.name = name;
        this.room = room;
        this.serialNumber = serialNumber;
        this.hyperlink = hyperlink;
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

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getDatasheet() {
        return this.hyperlink;
    }
    
}
