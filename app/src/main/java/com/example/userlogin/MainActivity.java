package com.example.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void signUp(View view){
        RequestQueue queue = Volley.newRequestQueue(this);
        Button signup=findViewById(R.id.signup);
        EditText email=findViewById(R.id.email);
        EditText password=findViewById(R.id.password);
        EditText name=findViewById(R.id.name);

        String tag_json_obj = "json_obj_req";

        String url = "http://10.0.2.2:3000/users";

        JSONObject jsonParams=new JSONObject();
        try {
            jsonParams.put("name",name.getText().toString());
            jsonParams.put("email",email.getText().toString());
            jsonParams.put("password",password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Intent in=new Intent(getApplicationContext(),User.class);
                        try {
                            in.putExtra("token",response.getString("token"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(in);
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        VolleyLog.d("Error: "
                                + error.getMessage());

                    }
                }) {



        };
        queue.add(jsonObjReq);


    }

    public void gotoLogin(View view){

        Intent logPage=new Intent(getApplicationContext(),Login.class);

        startActivity(logPage);

    }


}