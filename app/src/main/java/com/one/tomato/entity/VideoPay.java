package com.one.tomato.entity;

import java.util.List;

/* loaded from: classes3.dex */
public class VideoPay {
    private double balance;
    private List<String> downVideoList;
    private int freeTimes;
    private boolean hasPay;
    private List<String> photoDownVideoList;
    private int photoVideoPayTomatoAmount;
    private double tmtBalance;
    private double videoPayAmount;
    private int videoPayTomatoAmount;
    private int vipFreeVideoDownloads;

    public double getVideoPayAmount() {
        return this.videoPayAmount;
    }

    public void setVideoPayAmount(double d) {
        this.videoPayAmount = d;
    }

    public int getVideoPayTomatoAmount() {
        return this.videoPayTomatoAmount;
    }

    public void setVideoPayTomatoAmount(int i) {
        this.videoPayTomatoAmount = i;
    }

    public int getPhotoVideoPayTomatoAmount() {
        return this.photoVideoPayTomatoAmount;
    }

    public void setPhotoVideoPayTomatoAmount(int i) {
        this.photoVideoPayTomatoAmount = i;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double d) {
        this.balance = d;
    }

    public double getTmtBalance() {
        return this.tmtBalance;
    }

    public void setTmtBalance(double d) {
        this.tmtBalance = d;
    }

    public boolean isHasPay() {
        return this.hasPay;
    }

    public void setHasPay(boolean z) {
        this.hasPay = z;
    }

    public int getFreeTimes() {
        return this.freeTimes;
    }

    public void setFreeTimes(int i) {
        this.freeTimes = i;
    }

    public int getVipFreeVideoDownloads() {
        return this.vipFreeVideoDownloads;
    }

    public void setVipFreeVideoDownloads(int i) {
        this.vipFreeVideoDownloads = i;
    }

    public List<String> getDownVideoList() {
        return this.downVideoList;
    }

    public void setDownVideoList(List<String> list) {
        this.downVideoList = list;
    }

    public List<String> getPhotoDownVideoList() {
        return this.photoDownVideoList;
    }

    public void setPhotoDownVideoList(List<String> list) {
        this.photoDownVideoList = list;
    }
}
