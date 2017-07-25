package com.diyiliu.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description: Stock
 * Author: DIYILIU
 * Update: 2017-07-24 10:48
 */
public class Stock {

    private int id;
    private String inNo;
    private int memberId;
    private String memberName;
    private Integer gross;
    private Integer tare;
    private Integer suttle;
    private BigDecimal price;
    private BigDecimal money;

    private Date createTime;
    private Date inTime;
    private Date payTime;

    private Integer state;

    private Member member;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInNo() {
        return inNo;
    }

    public void setInNo(String inNo) {
        this.inNo = inNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Integer getGross() {
        return gross;
    }

    public void setGross(Integer gross) {
        this.gross = gross;
    }

    public Integer getTare() {
        return tare;
    }

    public void setTare(Integer tare) {
        this.tare = tare;
    }

    public Integer getSuttle() {
        return suttle;
    }

    public void setSuttle(Integer suttle) {
        this.suttle = suttle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
