package com.example.locationapp.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class NetworkManager {
    private static final int SOCKET_TIMEOUT_MS = 5;
    private String url = "some_url";
    String  REQUEST_TAG = "com.example.locationapp";
    private final String TAG = "NetworkManager";

    private static volatile NetworkManager sInstance;
    private RequestQueue mRequestQueue;

    private NetworkManager(Context context) {
        mRequestQueue = getRequestQueue(context);
    }

    public static NetworkManager getInstance(Context context) {
        if(sInstance ==  null) {
            synchronized (NetworkManager.class) {
                if(sInstance == null) {
                    sInstance = new NetworkManager(context);
                }
            }
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }

        return mRequestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        mRequestQueue.add(req);
    }

    public void send(final double lat, final double lng) {
        final HashMap<String, Double> params = new HashMap<String, Double>();
        params.put("lat", lat);
        params.put("lng", lng);
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });
        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }

}
