package com.example.merapratinidhi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.models.Ward;

import java.util.ArrayList;
import java.util.List;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.ViewHolder> {

    public interface OnWardClickListener {
        void onWardClick(Ward ward);
    }

    private List<Ward> wardList;
    private final OnWardClickListener listener;

    public WardAdapter(List<Ward> wardList, OnWardClickListener listener) {
        this.wardList = wardList != null ? wardList : new ArrayList<>();
        this.listener = listener;
    }

    public void updateList(List<Ward> newList) {
        this.wardList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ward, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ward ward = wardList.get(position);

        String title = "Ward " + ward.getWardNumber() + " — " + ward.getWardName();
        holder.tvTitle.setText(title);
        holder.tvCouncillor.setText("Councillor: " + ward.getCouncillorName());
        holder.tvDetails.setText("Assembly: " + ward.getAssemblyConstituency() + " • MP: " + ward.getParliamentaryConstituency());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onWardClick(ward);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wardList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCouncillor, tvDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_ward_title);
            tvCouncillor = itemView.findViewById(R.id.tv_ward_councillor);
            tvDetails = itemView.findViewById(R.id.tv_ward_details);
        }
    }
}
