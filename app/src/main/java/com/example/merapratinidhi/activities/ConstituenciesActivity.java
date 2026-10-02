package com.example.merapratinidhi.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.adapters.ConstituencyAdapter;
import com.example.merapratinidhi.data.DataRepository;
import com.example.merapratinidhi.models.AssemblyConstituency;
import com.example.merapratinidhi.models.ParliamentaryConstituency;
import com.example.merapratinidhi.models.Representative;
import com.example.merapratinidhi.utils.ThemeHelper;

import java.util.List;

public class ConstituenciesActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnTabAssembly, btnTabParliament;
    private RecyclerView rvConstituencies;

    private ConstituencyAdapter adapter;
    private DataRepository repository;
    private boolean isAssemblyMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constituencies);

        repository = DataRepository.getInstance(this);

        btnBack = findViewById(R.id.btn_back);
        btnTabAssembly = findViewById(R.id.btn_tab_assembly);
        btnTabParliament = findViewById(R.id.btn_tab_parliament);
        rvConstituencies = findViewById(R.id.rv_constituencies);

        rvConstituencies.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(v -> finish());

        btnTabAssembly.setOnClickListener(v -> {
            if (!isAssemblyMode) {
                isAssemblyMode = true;
                updateTabStyles();
                if (adapter != null) adapter.setMode(true);
            }
        });

        btnTabParliament.setOnClickListener(v -> {
            if (isAssemblyMode) {
                isAssemblyMode = false;
                updateTabStyles();
                if (adapter != null) adapter.setMode(false);
            }
        });

        setupAdapter();
        updateTabStyles();
    }

    private void updateTabStyles() {
        int activeBg = ContextCompat.getColor(this, R.color.primary);
        int transparentBg = Color.TRANSPARENT;
        int activeText = Color.WHITE;
        int inactiveText = ContextCompat.getColor(this, R.color.text_secondary);

        btnTabAssembly.setBackgroundTintList(ColorStateList.valueOf(isAssemblyMode ? activeBg : transparentBg));
        btnTabAssembly.setTextColor(isAssemblyMode ? activeText : inactiveText);

        btnTabParliament.setBackgroundTintList(ColorStateList.valueOf(!isAssemblyMode ? activeBg : transparentBg));
        btnTabParliament.setTextColor(!isAssemblyMode ? activeText : inactiveText);
    }

    private void setupAdapter() {
        List<AssemblyConstituency> assemblyList = repository.getAllAssemblyConstituencies();
        List<ParliamentaryConstituency> parliamentaryList = repository.getAllParliamentaryConstituencies();

        adapter = new ConstituencyAdapter(assemblyList, parliamentaryList, new ConstituencyAdapter.OnConstituencyClickListener() {
            @Override
            public void onAssemblyClick(AssemblyConstituency ac) {
                openRepresentativeDetail(ac.getMlaName());
            }

            @Override
            public void onParliamentaryClick(ParliamentaryConstituency pc) {
                openRepresentativeDetail(pc.getMpName());
            }
        });

        rvConstituencies.setAdapter(adapter);
    }

    private void openRepresentativeDetail(String leaderName) {
        List<Representative> reps = repository.getAllRepresentatives();
        Representative targetRep = null;
        for (Representative r : reps) {
            if (r.getName().equalsIgnoreCase(leaderName)) {
                targetRep = r;
                break;
            }
        }

        if (targetRep != null) {
            Intent intent = new Intent(this, RepresentativeDetailActivity.class);
            intent.putExtra(RepresentativeDetailActivity.EXTRA_REP_ID, targetRep.getId());
            startActivity(intent);
        }
    }
}
