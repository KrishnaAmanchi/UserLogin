package com.example.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent i=getIntent();
        String token=i.getStringExtra("token");
        RequestQueue queue = Volley.newRequestQueue(this);
        Button logout=findViewById(R.id.logout);
        TextView welcome=findViewById(R.id.welcome);
        String url1="http://10.0.2.2:3000/users/me";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response

                        try {
                            String email=response.getString("email");
                            String name=response.getString("name");
                            String welcomeMsg="Hi "+name+" , this is your account";
                            welcome.setText(welcomeMsg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }

        ){

            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();

                headers.put("Authorization", "Bearer "+token);
                return headers;
            }
        };;


        queue.add(getRequest);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag_json_obj = "json_obj_req";

                String url2 = "http://10.0.2.2:3000/users/logout";

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        url2,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Intent in=new Intent(getApplicationContext(),Login.class);
                                startActivity(in);
                            }

                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                VolleyLog.d("Error: "
                                        + error.getMessage());
                                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }) {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();

                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }


                };
                queue.add(jsonObjReq);


            }
        });
    }

}