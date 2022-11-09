package com.example.network;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue requestQueue;
        String url = "https://jsonplaceholder.typicode.com/todos/1";

        // Instantiate the cache.
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        // Set up network to use HttpUrlConnection as HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with cache and network.
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("StringRequest", "onResponse: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("StringRequest", "onErrorResponse: error");
            }
        });
        // requestQueue.add(request);
        MySingleton.getInstance(this).addToRequestQueue(request);
    }
}
