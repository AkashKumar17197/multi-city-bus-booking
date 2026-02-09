package com.busbooking.searchbuses.dto;

public class BoardingDroppingPointDTO {

    private int id;
    private String point;


    public long getSeqId() {
        return seqId;
    }

    private long seqId;
    private String time;

    public BoardingDroppingPointDTO(int id, String point, long seqId, String time) {
        this.id = id;
        this.point = point;
        this.seqId = seqId;
        this.time = time;
    }

    public int getId() { return id; }
    public String getPoint() { return point; }

    public String getTime() { return time; }
}