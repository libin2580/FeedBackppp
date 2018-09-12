package com.meridian.feedback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;



public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView bsignup;
    TextView Reg, txtnew, gst;
    EditText edt_loginname, edt_loginemail;
    String usernam, pass, str_email, str_pass, str_number, str_radio_patient, str_radio_place, str_name,str_room,str_phone;
    ProgressDialog pd;
    TextView fb, twtr, gpls, lnkdlin, frgt;
    WebView wv;
    // ArrayList<UserDetailsModel> arr_usrs = new ArrayList<>();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String eml, status, agent_id,sname,department;

//String REGISTER_URL = "http://zulekhahospitals.com/feedback/start.php?";
  //String MOB_URL = "http://zulekhahospitals.com/feedback/mobile.php?";

    String BASE_URL ="http://zulekhahospitals.com/feedback/";
    String demo="http://192.200.125.44/zulekhafeedback/";

 String MOB_URL1 = "http://192.200.125.44/zulekhafeedback/mobile.php?";
    String REGISTER_URL1 = "http://192.200.125.44/zulekhafeedback/start.php?";
    ProgressBar progress;
    TextView forgotpasswrd;
    RadioGroup radiopatient, radioplace,radio_language;
    RadioButton radio_id_patient, radio_id_place,radio_english,radio_arabic;
    RadioButton radio_inpatient, radio_outpatient, radio_sh, radio_dubai;
    int patient = 0, place = 0;
    static ArrayList<QuestionModel> questionsArrayList;
    boolean edittexterror = false;
    String user_id, suser_id;
    EditText edt_loginnumber,edt_room_number;
    String log_status,patient_type,patient_category;
    String regexStr = "^[+]?[0-9]{10,13}$";
    ImageButton logout;
    String k,s="true",location,fullname;
    RelativeLayout relative_room_number;
    SharedPreferences langusge_pref;
    String selected_language="en",name,mobile,bed,pinno,pin,email,doctor,specialty;
    String patienttype,doctor_nw,specialty_nw;
    LinearLayout type_layout;
    RelativeLayout doc_name,spclty_name;
    EditText edt_doc_name,edt_spclty_name;
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
            }
                Log.e("Key Hash=", key);
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }


        System.out.println("ss" + key);
        overridePendingTransition(0, 0);
        setContentView(R.layout.login_new);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        patient_type=getIntent().getStringExtra("patient_type");
        patient_category=getIntent().getStringExtra("patient_category");
        System.out.println("<<<<<<<<<<<patient_category>>>>>>>>>>>"+patient_category);

        pin=getIntent().getStringExtra("pin");

        name=getIntent().getStringExtra("name");

        mobile=getIntent().getStringExtra("mobile");
        bed=getIntent().getStringExtra("bed");

        email=getIntent().getStringExtra("email");
        doctor=getIntent().getStringExtra("doctor");
        specialty=getIntent().getStringExtra("specialty");
        System.out.println("<<<<<<<<<<<name>>>>>>>>>>>"+name);
        System.out.println("<<<<<<<<<<<pin>>>>>>>>>>>"+pin);
        System.out.println("<<<<<<<<<<<mobile>>>>>>>>>>>"+mobile);
        System.out.println("<<<<<<<<<<<name>>>>>>>>>>>"+bed);
        System.out.println("<<<<<<<<<<<email>>>>>>>>>>>"+email);

        langusge_pref = getSharedPreferences("LangPref", MODE_PRIVATE);
        SharedPreferences.Editor lng_edtr = langusge_pref.edit();
        lng_edtr.putString("language", "en");
        lng_edtr.commit();

        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        agent_id = preferences.getString("agent_id", null);
        log_status = preferences.getString("log_status", null);
        location = preferences.getString("location", null);
        fullname = preferences.getString("fullname", null);
        logout = (ImageButton) findViewById(R.id.close);
