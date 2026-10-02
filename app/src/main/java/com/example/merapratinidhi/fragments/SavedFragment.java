package com.example.merapratinidhi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.activities.MainActivity;
import com.example.merapratinidhi.activities.RepresentativeDetailActivity;
import com.example.merapratinidhi.adapters.RepresentativeAdapter;
import com.example.merapratinidhi.data.DataRepository;
import com.example.merapratinidhi.models.Representative;

import java.util.List;

public class SavedFragment extends Fragment {

    private RecyclerView rvSavedRepresentatives;
    private View layoutEmptySaved;
    private Button btnBrowseRepresentatives;

    private RepresentativeAdapter adapter;
    private DataRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getContext() == null) return;
        repository = DataRepository.getInstance(getContext());

        rvSavedRepresentatives = view.findViewById(R.id.rv_saved_representatives);
        layoutEmptySaved = view.findViewById(R.id.layout_empty_saved);
        btnBrowseRepresentatives = view.findViewById(R.id.btn_browse_representatives);

        rvSavedRepresentatives.setLayoutManager(new LinearLayoutManager(getContext()));

        btnBrowseRepresentatives.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchToTab(R.id.nav_representatives);
            }
        });

        loadSavedData();
    }

    private void loadSavedData() {
        List<Representative> savedList = repository.getSavedRepresentatives();

        if (savedList.isEmpty()) {
            rvSavedRepresentatives.setVisibility(View.GONE);
            layoutEmptySaved.setVisibility(View.VISIBLE);
        } else {
            rvSavedRepresentatives.setVisibility(View.VISIBLE);
            layoutEmptySaved.setVisibility(View.GONE);
        }

        if (adapter == null) {
            adapter = new RepresentativeAdapter(savedList, new RepresentativeAdapter.OnRepresentativeClickListener() {
                @Override
                public void onItemClick(Representative representative) {
                    Intent intent = new Intent(getContext(), RepresentativeDetailActivity.class);
                    intent.putExtra(RepresentativeDetailActivity.EXTRA_REP_ID, representative.getId());
                    startActivity(intent);
                }

                @Override
                public void onBookmarkClick(Representative representative, int position) {
                    repository.toggleSaveRepresentative(representative.getId());
                    loadSavedData();
                }
            });
            rvSavedRepresentatives.setAdapter(adapter);
        } else {
            adapter.updateList(savedList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSavedData();
    }
}
