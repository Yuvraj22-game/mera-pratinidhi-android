package com.example.merapratinidhi.models;

import java.io.Serializable;

public class Ward implements Serializable {
    private String id;
    private int wardNumber;
    private String wardName;
    private String councillorName;
    private String assemblyConstituency;
    private String parliamentaryConstituency;

    public Ward(String id, int wardNumber, String wardName, String councillorName,
                String assemblyConstituency, String parliamentaryConstituency) {
        this.id = id;
        this.wardNumber = wardNumber;
        this.wardName = wardName;
        this.councillorName = councillorName;
        this.assemblyConstituency = assemblyConstituency;
        this.parliamentaryConstituency = parliamentaryConstituency;
    }

    public String getId() { return id; }
    public int getWardNumber() { return wardNumber; }
    public String getWardName() { return wardName; }
    public String getCouncillorName() { return councillorName; }
    public String getAssemblyConstituency() { return assemblyConstituency; }
    public String getParliamentaryConstituency() { return parliamentaryConstituency; }
}
