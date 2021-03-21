package com.axeedo.mewallet.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.axeedo.mewallet.Network.ImageRequestQueue;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.Utils.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CategoryImageListAdapter extends BaseAdapter {

    private Vector<String> urls;
    private final LayoutInflater mInflater;
    private final Object stopRequestTag;

    public CategoryImageListAdapter(Context context, Object stopTag){
        mInflater = LayoutInflater.from(context);
        urls = new Vector<>();
        stopRequestTag = stopTag;
    }
    public void add(String url){
        urls.add(url);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Image list
        RequestQueue queue = ImageRequestQueue.getInstance(parent.getContext()).
                getRequestQueue();
        //Alternatively, we can use Volley's pre-made queue:
        // RequestQueue requestQueue = Volley.newRequestQueue(parent.getContext())

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.category_image_item, parent, false);
        }

        View finalV = convertView;
        ImageRequest imageRequest = new ImageRequest(
                (String)getItem(position),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ((ImageView)finalV.findViewById(R.id.itemCategoryImage)).setImageBitmap(response);
                        Log.i("JFL", "New Bitmap inflated");
                        //return response;
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ((ImageView)finalV.findViewById(R.id.itemCategoryImage)).setImageResource(R.drawable.ic_launcher_background);
                        Log.i("JFL", "Bitmap not inflated");
                    }
                }

        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.API_SECRET);
                return headers;
            }
        };
        imageRequest.setTag(stopRequestTag);
        queue.add(imageRequest);
        return finalV;
    }
}
