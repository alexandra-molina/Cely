package com.example.alexandramolina.cely;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView txt_ayuda;
    private TextView txt_registarse;
    private Button btn_iniciarSesion;
    CallbackManager callbackManager;
    TextView txtBirthday, txtEmail,txtUsuario,txtContrasena;
    ProgressDialog mDialog;
    ImageView imgAvatar;
    SharedPreferences sharedPreferences;
    ActionBar actionBar;
    String usuario, password;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#233a62")));

        txt_ayuda = findViewById(R.id.txt_ayuda);
        txt_registarse = findViewById(R.id.txt_registrarse);
        txtUsuario = findViewById(R.id.txt_usuario);
        txtContrasena = findViewById(R.id.txt_contrasena);
        btn_iniciarSesion = findViewById(R.id.btn_iniciarSesion);



        txt_ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityAyuda();
            }
        });
        txt_registarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityRegistrase();
            }
        });
        btn_iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario=  txtUsuario.getText().toString();
                password= txtContrasena.getText().toString();
                iniciarSesion();


            }
        });

        callbackManager = CallbackManager.Factory.create();

        final LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response", response.toString());
                        getData(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,email,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        if(AccessToken.getCurrentAccessToken() != null){
            //txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
        }
    }



    private void getData(JSONObject object){
        try{
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id")+"/picture?width=250&height=250");

            Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);

            txtEmail.setText(object.getString("email"));
            txtBirthday.setText(object.getString("birthday"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void printKeyHash(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.alexandramolina.cely", PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures){

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void abrirActivityAyuda(){
        Intent intent = new Intent(this, AyudaActivity.class);
        startActivity(intent);
    }
    public void abrirActivityRegistrase(){
        registro();
        Intent intent = new Intent(this, RegistrarseActivity.class);
        startActivity(intent);
    }

    public void abrirActivityTraductor(){
        Intent intent = new Intent(this, Translation.class);
        startActivity(intent);
    }
    public void abrirActivityPrincipal(){

        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    public void iniciarSesion(){

        StringRequest loginRequest = new StringRequest(Request.Method.POST, "https://celytranslate.herokuapp.com/v1/sessions", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                String name="";
                String email="";
                String id="";
                String status="";
                String message="";
                String authentication_token="";
                try {
                    json = new JSONObject(response);
                    status=json.getString("status");
                    message=json.getString("message");
                    if(status.equals("Success")){
                        JSONObject main = new JSONObject(json.getString("data"));
                        name = main.getString("name");
                        email = main.getString("email");
                        id = main.getString("id");
                        authentication_token = main.getString("authentication_token");
                        sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("authentication_token", authentication_token).apply();
                        sharedPreferences.edit().putString("id", id).apply();
                        sharedPreferences.edit().putString("email", email).apply();
                        sharedPreferences.edit().putString("name", name).apply();
                        abrirActivityPrincipal();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Log.d("ERROR",message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",usuario);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(loginRequest);

    }
    public void registro(){

        StringRequest registroRequest = new StringRequest(Request.Method.POST, "https://celytranslate.herokuapp.com/v1/registrations", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Pruebaaaa",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email","andrem.san12@gmail.com");
                params.put("password","1234756");
                params.put("name","La trucha");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(registroRequest);

    }


}
