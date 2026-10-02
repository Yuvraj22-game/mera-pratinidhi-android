package com.example.merapratinidhi.models;

import java.io.Serializable;

public class ParliamentaryConstituency implements Serializable {
    private String id;
    private String name;
    private String mpName;
    private int assemblyCount;

    public ParliamentaryConstituency(String id, String name, String mpName, int assemblyCount) {
        this.id = id;
        this.name = name;
        this.mpName = mpName;
        this.assemblyCount = assemblyCount;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getMpName() { return mpName; }
    public int getAssemblyCount() { return assemblyCount; }
}
