package com.example.merapratinidhi.models;

import java.io.Serializable;

public class AssemblyConstituency implements Serializable {
    private final String id;
    private final String name;
    private final String mlaName;
    private final String parliamentaryConstituency;
    private final int wardCount;

    public AssemblyConstituency(String id, String name, String mlaName,
                                String parliamentaryConstituency, int wardCount) {
        this.id = id;
        this.name = name;
        this.mlaName = mlaName;
        this.parliamentaryConstituency = parliamentaryConstituency;
        this.wardCount = wardCount;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getMlaName() { return mlaName; }
    public String getParliamentaryConstituency() { return parliamentaryConstituency; }
    public int getWardCount() { return wardCount; }
}
