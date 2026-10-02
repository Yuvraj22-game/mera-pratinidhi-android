package com.example.merapratinidhi.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.activities.RepresentativeDetailActivity;
import com.example.merapratinidhi.adapters.RepresentativeAdapter;
import com.example.merapratinidhi.data.DataRepository;
import com.example.merapratinidhi.models.Representative;

import java.util.List;

public class RepresentativesFragment extends Fragment {

    private RecyclerView rvRepresentatives;
    private View layoutEmptyState;
    private EditText etSearch;
    private ImageView btnClearSearch;

    private Button chipAll, chipCouncillors, chipMlas, chipMps;

    private RepresentativeAdapter adapter;
    private DataRepository repository;

    private String currentSearchQuery = "";
    private String currentFilterType = "All";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_representatives, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getContext() == null) return;
        repository = DataRepository.getInstance(getContext());

        rvRepresentatives = view.findViewById(R.id.rv_representatives);
        layoutEmptyState = view.findViewById(R.id.layout_empty_state);
        etSearch = view.findViewById(R.id.et_search);
        btnClearSearch = view.findViewById(R.id.btn_clear_search);

        chipAll = view.findViewById(R.id.chip_all);
        chipCouncillors = view.findViewById(R.id.chip_councillors);
        chipMlas = view.findViewById(R.id.chip_mlas);
        chipMps = view.findViewById(R.id.chip_mps);

        rvRepresentatives.setLayoutManager(new LinearLayoutManager(getContext()));

        setupFilterChips();
        setupSearch();
        filterAndRefreshData();
    }

    private void setupFilterChips() {
        View.OnClickListener filterClickListener = v -> {
            int id = v.getId();
            if (id == R.id.chip_all) {
                currentFilterType = "All";
            } else if (id == R.id.chip_councillors) {
                currentFilterType = "Councillors";
            } else if (id == R.id.chip_mlas) {
                currentFilterType = "MLAs";
            } else if (id == R.id.chip_mps) {
                currentFilterType = "MPs";
            }
            updateChipStyles();
            filterAndRefreshData();
        };

        chipAll.setOnClickListener(filterClickListener);
        chipCouncillors.setOnClickListener(filterClickListener);
        chipMlas.setOnClickListener(filterClickListener);
        chipMps.setOnClickListener(filterClickListener);

        updateChipStyles();
    }

    private void updateChipStyles() {
        if (getContext() == null) return;
        int activeBg = ContextCompat.getColor(getContext(), R.color.primary);
        int inactiveBg = ContextCompat.getColor(getContext(), R.color.surface_variant);
        int activeText = Color.WHITE;
        int inactiveText = ContextCompat.getColor(getContext(), R.color.text_secondary);

        setChipState(chipAll, currentFilterType.equalsIgnoreCase("All"), activeBg, inactiveBg, activeText, inactiveText);
        setChipState(chipCouncillors, currentFilterType.equalsIgnoreCase("Councillors"), activeBg, inactiveBg, activeText, inactiveText);
        setChipState(chipMlas, currentFilterType.equalsIgnoreCase("MLAs"), activeBg, inactiveBg, activeText, inactiveText);
        setChipState(chipMps, currentFilterType.equalsIgnoreCase("MPs"), activeBg, inactiveBg, activeText, inactiveText);
    }

    private void setChipState(Button button, boolean isSelected, int activeBg, int inactiveBg, int activeText, int inactiveText) {
        button.setBackgroundTintList(ColorStateList.valueOf(isSelected ? activeBg : inactiveBg));
        button.setTextColor(isSelected ? activeText : inactiveText);
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s.toString();
                btnClearSearch.setVisibility(currentSearchQuery.isEmpty() ? View.GONE : View.VISIBLE);
                filterAndRefreshData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnClearSearch.setOnClickListener(v -> etSearch.setText(""));
    }

    private void filterAndRefreshData() {
        List<Representative> filteredList = repository.filterRepresentatives(currentSearchQuery, currentFilterType);

        if (filteredList.isEmpty()) {
            rvRepresentatives.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            rvRepresentatives.setVisibility(View.VISIBLE);
            layoutEmptyState.setVisibility(View.GONE);
        }

        if (adapter == null) {
            adapter = new RepresentativeAdapter(filteredList, new RepresentativeAdapter.OnRepresentativeClickListener() {
                @Override
                public void onItemClick(Representative representative) {
                    Intent intent = new Intent(getContext(), RepresentativeDetailActivity.class);
                    intent.putExtra(RepresentativeDetailActivity.EXTRA_REP_ID, representative.getId());
                    startActivity(intent);
                }

                @Override
                public void onBookmarkClick(Representative representative, int position) {
                    repository.toggleSaveRepresentative(representative.getId());
                    filterAndRefreshData();
                }
            });
            rvRepresentatives.setAdapter(adapter);
        } else {
            adapter.updateList(filteredList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        filterAndRefreshData();
    }
}
