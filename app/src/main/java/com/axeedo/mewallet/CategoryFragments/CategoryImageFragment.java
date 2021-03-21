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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.axeedo.mewallet.Adapters.CategoryImageListAdapter;
import com.axeedo.mewallet.Adapters.CategoryListAdapter;
import com.axeedo.mewallet.Database.Category;
import com.axeedo.mewallet.Network.AsyncJSONFetcher;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionFragments.TransactionListFragment;
import com.axeedo.mewallet.Utils.Constants;
import com.axeedo.mewallet.Utils.OnSwitchFragmentListener;

/**
 * A fragment representing a list of Items.
 */
public class CategoryImageFragment extends Fragment {

    private Object stopRequestTag;
    // A Recyler view may be better for performance and stability here.
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

        String url = getString(R.string.image_url) + "?count=10";

        mAdapter = new CategoryImageListAdapter(getContext(), stopRequestTag);

        OnSwitchFragmentListener mParentListener = (OnSwitchFragmentListener) getContext();
        EditText param = view.findViewById(R.id.category_search_input);
        Button search = view.findViewById(R.id.category_search_btn);
        search.setOnClickListener((View v) -> {
            mAdapter.clear();
            String params = param.getText().toString();
            String urltmp;
            if(params == "" || params == null){
                urltmp = getString(R.string.image_url) + "?count=10";
            } else {
                urltmp = getString(R.string.image_url) + "?query=" + params + "&count=10";
            }
            fetchImage(urltmp);
            mAdapter.notifyDataSetChanged();
        });


        fetchImage(url);

        listView = view.findViewById(R.id.category_image_list);
        listView.setAdapter(mAdapter);

        return view;
    }

    public void fetchImage(String url){
        String authHeader = Constants.API_SECRET;
        new AsyncJSONFetcher(mAdapter, authHeader).execute(url);
    }
}