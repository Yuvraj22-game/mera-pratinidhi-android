package com.example.merapratinidhi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.adapters.WardAdapter;
import com.example.merapratinidhi.data.DataRepository;
import com.example.merapratinidhi.models.Representative;
import com.example.merapratinidhi.models.Ward;
import com.example.merapratinidhi.utils.ThemeHelper;

import java.util.List;

public class WardsActivity extends AppCompatActivity {

    private EditText etSearch;
    private ImageView btnClearSearch;
    private RecyclerView rvWards;

    private WardAdapter adapter;
    private DataRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wards);

        repository = DataRepository.getInstance(this);

        ImageButton btnBack = findViewById(R.id.btn_back);
        etSearch = findViewById(R.id.et_search_ward);
        btnClearSearch = findViewById(R.id.btn_clear_search_ward);
        rvWards = findViewById(R.id.rv_wards);

        rvWards.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(v -> finish());

        setupSearch();
        loadWards("");
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                btnClearSearch.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
                loadWards(query);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnClearSearch.setOnClickListener(v -> etSearch.setText(""));
    }

    private void loadWards(String query) {
        List<Ward> wardList = repository.filterWards(query);

        if (adapter == null) {
            adapter = new WardAdapter(wardList, ward -> {
                List<Representative> reps = repository.getAllRepresentatives();
                Representative targetRep = null;
                for (Representative r : reps) {
                    if (r.getName().equalsIgnoreCase(ward.getCouncillorName())) {
                        targetRep = r;
                        break;
                    }
                }

                if (targetRep != null) {
                    Intent intent = new Intent(WardsActivity.this, RepresentativeDetailActivity.class);
                    intent.putExtra(RepresentativeDetailActivity.EXTRA_REP_ID, targetRep.getId());
                    startActivity(intent);
                }
            });
            rvWards.setAdapter(adapter);
        } else {
            adapter.updateList(wardList);
        }
    }
}
