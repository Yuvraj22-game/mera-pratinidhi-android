package com.example.merapratinidhi.models;

import java.io.Serializable;

public class Ward implements Serializable {
    private final String id;
    private final int wardNumber;
    private final String wardName;
    private final String councillorName;
    private final String assemblyConstituency;
    private final String parliamentaryConstituency;

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
