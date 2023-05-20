package com.example.experimental.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login {
    private static final String API_URL = "http://capacitaciones-continuas-ista.us-east-1.elasticbeanstalk.com/auth/login";

    Context context;

    public Login(Context context) {
        this.context = context;
    }

    public void login(String username, String password, final OnLoginResponseListener responseListener) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_URL, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta exitosa aquí
                        responseListener.onLoginSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud aquí
                        responseListener.onLoginFailure(error.getMessage());
                    }
                });

        Volley.newRequestQueue(context).add(request);
    }

    public interface OnLoginResponseListener {
        void onLoginSuccess(String response);
        void onLoginFailure(String error);
    }
}