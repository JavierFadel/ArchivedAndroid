package com.example.parsing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private int number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/todos/1";
        String arrayUrl = "https://jsonplaceholder.typicode.com/todos";

        //  Request an object JSON response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() { // Capture response
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("JSON: ", "onResponseObject: " + response.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON: ", "onErrorResponse: error" );
            }
        });
        // Add request to RequestQueue
        requestQueue.add(jsonObjectRequest);

        // Request an array JSON response from provided URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, arrayUrl,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d("JSON: ", "onResponse: " +
                                jsonObject.getString("id") + " " +
                                jsonObject.getString("title") + " " +
                                jsonObject.getString("userId"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON: ", "onErrorResponse: Error");
            }
        });
        requestQueue.add(jsonArrayRequest);

        //        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://wwww.google.com";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        textView.setText("Response is: " + response.substring(0, 500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                textView.setText(R.string.warning);
//            }
//        });
    }
}
