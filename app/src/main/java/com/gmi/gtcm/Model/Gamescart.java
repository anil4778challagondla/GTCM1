package com.gmi.gtcm.Model;

public class Gamescart {
    private String CustomerGameFrequencyId;
    private String CustomerId;
    private String ClientId;

    public Gamescart() {

    }

    public String getCustomerGameFrequencyId() {
        return CustomerGameFrequencyId;
    }

    public void setCustomerGameFrequencyId(String customerGameFrequencyId) {
        CustomerGameFrequencyId = customerGameFrequencyId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getFrequencyId() {
        return FrequencyId;
    }

    public void setFrequencyId(String frequencyId) {
        FrequencyId = frequencyId;
    }

    public String getFinished() {
        return Finished;
    }

    public void setFinished(String finished) {
        Finished = finished;
    }

    public String getPrizeNumber() {
        return PrizeNumber;
    }

    public void setPrizeNumber(String prizeNumber) {
        PrizeNumber = prizeNumber;
    }

    public String getPrizeId() {
        return PrizeId;
    }

    public void setPrizeId(String prizeId) {
        PrizeId = prizeId;
    }

    public String getPrizeName() {
        return PrizeName;
    }

    public void setPrizeName(String prizeName) {
        PrizeName = prizeName;
    }

    public String getPrizeImagePath() {
        return PrizeImagePath;
    }

    public void setPrizeImagePath(String prizeImagePath) {
        PrizeImagePath = prizeImagePath;
    }

    public Gamescart(String customerGameFrequencyId, String customerId, String clientId, String frequencyId, String finished, String prizeNumber, String prizeId, String prizeName, String prizeImagePath) {
        CustomerGameFrequencyId = customerGameFrequencyId;
        CustomerId = customerId;
        ClientId = clientId;
        FrequencyId = frequencyId;
        Finished = finished;
        PrizeNumber = prizeNumber;
        PrizeId = prizeId;
        PrizeName = prizeName;
        PrizeImagePath = prizeImagePath;
    }

    private String FrequencyId;
    private String Finished;
    private String PrizeNumber;
    private String PrizeId;
    private String PrizeName;
    private String PrizeImagePath;
}
