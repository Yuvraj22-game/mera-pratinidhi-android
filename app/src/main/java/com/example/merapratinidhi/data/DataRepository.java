package com.example.merapratinidhi.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.merapratinidhi.models.AssemblyConstituency;
import com.example.merapratinidhi.models.ParliamentaryConstituency;
import com.example.merapratinidhi.models.Representative;
import com.example.merapratinidhi.models.Ward;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataRepository {

    private static final String PREF_NAME = "mera_pratinidhi_prefs";
    private static final String KEY_SAVED_REPS = "saved_rep_ids";

    private static DataRepository instance;
    private final List<Representative> allRepresentatives;
    private final List<Ward> allWards;
    private final List<AssemblyConstituency> allAssemblyConstituencies;
    private final List<ParliamentaryConstituency> allParliamentaryConstituencies;
    private final SharedPreferences preferences;

    private DataRepository(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        allRepresentatives = MockData.getRepresentatives();
        allWards = MockData.getWards();
        allAssemblyConstituencies = MockData.getAssemblyConstituencies();
        allParliamentaryConstituencies = MockData.getParliamentaryConstituencies();

        syncSavedState();
    }

    public static synchronized DataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DataRepository(context);
        }
        return instance;
    }

    private void syncSavedState() {
        Set<String> savedIds = preferences.getStringSet(KEY_SAVED_REPS, new HashSet<>());
        for (Representative rep : allRepresentatives) {
            rep.setSaved(savedIds.contains(rep.getId()));
        }
    }

    public List<Representative> getAllRepresentatives() {
        syncSavedState();
        return new ArrayList<>(allRepresentatives);
    }

    public List<Representative> filterRepresentatives(String query, String filterType) {
        syncSavedState();
        List<Representative> result = new ArrayList<>();
        String lowerQuery = (query == null) ? "" : query.trim().toLowerCase();

        for (Representative rep : allRepresentatives) {
            boolean matchesType = true;
            if (filterType != null && !filterType.equalsIgnoreCase("All")) {
                if (filterType.equalsIgnoreCase("Councillors")) {
                    matchesType = rep.getType().equalsIgnoreCase("Councillor");
                } else if (filterType.equalsIgnoreCase("MLAs")) {
                    matchesType = rep.getType().equalsIgnoreCase("MLA");
                } else if (filterType.equalsIgnoreCase("MPs")) {
                    matchesType = rep.getType().equalsIgnoreCase("MP");
                } else {
                    matchesType = rep.getType().equalsIgnoreCase(filterType);
                }
            }

            boolean matchesQuery = lowerQuery.isEmpty()
                    || rep.getName().toLowerCase().contains(lowerQuery)
                    || rep.getParty().toLowerCase().contains(lowerQuery)
                    || rep.getWardOrConstituency().toLowerCase().contains(lowerQuery)
                    || rep.getType().toLowerCase().contains(lowerQuery);

            if (matchesType && matchesQuery) {
                result.add(rep);
            }
        }
        return result;
    }

    public List<Representative> getSavedRepresentatives() {
        syncSavedState();
        List<Representative> saved = new ArrayList<>();
        for (Representative rep : allRepresentatives) {
            if (rep.isSaved()) {
                saved.add(rep);
            }
        }
        return saved;
    }

    public boolean toggleSaveRepresentative(String repId) {
        Set<String> savedIds = new HashSet<>(preferences.getStringSet(KEY_SAVED_REPS, new HashSet<>()));
        boolean isNowSaved = false;

        if (savedIds.contains(repId)) {
            savedIds.remove(repId);
        } else {
            savedIds.add(repId);
            isNowSaved = true;
        }

        preferences.edit().putStringSet(KEY_SAVED_REPS, savedIds).apply();

        for (Representative rep : allRepresentatives) {
            if (rep.getId().equals(repId)) {
                rep.setSaved(isNowSaved);
                break;
            }
        }
        return isNowSaved;
    }

    public Representative getRepresentativeById(String repId) {
        syncSavedState();
        for (Representative rep : allRepresentatives) {
            if (rep.getId().equals(repId)) {
                return rep;
            }
        }
        return null;
    }

    public List<Ward> getAllWards() {
        return new ArrayList<>(allWards);
    }

    public List<Ward> filterWards(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllWards();
        }
        String lower = query.trim().toLowerCase();
        List<Ward> result = new ArrayList<>();
        for (Ward w : allWards) {
            if (w.getWardName().toLowerCase().contains(lower)
                    || String.valueOf(w.getWardNumber()).contains(lower)
                    || w.getCouncillorName().toLowerCase().contains(lower)
                    || w.getAssemblyConstituency().toLowerCase().contains(lower)) {
                result.add(w);
            }
        }
        return result;
    }

    public List<AssemblyConstituency> getAllAssemblyConstituencies() {
        return new ArrayList<>(allAssemblyConstituencies);
    }

    public List<ParliamentaryConstituency> getAllParliamentaryConstituencies() {
        return new ArrayList<>(allParliamentaryConstituencies);
    }
}
