package com.gmi.gtcm.Model;

public class OfferModel {
    private String PromotionId;
    private String ClientId;
    private String BusinessId;
    private String BusinessName;
    private String Businessaddress;
    private String url;
    private String BusinessLogoPath;
    private String PromotionName;
    private String CategoryId;
    private String CategoryName;
    private String PromotionImagePath;
    private String Conditions;



    private String PartnerId;
    private String PartnerLogoPath;
    private String Description;



    private String BuyingStyle;


    public String getPromotionId() {
        return PromotionId;
    }

    public void setPromotionId(String promotionId) {
        PromotionId = promotionId;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getBusinessaddress() {
        return Businessaddress;
    }

    public void setBusinessaddress(String businessaddress) {
        Businessaddress = businessaddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBusinessLogoPath() {
        return BusinessLogoPath;
    }

    public void setBusinessLogoPath(String businessLogoPath) {
        BusinessLogoPath = businessLogoPath;
    }

    public String getPromotionName() {
        return PromotionName;
    }

    public void setPromotionName(String promotionName) {
        PromotionName = promotionName;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getPromotionImagePath() {
        return PromotionImagePath;
    }

    public void setPromotionImagePath(String promotionImagePath) {
        PromotionImagePath = promotionImagePath;
    }

    public String getConditions() {
        return Conditions;
    }

    public void setConditions(String conditions) {
        Conditions = conditions;
    }

    public String getPartnerId() {
        return PartnerId;
    }

    public void setPartnerId(String partnerId) {
        PartnerId = partnerId;
    }

    public String getPartnerLogoPath() {
        return PartnerLogoPath;
    }

    public void setPartnerLogoPath(String partnerLogoPath) {
        PartnerLogoPath = partnerLogoPath;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBuyingStyle() {
        return BuyingStyle;
    }

    public void setBuyingStyle(String buyingStyle) {
        BuyingStyle = buyingStyle;
    }

    public String getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        RetailPrice = retailPrice;
    }

    public String getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public String getAttrlist() {
        return Attrlist;
    }

    public void setAttrlist(String attrlist) {
        Attrlist = attrlist;
    }

    public String getAttributes() {
        return Attributes;
    }

    public void setAttributes(String attributes) {
        Attributes = attributes;
    }

    public String getAttributeVales() {
        return AttributeVales;
    }

    public void setAttributeVales(String attributeVales) {
        AttributeVales = attributeVales;
    }

    public OfferModel() {

    }


    private String RetailPrice;
    private String SellingPrice;
    private String Attrlist;
    private String Attributes;

    public OfferModel(String promotionId, String clientId, String businessId, String businessName, String businessaddress, String url, String businessLogoPath, String promotionName, String categoryId, String categoryName, String promotionImagePath, String conditions, String partnerId, String partnerLogoPath, String description, String buyingStyle, String retailPrice, String sellingPrice, String attrlist, String attributes, String attributeVales) {
        PromotionId = promotionId;
        ClientId = clientId;
        BusinessId = businessId;
        BusinessName = businessName;
        Businessaddress = businessaddress;
        this.url = url;
        BusinessLogoPath = businessLogoPath;
        PromotionName = promotionName;
        CategoryId = categoryId;
        CategoryName = categoryName;
        PromotionImagePath = promotionImagePath;
        Conditions = conditions;
        PartnerId = partnerId;
        PartnerLogoPath = partnerLogoPath;
        Description = description;
        BuyingStyle = buyingStyle;
        RetailPrice = retailPrice;
        SellingPrice = sellingPrice;
        Attrlist = attrlist;
        Attributes = attributes;
        AttributeVales = attributeVales;
    }

    private String AttributeVales;
}
