package com.luckyseven.safeph.Adapters.PositiveTestAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.luckyseven.safeph.Adapters.PositiveTestAdapter.Holder.PositiveTestHolder;
import com.luckyseven.safeph.Models.PositiveTestManager;
import com.luckyseven.safeph.R;

public class PositiveTestAdapter extends RecyclerView.Adapter<PositiveTestHolder> {
    private Context context;
    private ArrayList<PositiveTestManager.PositiveTest> tests = new ArrayList<>();

    public PositiveTestAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PositiveTestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_positive_result, parent, false);
        return new PositiveTestHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PositiveTestHolder holder, int position) {
        holder.binding.tvDate.setText(context.getString(R.string.date_test, tests.get(position).getDateFormated()));
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public void refresh() {
        tests = PositiveTestManager.get().getTests();
        notifyDataSetChanged();
    }
}
