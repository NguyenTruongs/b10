package com.example.b10.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.b10.MainActivity;
import com.example.b10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button buttomLogin;
    private Button buttonRegister;
    private ProgressBar loading;
    //private static String URL_LOGIN = "http://192.168.1.107:81/Android_LOGIN_REGISTER/login.php";
    private static String URL_LOGIN = "http://192.168.43.118:81/Android_LOGIN_REGISTER/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mapping();

        // click login
        buttomLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();
                Log.d("zzz", "Email : " + mEmail + " , password : " + mPass);

                if (!mEmail.isEmpty() || !mPass.isEmpty()) {  // isEmpty kiểm tra chuỗi email và password có rỗng hay không nếu != null
                    Login(mEmail, mPass);
                    Log.d("zzz", " != null  hiển thị Email : " + mEmail + ", != null  hiển thị password : " + mPass);
                    Log.d("XXX", "Vào Login");
                } else { // nếu == null nhập vào
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                    Log.d("XXX", "Chưa vào Login");
                }
            }
        });

//        click register
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void mapping() {
        loading = findViewById(R.id.loading);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        buttomLogin = findViewById(R.id.button_Login);
        buttonRegister = findViewById(R.id.button_Register);

    }


    private void Login(String email, String password) {

        loading.setVisibility(View.GONE);
        buttomLogin.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("QQQ: ", "Vào 1");
                        Log.d("RRR: ", "RRR 1 : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("RRR", "RRR 2  : " + response);
                            Log.d("RRR", "RRR 3  : " + jsonObject);
                            String success = jsonObject.getString("success");
                            Log.d("RRR", "RRR 4  : " + success);
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            Log.d("QQQ: ", "Vào 2");
                            if (success.equals("1")) {
                                Log.d("QQQ: ", "Vào 3");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();

                                    Toast.makeText(LoginActivity.this, "Success Login. \nYour Name : " + name + "\nYour Email : " + email, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    loading.setVisibility(View.GONE);
                                    Log.d("BBB", response);
                                    Log.d("QQQ: ", "Vào 4");
                                }
                            }

                        } catch (JSONException e) {
                            Log.d("QQQ: ", "Vào 5");
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error 1 " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            buttomLogin.setVisibility(View.VISIBLE);
                            Log.d("CCC", e.getMessage());
                            Log.d("DDD", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        buttomLogin.setVisibility(View.VISIBLE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                Log.d("EEE", "Email : " + email + " , " + "Password : " + password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}