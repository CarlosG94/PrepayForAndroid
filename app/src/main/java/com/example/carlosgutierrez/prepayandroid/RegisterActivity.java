package com.example.carlosgutierrez.prepayandroid;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlosgutierrez on 7/31/14.
 */
public class RegisterActivity extends Activity {
    private String BASE_URL = "http://prepago.herokuapp.com/api/signup";
    public EditText userPhone;
    public EditText userEmail;
    public EditText userPassword;
    public EditText userPasswordConfirmation;
    public EditText userPin;
    private String REQUESTBIN_URL = "http://requestb.in/sqnq7qsq";
    //TODO email and phone must be unique.
    //private String DUMMY_URL = "http://prepago.herokuapp.com/api/signup?user[email]=f1@example.com&user[password]=password&user[password_confirmation]=password&user[pin]=1234&user[phone]=1011";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userEmail = (EditText) findViewById(R.id.emailEditText);
        userPhone = (EditText) findViewById(R.id.phoneEditText);
        userPin = (EditText) findViewById(R.id.pinEditText);
        userPassword = (EditText) findViewById(R.id.editTextPassword);
        userPasswordConfirmation = (EditText) findViewById(R.id.confirmPasswordEditText);
    }

    public void registerUser(View v) {
        //TODO Use the register service.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, REQUESTBIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        VolleyLog.v("Volley", response);
                        Toast.makeText(RegisterActivity.this,
                                "Response: " + response, Toast.LENGTH_LONG)
                                .show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
                Log.e("Request", "" + this.toString());
                VolleyLog.e("Volley Error", "" + error.toString());
                Toast.makeText(RegisterActivity.this,
                        "Response: " + error.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user[email]", "" + userEmail.getText().toString());
                params.put("user[password]", "" + userPassword.getText().toString());
                params.put("user[password_confirmation]", "" + userPasswordConfirmation.getText().toString());
                params.put("user[pin]", "" + userPin.getText().toString());
                params.put("user[phone]", "" + userPhone.getText().toString());
                return params;
            }
        };
        // use setRetryPolicy to reconfigure retry policies.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
        // add the request object to the queue to be executed.
        requestQueue.add(stringRequest);
    }
}
