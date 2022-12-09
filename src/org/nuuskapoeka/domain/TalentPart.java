package org.nuuskapoeka.domain;

public class TalentPart {

    private String name;
    private int spirits;
    private int price;
    private String color;
    private double baseMin;
    private double baseMax;
    private double damageScale;
    private int hitAmount;
    private double shieldIgnoreMin;
    private double shieldIgnoreMax;
    private double healMin;
    private double healMax;
    private String healType;

    public TalentPart(String name,
                      int spirits,
                      int price,
                      String color,
                      double baseMin,
                      double baseMax,
                      double damageScale,
                      int hitAmount,
                      double shieldIgnoreMin,
                      double shieldIgnoreMax,
                      double healMin,
                      double healMax,
                      String healType) {
        this.name = name;
        this.spirits = spirits;
        this.price = price;
        this.color = color;
        this.baseMin = baseMin;
        this.baseMax = baseMax;
        this.damageScale = damageScale;
        this.hitAmount = hitAmount;
        this.shieldIgnoreMin = shieldIgnoreMin;
        this.shieldIgnoreMax = shieldIgnoreMax;
        this.healMin = healMin;
        this.healMax = healMax;
        this.healType = healType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpirits() {
        return spirits;
    }

    public void setSpirits(int spirits) {
        this.spirits = spirits;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getBaseMin() {
        return baseMin;
    }

    public void setBaseMin(double baseMin) {
        this.baseMin = baseMin;
    }

    public double getBaseMax() {
        return baseMax;
    }

    public void setBaseMax(double baseMax) {
        this.baseMax = baseMax;
    }

    public double getDamageScale() {
        return damageScale;
    }

    public void setDamageScale(double damageScale) {
        this.damageScale = damageScale;
    }

    public int getHitAmount() {
        return hitAmount;
    }

    public void setHitAmount(int hitAmount) {
        this.hitAmount = hitAmount;
    }

    public double getShieldIgnoreMin() {
        return shieldIgnoreMin;
    }

    public void setShieldIgnoreMin(double shieldIgnoreMin) {
        this.shieldIgnoreMin = shieldIgnoreMin;
    }

    public double getShieldIgnoreMax() {
        return shieldIgnoreMax;
    }

    public void setShieldIgnoreMax(double shieldIgnoreMax) {
        this.shieldIgnoreMax = shieldIgnoreMax;
    }

    public double getHealMin() {
        return healMin;
    }

    public void setHealMin(double healMin) {
        this.healMin = healMin;
    }

    public double getHealMax() {
        return healMax;
    }

    public void setHealMax(double healMax) {
        this.healMax = healMax;
    }

    public String getHealType() {
        return healType;
    }

    public void setHealType(String healType) {
        this.healType = healType;
    }
}
