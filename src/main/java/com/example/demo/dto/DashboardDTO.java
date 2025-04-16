package com.example.demo.dto;

public class DashboardDTO {
    private long prodCount;
    private long userCount;
    private double totalRevenue;
    private long totalSold;


    public DashboardDTO(long prodCount, long userCount, double totalRevenue, long totalSold) {
        this.prodCount = prodCount;
        this.userCount = userCount;
        this.totalRevenue = totalRevenue;
        this.totalSold = totalSold;
    }

    public long getProdCount() {
        return prodCount;
    }

    public void setProdCount(long prodCount) {
        this.prodCount = prodCount;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(long totalSold) {
        this.totalSold = totalSold;
    }
}
