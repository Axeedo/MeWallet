package com.axeedo.mewallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.axeedo.mewallet.CategoryFragments.CategoryImageFragment;
import com.axeedo.mewallet.CategoryFragments.CategoryListFragment;
import com.axeedo.mewallet.TransactionFragments.TransactionListFragment;
import com.axeedo.mewallet.Utils.OnSwitchFragmentListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    OnSwitchFragmentListener mParentListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button categories = view.findViewById(R.id.category_list_btn);
        Button transactions = view.findViewById(R.id.transaction_list_btn);
        Button images = view.findViewById(R.id.image_list_btn);
        mParentListener = (OnSwitchFragmentListener) getContext();

        categories.setOnClickListener((View v) -> {
            mParentListener.goToFragment(CategoryListFragment.class, null);
        });
        transactions.setOnClickListener((View v) -> {
            mParentListener.goToFragment(TransactionListFragment.class, null);
        });

        images.setOnClickListener((View v) -> {
            mParentListener.goToFragment(CategoryImageFragment.class, null);
        });
        return view;
    }
}