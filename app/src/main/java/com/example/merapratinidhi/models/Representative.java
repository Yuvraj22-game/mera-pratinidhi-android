package com.example.merapratinidhi.models;

import java.io.Serializable;

public class Representative implements Serializable {
    private String id;
    private String name;
    private String party;
    private String type; // "Councillor", "MLA", "MP"
    private String wardOrConstituency;
    private String phone;
    private String email;
    private String officeAddress;
    private String source;
    private String verifiedDate;
    private String initials;
    private String avatarBgColor;
    private boolean isSaved;

    public Representative(String id, String name, String party, String type, String wardOrConstituency,
                          String phone, String email, String officeAddress, String source,
                          String verifiedDate, String initials, String avatarBgColor) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.type = type;
        this.wardOrConstituency = wardOrConstituency;
        this.phone = phone;
        this.email = email;
        this.officeAddress = officeAddress;
        this.source = source;
        this.verifiedDate = verifiedDate;
        this.initials = initials;
        this.avatarBgColor = avatarBgColor;
        this.isSaved = false;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getParty() { return party; }
    public String getType() { return type; }
    public String getWardOrConstituency() { return wardOrConstituency; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getOfficeAddress() { return officeAddress; }
    public String getSource() { return source; }
    public String getVerifiedDate() { return verifiedDate; }
    public String getInitials() { return initials; }
    public String getAvatarBgColor() { return avatarBgColor; }
    public boolean isSaved() { return isSaved; }

    public void setSaved(boolean saved) { isSaved = saved; }
}
