package com.example.merapratinidhi.viewmodels;

import com.example.merapratinidhi.data.DataRepository;
import com.example.merapratinidhi.models.Representative;

import java.util.List;

public class RepresentativesViewModel {

    private DataRepository repository;
    private String searchQuery = "";
    private String filterType = "All";

    public void init(DataRepository repo) {
        this.repository = repo;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query != null ? query : "";
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String type) {
        this.filterType = type != null ? type : "All";
    }

    public List<Representative> getFilteredRepresentatives() {
        if (repository == null) {
            return java.util.Collections.emptyList();
        }
        return repository.filterRepresentatives(searchQuery, filterType);
    }
}
