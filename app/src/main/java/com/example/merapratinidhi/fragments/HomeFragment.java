package com.example.merapratinidhi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.activities.ConstituenciesActivity;
import com.example.merapratinidhi.activities.LocationActivity;
import com.example.merapratinidhi.activities.MainActivity;
import com.example.merapratinidhi.activities.RepresentativeDetailActivity;
import com.example.merapratinidhi.activities.WardsActivity;
import com.example.merapratinidhi.adapters.RepresentativeAdapter;
import com.example.merapratinidhi.data.DataRepository;
import com.example.merapratinidhi.models.Representative;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvYourRepresentatives;
    private RepresentativeAdapter adapter;
    private DataRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getContext() == null) return;
        repository = DataRepository.getInstance(getContext());

        rvYourRepresentatives = view.findViewById(R.id.rv_your_representatives);
        rvYourRepresentatives.setLayoutManager(new LinearLayoutManager(getContext()));

        setupFeaturedRepresentatives();

        // Location buttons
        View btnLocationIndicator = view.findViewById(R.id.btn_location_indicator);
        View btnViewMyReps = view.findViewById(R.id.btn_view_my_representatives);

        View.OnClickListener openLocation = v -> {
            Intent intent = new Intent(getContext(), LocationActivity.class);
            startActivity(intent);
        };

        btnLocationIndicator.setOnClickListener(openLocation);
        btnViewMyReps.setOnClickListener(openLocation);

        // Quick action cards
        View cardFindReps = view.findViewById(R.id.card_action_find_reps);
        View cardSavedReps = view.findViewById(R.id.card_action_saved_reps);
        View cardExploreWards = view.findViewById(R.id.card_action_explore_wards);
        View cardExploreConstituencies = view.findViewById(R.id.card_action_explore_constituencies);

        cardFindReps.setOnClickListener(openLocation);

        cardSavedReps.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchToTab(R.id.nav_saved);
            }
        });

        cardExploreWards.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WardsActivity.class);
            startActivity(intent);
        });

        cardExploreConstituencies.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ConstituenciesActivity.class);
            startActivity(intent);
        });
    }

    private void setupFeaturedRepresentatives() {
        List<Representative> allReps = repository.getAllRepresentatives();
        List<Representative> featured = new ArrayList<>();

        // Pick 1 Councillor, 1 MLA, 1 MP for Home showcase
        Representative councillor = null, mla = null, mp = null;
        for (Representative r : allReps) {
            if (councillor == null && r.getType().equalsIgnoreCase("Councillor")) councillor = r;
            if (mla == null && r.getType().equalsIgnoreCase("MLA")) mla = r;
            if (mp == null && r.getType().equalsIgnoreCase("MP")) mp = r;
        }

        if (councillor != null) featured.add(councillor);
        if (mla != null) featured.add(mla);
        if (mp != null) featured.add(mp);

        adapter = new RepresentativeAdapter(featured, new RepresentativeAdapter.OnRepresentativeClickListener() {
            @Override
            public void onItemClick(Representative representative) {
                Intent intent = new Intent(getContext(), RepresentativeDetailActivity.class);
                intent.putExtra(RepresentativeDetailActivity.EXTRA_REP_ID, representative.getId());
                startActivity(intent);
            }

            @Override
            public void onBookmarkClick(Representative representative, int position) {
                repository.toggleSaveRepresentative(representative.getId());
                setupFeaturedRepresentatives();
            }
        });

        rvYourRepresentatives.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupFeaturedRepresentatives();
    }
}
