package com.axeedo.mewallet.CategoryFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.axeedo.mewallet.Adapters.CategoryImageListAdapter;
import com.axeedo.mewallet.Adapters.CategoryListAdapter;
import com.axeedo.mewallet.Database.Category;
import com.axeedo.mewallet.Network.AsyncJSONFetcher;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.Utils.Constants;

/**
 * A fragment representing a list of Items.
 */
public class CategoryImageFragment extends Fragment {

    private Object stopRequestTag;
    ListView listView;
    CategoryImageListAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryImageFragment() {
        super();
        stopRequestTag = new Object();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_image_list, container, false);

        mAdapter = new CategoryImageListAdapter(getContext(), stopRequestTag);
        listView = view.findViewById(R.id.category_image_list);
        listView.setAdapter(mAdapter);

        String url = getString(R.string.image_url) + "?count=10";
        String authHeader = Constants.API_SECRET;
        new AsyncJSONFetcher(mAdapter, authHeader).execute(url);
        return view;
    }
}