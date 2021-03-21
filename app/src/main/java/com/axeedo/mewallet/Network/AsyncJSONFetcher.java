package com.axeedo.mewallet.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.axeedo.mewallet.Adapters.CategoryImageListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncJSONFetcher extends AsyncTask<String, Void, JSONObject> {

    URL url;
    CategoryImageListAdapter adapter;
    String authHeader;
    String params;

    public AsyncJSONFetcher(CategoryImageListAdapter adapter, String authHeader) {
        this.adapter = adapter;
        this.authHeader = authHeader;
        params = null;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject json =  null;

        try{
            /*StringBuilder urlBuild = new StringBuilder();
            urlBuild.append(strings[0]);
            if(params!=null){
                urlBuild.append(params);
            }*/
            setUrl(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            try {
                //set connection
                conn.setReadTimeout(10000);//this is in milliseconds
                conn.setConnectTimeout(15000);//this is in milliseconds
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Bearer "+ authHeader);


                //start request
                InputStream in = new BufferedInputStream(conn.getInputStream());
                String s = readStream(in);
                json = new JSONObject(s);
            } catch (JSONException e) {
                Log.i("JFL", "JSONException", e);
            } finally {
                conn.disconnect();
            }
        } catch (MalformedURLException e) {
            Log.i("JFL", "MalformedURLException", e);
        } catch (IOException e) {
            Log.i("JFL", "IOException", e);
        }
        return json;
    }

    private String readStream(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        try {
            for (String line = reader.readLine(); line != null; line =reader.readLine()){
                sb.append(line);
            }
        } catch (IOException e) {
            Log.i("JFL", "IOException", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.i("JFL", "IOException", e);
            }
        }
        return sb.toString();
    }

    public void setUrl(String url) throws MalformedURLException{
        this.url = new URL(url);
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            // todo build a working json parser
            int size = json.getJSONArray("icons").length();
            Log.i("JFL", "Size of Array: " + size);
            for(int i=0; i<size; i++){
                String url = json.getJSONArray("icons").getJSONObject(i)
                        .getJSONArray("raster_sizes").getJSONObject(7)
                        .getJSONArray("formats").getJSONObject(0)
                        .getString("download_url");
                adapter.add(url);
                Log.i("JFL", "Adding to adapter url : " + url);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.i("JFL", "JSONException", e);
        }
    }
}
