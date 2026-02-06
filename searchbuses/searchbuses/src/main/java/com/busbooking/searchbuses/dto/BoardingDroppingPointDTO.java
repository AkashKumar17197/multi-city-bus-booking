package com.busbooking.searchbuses.dto;

public class BoardingDroppingPointDTO {

    private int id;
    private String point;
    private String time;

    public BoardingDroppingPointDTO(int id, String point, String time) {
        this.id = id;
        this.point = point;
        this.time = time;
    }

    public int getId() { return id; }
    public String getPoint() { return point; }
    public String getTime() { return time; }
}