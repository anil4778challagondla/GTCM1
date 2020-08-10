package com.gmi.gtcm.Model;

public class Groupsnoticesmodel {
    private String OrgId;
    private String GroupId;

    public Groupsnoticesmodel(String orgId, String groupId, String groupName, String logoPath) {
        OrgId = orgId;
        GroupId = groupId;
        GroupName = groupName;
        LogoPath = logoPath;
    }

    private String GroupName;

    public Groupsnoticesmodel() {

    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getLogoPath() {
        return LogoPath;
    }

    public void setLogoPath(String logoPath) {
        LogoPath = logoPath;
    }

    private String LogoPath;
}