System.out.println("<<<<<<<<<<<patient_type>>>>>>>>>>>"+patient_type);
        relative_room_number=(RelativeLayout)findViewById(R.id.relative_room_number);
        doc_name=(RelativeLayout)findViewById(R.id.doc_name);
        spclty_name=(RelativeLayout)findViewById(R.id.spclty_name);

        edt_room_number=(EditText)findViewById(R.id.edt_room_number);
        radio_language=(RadioGroup)findViewById(R.id.radio_language);
        radio_english = (RadioButton) findViewById(R.id.radio_english);
        radio_arabic = (RadioButton) findViewById(R.id.radio_arabic);
        type_layout= (LinearLayout) findViewById(R.id.type_layout);
        radiopatient = (RadioGroup) findViewById(R.id.radio_patient);
      //  radioplace = (RadioGroup) findViewById(R.id.radio_place);
        radio_inpatient = (RadioButton) findViewById(R.id.radio_inpatient);
        radio_outpatient = (RadioButton) findViewById(R.id.radio_outpatient);
//        radio_sh = (RadioButton) findViewById(R.id.radio_sharjah);
//        radio_dubai = (RadioButton) findViewById(R.id.radio_dubai);
        forgotpasswrd = (TextView) findViewById(R.id.txt_forgot);
        logout = (ImageButton) findViewById(R.id.close);
        forgotpasswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlertDialog cc = new CustomAlertDialog(LoginActivity.this);
                cc.show();
            }
        });
        edt_loginname = (EditText) findViewById(R.id.edt_loginname);
        edt_loginemail = (EditText) findViewById(R.id.edt_loginemail);
        edt_doc_name=(EditText) findViewById(R.id.edt_doc_name);
        edt_spclty_name=(EditText) findViewById(R.id.edt_spclty_name);
        edt_loginnumber = (EditText) findViewById(R.id.edt_loginnumber);

        Selection.setSelection(edt_loginnumber.getText(), edt_loginnumber.getText().length());
        type_layout.setVisibility(View.GONE);
        if (name==null){
            edt_loginname.setText("");

        }else {
            edt_loginname.setText(name);

        }
        if (mobile==null){
            edt_loginnumber.setText("+971");

        }else {
            edt_loginnumber.setText("+971"+mobile);

        }
        if(email==null){
            edt_loginemail.setText("");

        }else {
            edt_loginemail.setText(email);

        }
        if(doctor!=null){
            edt_doc_name.setText(doctor);
        }else {
            doc_name.setVisibility(View.VISIBLE);
        }
        if(specialty!=null){
            edt_spclty_name.setText(specialty);
        }else {
            spclty_name.setVisibility(View.VISIBLE);
        }


        if (patient_type.equalsIgnoreCase("2")){
           relative_room_number.setVisibility(View.GONE);
            doc_name.setVisibility(View.GONE);
            spclty_name.setVisibility(View.GONE);
           patient = 2;
           pin="-";
           str_room="-";

       }else{

           relative_room_number.setVisibility(View.VISIBLE);
           edt_room_number.setText(bed);
           patient = 1;
       }
        radiopatient.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radio_inpatient)
                {
                    edt_room_number.setText(bed);
                    relative_room_number.setVisibility(View.VISIBLE);
                }else if(i==R.id.radio_outpatient){
                    relative_room_number.setVisibility(View.GONE);
                }
            }
        });

        radio_language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radio_english)
                {
                    SharedPreferences.Editor lng_edtr = langusge_pref.edit();
                    lng_edtr.putString("language", "en");
                    lng_edtr.commit();
                    selected_language="en";
                }else if(i==R.id.radio_arabic){
                    SharedPreferences.Editor lng_edtr = langusge_pref.edit();
                    lng_edtr.putString("language", "ar");
                    lng_edtr.commit();
                    selected_language="ar";
                }
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new MaterialStyledDialog.Builder(LoginActivity.this)
                        // .setTitle("SUCCESS!")
                        .setDescription("Do you want to Logout " + fullname)
                        .setTitle("LOGOUT")
                        .setStyle(Style.HEADER_WITH_TITLE)
                        // .withDialogAnimation(true)
                        .setHeaderColor(R.color.colorPrimary)
                        .setCancelable(true)
                        .setPositive(getResources().getString(R.string.yes), new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(final MaterialDialog dialog, DialogAction which) {

                                progress.setVisibility(ProgressBar.VISIBLE);


                                NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
                                final boolean i = networkCheckingClass.ckeckinternet();

                                RequestQueue queuezz = Volley.newRequestQueue(getApplicationContext());
                   //  String url = "http://zulekhahospitals.com/feedback/logout.php?user_id=" + agent_id;

                      String url = Constants.BASE_URL +"logout.php?user_id=" + agent_id;


                                StringRequest stringRequestZZ = new StringRequest
                                        (Request.Method.GET, url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Display the first 500 characters of the response string.
                                                //  tv.setText("Response is: "+ response);


                                                System.out.println("login....i" + response);


                                                if (response.contentEquals("\"logout\"")) {

                                                    //  String named = jsonObj.getString("name");
                                                    progress.setVisibility(ProgressBar.GONE);


                                                    SharedPreferences preferences1 = getSharedPreferences("MyPref", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = preferences1.edit();
                                                    editor.putString("log_status", "logout");
                                                    editor.putString("agent_id", null);
                                                    editor.clear();
                                                    editor.apply();
                                                    editor.commit();
                                                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                                    String log_status = preferences.getString("log_status", null);
                                                    String agent_id = preferences.getString("agent_id", null);

                                                    System.out.println("useridss1" + agent_id + log_status);
                                                    Intent is = new Intent(getApplicationContext(), LoginPage.class);
                                                    is.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(is);
                                                    finish();
                                                }


                                            }


                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                dialog.dismiss();
                                                //tv.setText("That didn't work!");

                                            }
                                        });

                                queuezz.add(stringRequestZZ);


                            }
                        })
                        .setNegative(getResources().getString(R.string.no), new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            }


        });

        edt_loginnumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
// TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
// TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("ssss" + s + "+......" + edt_loginnumber.getText().toString());
                if (!s.toString().startsWith("+971")) {
                    edt_loginnumber.setText("+971");
                    Selection.setSelection(edt_loginnumber.getText(), edt_loginnumber.getText().length());
                    str_number = edt_loginnumber.getText().toString();

                    k = str_number.replace("+971", "");


                }
            //    new SendToServer().execute();
                mob(String.valueOf(s));


            }

        });



        int selectedId_patient = radiopatient.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radio_id_patient = (RadioButton) findViewById(selectedId_patient);


        progress = (ProgressBar) findViewById(R.id.progress_bar_login_activity);
        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                progress.setVisibility(View.INVISIBLE);
                return false;
            }
        });



        wv = new WebView(this);

        if (radio_outpatient.isChecked()) {
            str_radio_patient = "Outpatient";


        } else if (radio_inpatient.isChecked()) {
            str_radio_patient = "Inpatient";

        } else {
            str_radio_patient = "";
            edittexterror = true;
        }


        login = (Button) findViewById(R.id.but_proceed_login_activity);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patient_type.equalsIgnoreCase("2")) {
                    if (s.contentEquals("true")) {
                        edt_loginnumber.setError("Should be minimum 9");
                    } else {
                        log();

                        startService(new Intent(getBaseContext(), MyService.class));
                    }
                }
                else {
                    log();

                    startService(new Intent(getBaseContext(), MyService.class));
                }
            }
        });


    }


    public void log() {
        str_email = edt_loginemail.getText().toString();
        str_name = edt_loginname.getText().toString();
        str_number = edt_loginnumber.getText().toString();
        str_room=edt_room_number.getText().toString();


        if (radio_outpatient.isChecked()) {
            str_radio_patient = "Outpatient";
            str_radio_patient = "Outpatient";
        } else if (radio_inpatient.isChecked()) {
            str_radio_patient = "Inpatient";
        } else {
            str_radio_patient = "";
        }

        if (str_radio_patient.contentEquals("Inpatient")) {
            patient = 1;
        } else {
            patient = 2;
        }

        System.out.println("..." + str_name + "...." + str_email + "..." + str_number + "..." + str_name + "..." + str_radio_patient + str_radio_place + patient + "..." + place);


        if(str_name.matches(""))
        {
            Snackbar.with(LoginActivity.this, null)
                    .type(Type.ERROR)
                    .message("Enter Name")
                    .duration(Duration.LONG)

                    .show();
            edittexterror = true;
        }

        else if(radio_inpatient.isChecked())
        {
            if(str_room.matches(""))
            {
                Snackbar.with(LoginActivity.this, null)
                        .type(Type.ERROR)
                        .message("Please Enter Room Number")
                        .duration(Duration.LONG)

                        .show();
                edittexterror = true;
            }else{
                edittexterror = false;
            }
        }else {
            edittexterror = false;
        }




        if (edittexterror == false)
        {
            str_email = edt_loginemail.getText().toString();
            str_name = edt_loginname.getText().toString();
            str_number = edt_loginnumber.getText().toString();
            str_room=edt_room_number.getText().toString();
            final String k = str_number.replace("+971", "");


            langusge_pref.edit().putString("roomno",str_room).commit();

            if (str_radio_patient.contentEquals("Inpatient")) {
                patient = 1;
            } else {
                patient = 2;
            }


            System.out.println("details..." + str_name + "...." + str_email + "..." + k + "..." + str_name + "..." + str_radio_patient + str_radio_place + patient + "..." + place);

            str_phone=k;
            // progress.setVisibility(ProgressBar.VISIBLE);
            NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
            boolean i = networkCheckingClass.ckeckinternet();

            if (i) {

                progress.setVisibility(ProgressBar.VISIBLE);
                SharedPreferences myPrefs = getSharedPreferences("LangPref", Context.MODE_PRIVATE);
                String language = myPrefs.getString("language", null);
                if(language.equals("ar")) {
                    Locale locale2 = new Locale("ar");
                    Locale.setDefault(locale2);
                    DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getBaseContext().getResources().updateConfiguration(config2, displayMetrics);
                }else if(language.equals("en")) {
                    Locale locale2 = new Locale("en");
                    Locale.setDefault(locale2);
                    DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getBaseContext().getResources().updateConfiguration(config2, displayMetrics);
                }


               // LanguageCheck lc=new LanguageCheck(getApplicationContext());
//demo : http://zulekhahospitals.com/feedback/demo/login.php
//original : Constants.BASE_URL +"start.php?
                System.out.println("----------- "+Constants.BASE_URL+"start.php?");
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL+"start.php?",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse( String response) {
                                //  progress.setVisibility(ProgressBar.GONE);

                                // JSONObject jsonObj = null;
                                //  jsonObj = new JSONObject(response);
                                System.out.println("responseeeee" + response);
                                String agentid = null,ques_id=null,question = null,email=null,phone=null,usertype=null,locations=null;
                                // statusd = jsonObj.getString("status");

                                progress.setVisibility(ProgressBar.GONE);
                                // pd.dismiss();
                                if (response != null && response.length() > 0)

                                {
                                    try {
                                        questionsArrayList = new ArrayList<>();
                                        JSONArray jsonarray = new JSONArray(response);
                                        System.out.println("00");
                                        for (int i = 0; i < jsonarray.length(); i++)
                                        {
                                            QuestionModel questionModel = new QuestionModel();
                                            System.out.println("01");
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            if(jsonobject.has("agentid")) {
                                                agentid = jsonobject.getString("agentid");
                                            }
                                            if(jsonobject.has("ques_id")) {
                                                ques_id= jsonobject.getString("ques_id");
                                            }
                                            if(jsonobject.has("question")) {
                                                question = jsonobject.getString("question");
                                            }   if(jsonobject.has("user_id")) {

                                            suser_id = jsonobject.getString("user_id");
                                        }
                                            if(jsonobject.has("name")) {
                                                sname = jsonobject.getString("name");
                                            }
                                            if(jsonobject.has("department")) {
                                                department = jsonobject.getString("department");
                                            }
                                            if(jsonobject.has("email")) {
                                                email = jsonobject.getString("email");
                                            }
                                            if(jsonobject.has("phoneno")) {
                                                phone = jsonobject.getString("phoneno");
                                            }
                                            if(jsonobject.has("usertype")) {
                                                usertype = jsonobject.getString("usertype");
                                            }
                                            if(jsonobject.has("location")) {
                                                locations = jsonobject.getString("location");
                                            }
                                            if(jsonobject.has("pinno")) {
                                                pinno = jsonobject.getString("pinno");
                                            }
                                            if(jsonobject.has("patienttype")){
                                                patienttype=jsonobject.getString("patienttype");
                                                System.out.println("<<<<<<<<<>>>>>>>>>>>>>>>>>" + patienttype);

                                            }
                                            if(jsonobject.has("doctor")){
                                                doctor_nw=jsonobject.getString("doctor");
                                                System.out.println("<<<<<<<<<>>>>>>>>>>>>>>>>>" + doctor_nw);

                                            }
                                            if(jsonobject.has("specialty")){
                                                specialty_nw=jsonobject.getString("specialty");
                                                System.out.println("<<<<<<<<<>>>>>>>>>>>>>>>>>" + specialty_nw);

                                            }
                                            questionModel.setDepartment(department);
                                            questionModel.setUser_id(user_id);
                                            questionModel.setAgentid(agentid);
                                            questionModel.setPerson_name(sname);
                                            questionModel.setQuestid(ques_id);
                                            questionModel.setQuestname(question);
                                            questionModel.setPinno(pinno);
                                            questionModel.setDoctor(doctor);
                                            questionModel.setPatienttype(patienttype);
                                            questionModel.setSpecialty(specialty);
                                            questionsArrayList.add(questionModel);
                                            System.out.println("userrrsname" + suser_id + sname);

                                        }


                                        SharedPreferences preferences = getSharedPreferences("MyPref_login", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        //   editor.putString("log_status","login");
                                        editor.putString("user_id", suser_id);
                                        editor.putString("name",sname);
                                        editor.putString("department",department);
                                        editor.putString("email",email);
                                        editor.putString("phone",phone);
                                        editor.putString("usertype",usertype);
                                        editor.putString("pinno",pinno);
                                        editor.putString("location",locations);
                                        editor.putString("doctor",doctor);
                                        editor.putString("specialty",specialty);
                                        editor.putString("patienttype",patienttype);

                                        editor.commit();




                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Intent intent = new Intent(LoginActivity.this, QuestionsNwnw.class);
                                    intent.putExtra("user_ids", suser_id);
                                    intent.putExtra("place", String.valueOf(place));
                                    System.out.println("placeeeeeeee..value" + String.valueOf(place));

                                    /// intent.putExtra("QuestionListExtra", ArrayList<QuestionModel>questionsArrayList);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Snackbar.with(LoginActivity.this, null)
                                        .type(Type.ERROR)
                                        .message("Something went wrong.Please try again later.")
                                        .duration(Duration.LONG)

                                        .show();
                                progress.setVisibility(ProgressBar.GONE);
//
//                                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

//meridian.net.in/demo/etsdc/response.php?fid=1&email=" + email + "&phone=" + phon + "&name=" + fulnam + "&occupation=" + occ + "&location=" + loc + "&password=" + pass

                        System.out.println("userrrri_refff" + str_email + k + str_name + agent_id + location);
                        params.put("name", str_name);
                        params.put("phoneno", k);
                        params.put("email", str_email);
                        params.put("agentid", agent_id);

                        params.put("patienttype",patient_category);



                        params.put("usertype", patient_type);
                        params.put("location", location);
                        if(doctor!=null){
                            params.put("doctor",doctor);

                        }else {
                            params.put("doctor","-");

                        }
                        if(specialty!=null){
                            params.put("specialty",specialty);

                        }else {
                            params.put("specialty","-");

                        }
                        if(str_room.trim()!="") {
                            params.put("roomno", str_room);
                            params.put("pinno", pin);

                        }
                        if(selected_language.equals("en")){
                            params.put("language", "EN");
                        }else{
                            params.put("language", "AR");
                        }
                        System.out.println("----------- params : "+params);
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                int socketTimeout = 30000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);
                requestQueue.getCache().clear();

            } else {
                Snackbar.with(LoginActivity.this, null)
                        .type(Type.ERROR)
                        .message("No internet connection")
                        .duration(Duration.LONG)

                        .show();

            }
        }
    }

    public static ArrayList<QuestionModel> questn() {

        return questionsArrayList;

    }

    @Override
    public void onBackPressed() {
        new MaterialStyledDialog.Builder(LoginActivity.this)
                .setTitle("EXIT")
                .setDescription("Do you want to Exit Feedback App?")
                .setStyle(Style.HEADER_WITH_TITLE)
                // .withDialogAnimation(true)
                .setHeaderColor(R.color.colorPrimary)
                .setCancelable(true)
                .setPositive(getResources().getString(R.string.yes), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(final MaterialDialog dialog, DialogAction which) {

                        dialog.dismiss();


                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.putExtra("EXIT", true);
                        startActivity(intent);
// dialog.dismiss();
                        finish();





                    }
                })
                .setNegative(getResources().getString(R.string.no), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                      dialog.dismiss();
                    }
                })
                .show();
    }

    public void mob(final String number) {
        final String k=number.replace("+971","");
        if(k.length()!=9)
        {edittexterror=true;

            System.out.println("99999");

            s="true";
        }
        else {
            s="false";
            edittexterror=false;
        }


        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
        boolean i = networkCheckingClass.ckeckinternet();

        if (i) {
            progress.setVisibility(ProgressBar.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL +"mobile.php?",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            //  progress.setVisibility(ProgressBar.GONE);

                            // JSONObject jsonObj = null;
                            //  jsonObj = new JSONObject(response);
                            System.out.println("responseeeeemobileee....." + response);
                            // statusd = jsonObj.getString("status");

                            progress.setVisibility(ProgressBar.GONE);
                            // pd.dismiss();
                            JSONObject jsonObj = null;
                            String name, email;
                            try {


                                if (response != null)
                                {
                                    if (response.equals("\"failed\""))
                                    {



                                    }
                                    else
                                        {

                                        try {
                                            jsonObj = new JSONObject(response);
                                            //  name = jsonObj.getString("name");

                                            if (jsonObj.has("name")) {

                                                name = jsonObj.getString("name");
                                                System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>" + name);
                                                edt_loginname.setText(name);
                                            }
                                            if (jsonObj.has("email")) {
                                                email = jsonObj.getString("email");
                                                edt_loginemail.setText(email);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                         }


                                }

                            }
                            catch (Exception e)
                            {

                            }

//                            {
//
//                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress.setVisibility(ProgressBar.GONE);
                            /*Snackbar.with(LoginActivity.this, null)
                                    .type(Type.ERROR)
                                    .message(error.toString())
                                    .duration(Duration.LONG)

                                    .show();*/
//
//                                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

//meridian.net.in/demo/etsdc/response.php?fid=1&email=" + email + "&phone=" + phon + "&name=" + fulnam + "&occupation=" + occ + "&location=" + loc + "&password=" + pass

                    System.out.println("userrrri_refff" + k);
                    params.put("mobile",k);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
            requestQueue.getCache().clear();


        } else {
            Snackbar.with(LoginActivity.this, null)
                    .type(Type.ERROR)
                    .message("No internet connection")
                    .duration(Duration.LONG)

                    .show();

        }


    }



   public Boolean Number_Validate(String number)
    {
        return  !TextUtils.isEmpty(number) && (number.length()==9) && android.util.Patterns.PHONE.matcher(number).matches();
    }





}
