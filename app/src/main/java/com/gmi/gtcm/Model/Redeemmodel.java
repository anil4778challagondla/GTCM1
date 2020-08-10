package com.gmi.gtcm.Model;

public class Redeemmodel {
    private String PromotionName;
    private String CreatedDatestring;
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String color;

    public Redeemmodel(String createdDatestring, String attributes) {
        CreatedDatestring = createdDatestring;
        Attributes = attributes;
    }

    public String getCreatedDatestring() {
        return CreatedDatestring;
    }

    public void setCreatedDatestring(String createdDatestring) {
        CreatedDatestring = createdDatestring;
    }

    public String getAttributes() {
        return Attributes;
    }

    public void setAttributes(String attributes) {
        Attributes = attributes;
    }

    private String Attributes;
    private String ItemCost;


    public Redeemmodel(String promotionName, String promotionImagePath, String redeemCode, String promotionId, String itemQty, String itemCost, String itemTotalCost) {
        PromotionName = promotionName;
        PromotionImagePath = promotionImagePath;
        RedeemCode = redeemCode;
        PromotionId = promotionId;
        ItemQty = itemQty;
        ItemCost = itemCost;
        ItemTotalCost = itemTotalCost;
    }

    private String PromotionImagePath;
    private String RedeemCode;

    public Redeemmodel() {

    }

    public String getPromotionName() {
        return PromotionName;
    }

    public void setPromotionName(String promotionName) {
        PromotionName = promotionName;
    }

    public String getPromotionImagePath() {
        return PromotionImagePath;
    }

    public void setPromotionImagePath(String promotionImagePath) {
        PromotionImagePath = promotionImagePath;
    }

    public String getRedeemCode() {
        return RedeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        RedeemCode = redeemCode;
    }

    public String getPromotionId() {
        return PromotionId;
    }

    public void setPromotionId(String promotionId) {
        PromotionId = promotionId;
    }

    public String getItemQty() {
        return ItemQty;
    }

    public void setItemQty(String itemQty) {
        ItemQty = itemQty;
    }

    public String getItemCost() {
        return ItemCost;
    }

    public void setItemCost(String itemCost) {
        ItemCost = itemCost;
    }

    public String getItemTotalCost() {
        return ItemTotalCost;
    }

    public void setItemTotalCost(String itemTotalCost) {
        ItemTotalCost = itemTotalCost;
    }

    private String PromotionId;
    private String ItemQty;

    private String ItemTotalCost;
}
