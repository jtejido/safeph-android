package com.luckyseven.safeph.Adapters.ExposureEntityAdapter.Holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luckyseven.safeph.databinding.ItemExposureEntityBinding;


public class ExposureEntityHolder extends RecyclerView.ViewHolder {
   public ItemExposureEntityBinding binding;

    public ExposureEntityHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemExposureEntityBinding.bind(itemView);

    }
}
