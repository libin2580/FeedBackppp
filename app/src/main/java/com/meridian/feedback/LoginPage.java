package com.meridian.feedback;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginPage extends AppCompatActivity {
    Button login;

    EditText edtpass,edtusrnam,edtloc;
    String usernam, pass;


    ProgressBar progress;
    String agent_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }



        System.out.println("ss"+key);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_login);


        progress = (ProgressBar)findViewById(R.id.progress_bar_login_page);
        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                progress.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        edtusrnam = (EditText) findViewById(R.id.edt_loginuser);
        edtpass = (EditText) findViewById(R.id.edt_loginpass);

        edtloc = (EditText) findViewById(R.id.edt_loginlocation);

        edtpass.setTypeface(Typeface.DEFAULT);
        edtpass.setTransformationMethod(new PasswordTransformationMethod());
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPrefN", MODE_PRIVATE);
        String username= preferences.getString("username",null);
        String password= preferences.getString("password",null);
        System.out.println("passs"+password);
        if(username!=null||password!=null)
        {
            edtusrnam.setText(username);
            edtpass.setText(password);
        }

        SharedPreferences preferences_status = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String chk= preferences_status.getString("log_status",null);
        System.out.println("checkkkk.."+chk);

        if(chk!=null)
        {
            if (chk.contentEquals("login"))
            {
                Intent i = new Intent(getApplicationContext(), Patient_typeActivity.class);
                startActivity(i);
                finish();
            }


        }






        login = (Button) findViewById(R.id.but_proceed_login_page);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log();

            }
        });






    }


    public void log() {

        usernam= edtusrnam.getText().toString();
        pass = edtpass.getText().toString();






      System.out.println("..." + usernam+ "..." + pass );
        if (  usernam.matches("") || pass.matches("") ) {

            Snackbar.with(LoginPage.this, null)
                    .type(Type.ERROR)
                    .message("Empty Fields")
                    .duration(Duration.LONG)

                    .show();

        }

        else {
            progress.setVisibility(ProgressBar.VISIBLE);


            NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
            final boolean i = networkCheckingClass.ckeckinternet();
            System.out.println("login....i" + i);
            if (i) {
                RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
String url1 = Constants.BASE_URL +"login.php?username=" + usernam + "&password=" + pass;


                StringRequest stringRequest1 = new StringRequest
                        (Request.Method.GET, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                //  tv.setText("Response is: "+ response);
                                System.out.println("responseeeee" + response);
                                progress.setVisibility(ProgressBar.GONE);
                                if (response.contentEquals("\"failed\""))
                                {
                                    progress.setVisibility(ProgressBar.GONE);
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            LoginPage.this);
                                    alertDialogBuilder.setTitle("Alert");


                                    alertDialogBuilder.setMessage("Invalid Username and Password");

                                    alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            progress.setVisibility(ProgressBar.GONE);
                                            dialog.dismiss();


                                        }
                                    });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    Window window = alertDialog.getWindow();
                                    window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                    Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                                    //    nbutton.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.butnbakcolr));
                                    nbutton.setTextColor(getApplicationContext().getResources().getColor(R.color.butnbakcolr));



                                }
                              else {
                                    JSONObject jsonObj = null;
                                    String fullname=null,email=null,location=null,log_status=null;
                                    try {

                                        jsonObj = new JSONObject(response);
                                        if( jsonObj.has("agent_id"))
                                        {
                                            agent_id = jsonObj.getString("agent_id");
                                        }
                                        if( jsonObj.has("name"))
                                        {
                                       fullname = jsonObj.getString("name");
                                        }
                                        if( jsonObj.has("location"))
                                        {
                                            location = jsonObj.getString("location");
                                        }
                                        if( jsonObj.has("log_status")) {
                                          log_status = jsonObj.getString("log_status");
                                        }
                                        System.out.println("result" + response);


                                        progress.setVisibility(ProgressBar.GONE);
                                        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();

                                        editor.putString("agent_id",agent_id);
                                        editor.putString("fullname",fullname);
                                        editor.putString("location",location);
                                        editor.putString("log_status","login");
                                        editor.commit();

                                        SharedPreferences preferences1 = getSharedPreferences("MyPref", MODE_PRIVATE);
                                        String userid = preferences1.getString("user_id",null);
                                        System.out.println("useridss" + userid);
                                        progress.setVisibility(ProgressBar.GONE);

                                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginPage.this);
                                        boolean Islogin = Boolean.parseBoolean("true");
                                        prefs.edit().putBoolean("Islogin", Islogin).commit();
                                        Intent is = new Intent(getApplicationContext(), Patient_typeActivity.class);
                                        startActivity(is);
                                        finish();




                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }


                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //tv.setText("That didn't work!");

                            }
                        });

                queue1.add(stringRequest1);
                queue1.getCache().clear();
            }
            else {
                Snackbar.with(LoginPage.this, null)
                        .type(Type.ERROR)
                        .message("Empty Fields")
                        .duration(Duration.LONG)

                        .show();
            }
        }

    }


}
