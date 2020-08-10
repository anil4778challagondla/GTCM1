package com.gmi.gtcm.Model;

public class Gamesmodel {
    private String GameId;
    private String GameTitle;
    private String GameImagepath;

    public Gamesmodel(String scratchImagePath) {
        ScratchImagePath = scratchImagePath;
    }

    public String getScratchImagePath() {
        return ScratchImagePath;
    }

    public void setScratchImagePath(String scratchImagePath) {
        ScratchImagePath = scratchImagePath;
    }

    private String ScratchImagePath;

    public Gamesmodel() {

    }

    public String getGameId() {
        return GameId;
    }

    public void setGameId(String gameId) {
        GameId = gameId;
    }

    public String getGameTitle() {
        return GameTitle;
    }

    public void setGameTitle(String gameTitle) {
        GameTitle = gameTitle;
    }

    public String getGameImagepath() {
        return GameImagepath;
    }

    public void setGameImagepath(String gameImagepath) {
        GameImagepath = gameImagepath;
    }

    public String getGameDescription() {
        return GameDescription;
    }

    public void setGameDescription(String gameDescription) {
        GameDescription = gameDescription;
    }

    public String getConditionsApply() {
        return ConditionsApply;
    }

    public void setConditionsApply(String conditionsApply) {
        ConditionsApply = conditionsApply;
    }

    public String getStartDatestring() {
        return StartDatestring;
    }

    public void setStartDatestring(String startDatestring) {
        StartDatestring = startDatestring;
    }

    public String getEndDatestring() {
        return EndDatestring;
    }

    public void setEndDatestring(String endDatestring) {
        EndDatestring = endDatestring;
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

    private String GameDescription;

    public Gamesmodel(String gameId, String gameTitle, String gameImagepath, String gameDescription, String conditionsApply, String startDatestring, String endDatestring, String clientId, String businessId) {
        GameId = gameId;
        GameTitle = gameTitle;
        GameImagepath = gameImagepath;
        GameDescription = gameDescription;
        ConditionsApply = conditionsApply;
        StartDatestring = startDatestring;
        EndDatestring = endDatestring;
        ClientId = clientId;
        BusinessId = businessId;
    }

    private String ConditionsApply;
    private String StartDatestring;
    private String EndDatestring;
    private String ClientId;
    private String BusinessId;
}
