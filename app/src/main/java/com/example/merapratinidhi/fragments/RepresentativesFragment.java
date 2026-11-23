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
import com.example.merapratinidhi.viewmodels.RepresentativesViewModel;

import java.util.List;

public class RepresentativesFragment extends Fragment {

    private static final String KEY_SEARCH_QUERY = "key_search_query";
    private static final String KEY_FILTER_TYPE = "key_filter_type";

    private RecyclerView rvRepresentatives;
    private View layoutEmptyState;
    private EditText etSearch;
    private ImageView btnClearSearch;

    private Button chipAll, chipCouncillors, chipMlas, chipMps;

    private RepresentativeAdapter adapter;
    private RepresentativesViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_representatives, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getContext() == null) return;

        viewModel = new RepresentativesViewModel();
        viewModel.init(DataRepository.getInstance(getContext()));

        if (savedInstanceState != null) {
            viewModel.setSearchQuery(savedInstanceState.getString(KEY_SEARCH_QUERY, ""));
            viewModel.setFilterType(savedInstanceState.getString(KEY_FILTER_TYPE, "All"));
        }

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

        if (!viewModel.getSearchQuery().isEmpty()) {
            etSearch.setText(viewModel.getSearchQuery());
            btnClearSearch.setVisibility(View.VISIBLE);
        }

        filterAndRefreshData();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) {
            outState.putString(KEY_SEARCH_QUERY, viewModel.getSearchQuery());
            outState.putString(KEY_FILTER_TYPE, viewModel.getFilterType());
        }
    }

    private void setupFilterChips() {
        View.OnClickListener filterClickListener = v -> {
            int id = v.getId();
            if (id == R.id.chip_all) {
                viewModel.setFilterType("All");
            } else if (id == R.id.chip_councillors) {
                viewModel.setFilterType("Councillors");
            } else if (id == R.id.chip_mlas) {
                viewModel.setFilterType("MLAs");
            } else if (id == R.id.chip_mps) {
                viewModel.setFilterType("MPs");
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

        String currentFilter = viewModel.getFilterType();
        setChipState(chipAll, currentFilter.equalsIgnoreCase("All"), activeBg, inactiveBg, activeText, inactiveText);
        setChipState(chipCouncillors, currentFilter.equalsIgnoreCase("Councillors"), activeBg, inactiveBg, activeText, inactiveText);
        setChipState(chipMlas, currentFilter.equalsIgnoreCase("MLAs"), activeBg, inactiveBg, activeText, inactiveText);
        setChipState(chipMps, currentFilter.equalsIgnoreCase("MPs"), activeBg, inactiveBg, activeText, inactiveText);
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
                String query = s.toString();
                viewModel.setSearchQuery(query);
                btnClearSearch.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
                filterAndRefreshData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnClearSearch.setOnClickListener(v -> etSearch.setText(""));
    }

    private void filterAndRefreshData() {
        if (viewModel == null) return;
        List<Representative> filteredList = viewModel.getFilteredRepresentatives();

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
                    if (getContext() != null) {
                        DataRepository.getInstance(getContext()).toggleSaveRepresentative(representative.getId());
                        filterAndRefreshData();
                    }
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
