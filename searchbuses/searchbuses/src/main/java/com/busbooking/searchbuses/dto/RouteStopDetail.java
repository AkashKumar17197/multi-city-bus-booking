package com.busbooking.searchbuses.dto;

public class RouteStopDetail {
    private Long cityId;
    private Long seqId;
    private String duration;

    public RouteStopDetail(Long cityId, Long seqId, String duration) {
        this.cityId = cityId;
        this.seqId = seqId;
        this.duration = duration;

    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}