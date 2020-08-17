package com.luckyseven.safeph.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luckyseven.safeph.Activities.ShareDiagnosisActivity;
import com.luckyseven.safeph.Adapters.PositiveTestAdapter.PositiveTestAdapter;
import com.luckyseven.safeph.Listeners.IFilterListener;
import com.luckyseven.safeph.Models.PositiveTestManager;
import com.luckyseven.safeph.databinding.FragmentNotifyHomeBinding;


public class NotifyOthersFragment extends Fragment implements IFilterListener {
    private FragmentNotifyHomeBinding binding;
    private PositiveTestAdapter positiveTestAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotifyHomeBinding.inflate(inflater, container, false);
        setup();
        return binding.getRoot();
    }

    private void setup() {
        binding.fragmentNotifyShareButton.setOnClickListener(view -> startShareActivity());
        binding.notifyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        positiveTestAdapter = new PositiveTestAdapter(getContext());
        binding.notifyRecyclerView.setAdapter(positiveTestAdapter);
        if(!PositiveTestManager.get().getTests().isEmpty()) {
            binding.llSharedList.setVisibility(View.VISIBLE);
        }
    }

    private void startShareActivity() {
        Intent intent = new Intent(getActivity(), ShareDiagnosisActivity.class);
        startActivity(intent);
    }
    @Override
    public void getNewValue(Object object) {
        binding.testItem.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(positiveTestAdapter != null && !PositiveTestManager.get().getTests().isEmpty()) {
            positiveTestAdapter.refresh();
            binding.llSharedList.setVisibility(View.VISIBLE);
        }
    }
}
