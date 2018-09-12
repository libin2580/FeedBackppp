package com.meridian.feedback;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import cn.refactor.lib.colordialog.PromptDialog;

import static com.meridian.feedback.Constants.BASE_URL;
import static com.meridian.feedback.Constants.PIN_URL_ORGINAL;
import static com.meridian.feedback.Constants.PIN_URL_ORGINAL_Sharjha;

/**
 * Created by libin on 3/23/2018.
 */

public class Patient_typeActivity extends Activity {
    Button inpatient, outpatient;
    ViewPager viewPager;
    RecyclerView recyclerview;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    LinearLayoutManager layoutManager1;
    Banner_adapter adapter1;
    public int pos = 0;
    public Handler handler;
    JSONArray array1;
    String jsonString;
    static ArrayList<Action_Services_Model> asm;
    Action_Services_Model am;
    String type,count,ontime;
    String  status,status1, agent_id;
    String s="true",location,fullname;
    String log_status;
    ProgressBar progress,progress_loader;
    ImageButton logout;
    View custompopup_view,custompopup_view1;
    PopupWindow category_popupwindow;
    PopupWindow address_popupwindow;
    LinearLayout coordinatorLayout;
    EditText inpatient_id;
    Button submit,submit1;
    LinearLayout close;
    String patient_id;
    char second;
    LinearLayout slider_lay;
    String id,name,mobile,pin,bed,id1,str,email,doctor,specialty;
    RadioGroup radio_cat;
    RadioButton radio_self,radio_insurence,radio_corporate;
    LinearLayout close1;
    String str_radio_categry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.patient_type_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        inpatient = (Button) findViewById(R.id.inpatient);
        outpatient = (Button) findViewById(R.id.outpatient);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        progress = (ProgressBar) findViewById(R.id.progress_bar_login_page);
        slider_lay= (LinearLayout) findViewById(R.id.slider_lay);
        handler=new Handler();

        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        agent_id = preferences.getString("agent_id", null);
        log_status = preferences.getString("log_status", null);
        location = preferences.getString("location", null);
        fullname = preferences.getString("fullname", null);
        System.out.println("<<<<<<<<<<<<<<location>>>>>>>>>>>>>>>" + location);
        layoutManager1 = new LinearLayoutManager(getApplicationContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager1);
        recyclerview.setAdapter(adapter1);
        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(Patient_typeActivity.this);

        boolean i = networkCheckingClass.ckeckinternet();
        if (i) {
            new FetchImage().execute();
        }else {
            Snackbar.with(Patient_typeActivity.this, null)
                    .type(Type.ERROR)
                    .message("No Internet")
                    .duration(Duration.LONG)

                    .show();
        }

        inpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_patient_id();

            }
        });
        outpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_patient_id1();


            }
        });
        final LayoutInflater inflator1 = (LayoutInflater) Patient_typeActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        custompopup_view1 = inflator1.inflate(R.layout.actvity_category_layout, null);

        radio_cat = (RadioGroup)custompopup_view1. findViewById(R.id.radio_cat);
        radio_self = (RadioButton)custompopup_view1. findViewById(R.id.radio_self);
        radio_insurence = (RadioButton)custompopup_view1. findViewById(R.id.radio_insurence);
        radio_corporate = (RadioButton)custompopup_view1. findViewById(R.id.radio_corporate);
        close1= (LinearLayout)custompopup_view1. findViewById(R.id.close1);

        submit1= (Button) custompopup_view1. findViewById(R.id.submit1);
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_popupwindow.dismiss();
            }
        });submit1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (radio_self.isChecked()) {
            str_radio_categry= "Self Pay ";

        } else if (radio_insurence.isChecked()) {
            str_radio_categry = "Insurance";
        } else if(radio_corporate.isChecked()) {
            str_radio_categry="Corporate";
        }
           else {
            str_radio_categry = "";
            }
            if(patient_id!=null) {
            if(status1.equalsIgnoreCase("true")) {
                Intent ips = new Intent(getApplicationContext(), LoginActivity.class);
                ips.putExtra("patient_type", "1");
                ips.putExtra("pin", pin);
                ips.putExtra("name", name);
                ips.putExtra("mobile", mobile);
                ips.putExtra("bed", bed);
                ips.putExtra("email", email);
                ips.putExtra("doctor", doctor);
                ips.putExtra("specialty", specialty);
                ips.putExtra("patient_category", str_radio_categry);
                startActivity(ips);
                finish();

            }else {
                Intent ips = new Intent(getApplicationContext(), LoginActivity.class);
                ips.putExtra("patient_type", "1");
                ips.putExtra("pin", patient_id);
                ips.putExtra("name", "");
                ips.putExtra("mobile", "");
                ips.putExtra("bed", "");
                ips.putExtra("email", "");
                ips.putExtra("patient_category", str_radio_categry);

                startActivity(ips);
                finish();

            }
            }else {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("patient_type", "2");
                i.putExtra("patient_category",str_radio_categry);
                startActivity(i);
                finish();
            }


    }
});

        final LayoutInflater inflator = (LayoutInflater) Patient_typeActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        custompopup_view = inflator.inflate(R.layout.activity_add_new_address, null);
        coordinatorLayout=(LinearLayout) custompopup_view. findViewById(R.id.coordinatorLayout);
        progress_loader = (ProgressBar)custompopup_view. findViewById(R.id.progress_popup);
        inpatient_id = (EditText)custompopup_view. findViewById(R.id.editText);
        submit= (Button) custompopup_view. findViewById(R.id.submit);
        close= (LinearLayout)custompopup_view. findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address_popupwindow.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Patient_typeActivity.this.isFinishing()) {


                    NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(Patient_typeActivity.this);

                    boolean i = networkCheckingClass.ckeckinternet();
                    if (i) {


                        if (inpatient_id.getText().toString().isEmpty()) {
                            Snackbar.with(Patient_typeActivity.this, null)
                                    .type(Type.ERROR)
                                    .message("Please Enter Id")
                                    .duration(Duration.LONG)

                                    .show();
                            // inpatient_id.setError("Enter Id");
                        } else {
                            patient_id = inpatient_id.getText().toString();
                            s= patient_id.substring(0,1);
                            char first = patient_id.charAt(0);
                            if(patient_id.length()>1){
                                 second = patient_id.charAt(1);
                            }

                            String s = Character.toString(first);
                            String s1 = Character.toString(second);

                            System.out.println("<<<<<<<<<<<<<<first>>>>>>>>>>>>>>>>>>" + s);

                            System.out.println("<<<<<<<<<<<<<<second>>>>>>>>>>>>>>>>>>" + s1);
                   if(s.equalsIgnoreCase("Z")&&(s1.equalsIgnoreCase("H"))) {
                       new getDetails().execute();

                   }
                   else {
                       Snackbar.with(Patient_typeActivity.this, null)
                               .type(Type.ERROR)
                               .message("Invalid Id")
                               .duration(Duration.LONG)

                               .show();
                   }

                        }




            }
                    else {
                        Snackbar.with(Patient_typeActivity.this, null)
                                .type(Type.ERROR)
                                .message("No Internet")
                                .duration(Duration.LONG)

                                .show();
                    }

                }
            }
        });


        logout = (ImageButton) findViewById(R.id.close);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new MaterialStyledDialog.Builder(Patient_typeActivity.this)
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

                                String url = BASE_URL +"logout.php?user_id=" + agent_id;


                                StringRequest stringRequestZZ = new StringRequest
                                        (Request.Method.GET, url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Display the first 500 characters of the response string.


                                                System.out.println("login....i" + response);


                                                if (response.contentEquals("\"logout\"")) {

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


        LinearSnapHelper snapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {


                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;

                int position = layoutManager.getPosition(centerView);
                System.out.println("position : " + position);

                int targetPosition = -1;

                if (layoutManager.canScrollHorizontally()) {
                    System.out.println("adapter1.getItemCount() : " + adapter1.getItemCount());
                    try {
                        if (velocityX < 0) {
                            targetPosition = position - 1;
                            if (targetPosition == -1) {
                                pos = targetPosition + 1;
                            } else {
                                pos = pos - 1;
                            }

                            System.out.println("targetPosition : " + targetPosition);
                            System.out.println("pos : " + pos);
                            addBottomDots(pos);
                        } else {
                            targetPosition = position + 1;
                            if (targetPosition == 4) {
                                pos = targetPosition - 1;
                            } else {
                                pos = pos + 1;
                            }
                            System.out.println("targetPosition : " + targetPosition);
                            System.out.println("pos : " + pos);
                            addBottomDots(pos);
                        }


                    } catch (ArrayIndexOutOfBoundsException ae) {
                        ae.printStackTrace();
                    }

                }

                if (layoutManager.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                final int firstItem = 0;
                final int lastItem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                return targetPosition;
            }
        };


        snapHelper.attachToRecyclerView(recyclerview);


        final int speedScroll = 4000;
        final Runnable runnable = new Runnable() {

            boolean flag = true;

            @Override
            public void run() {
                try {
                    if (pos < adapter1.getItemCount()) {
                        if (pos == adapter1.getItemCount() - 1) {
                            flag = false;
                        } else if (pos == 0) {
                            flag = true;
                        }
                        if (flag)
                            pos++;
                        else
                            pos--;

                        recyclerview.smoothScrollToPosition(pos);
                     //            handler.postDelayed(this, speedScroll);
                    }
                    addBottomDots(pos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };



    }

    private void display_patient_id1() {
        try {
            // plus_btn.setVisibility(View.GONE);

            category_popupwindow = new PopupWindow(custompopup_view1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (Build.VERSION.SDK_INT >= 21) {
                category_popupwindow.setElevation(5.0f);
            }
            category_popupwindow.setFocusable(true);
            category_popupwindow.setAnimationStyle(R.style.AppTheme);
            category_popupwindow.showAtLocation(coordinatorLayout, Gravity.CENTER, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void display_patient_id() {
        try {
           // plus_btn.setVisibility(View.GONE);

            address_popupwindow = new PopupWindow(custompopup_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (Build.VERSION.SDK_INT >= 21) {
                address_popupwindow.setElevation(5.0f);
            }
            address_popupwindow.setFocusable(true);
            address_popupwindow.setAnimationStyle(R.style.AppTheme);
            address_popupwindow.showAtLocation(coordinatorLayout, Gravity.CENTER, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param currentPage This is the first paramter to addBottomDots method
     */
    public void addBottomDots(int currentPage) {
        if (array1 != null && array1.length() > 0 & !array1.equals(null)) {
            dots = new TextView[array1.length()];


            int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
            int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

            dotsLayout.removeAllViews();

            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(colorsInactive[currentPage]);
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(colorsActive[currentPage]);
        } else {
            System.out.println(">>>>");
        }

    }



    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    class FetchImage extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * @param strings
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            // JSONArray array1;
            New_HttpHandler h = new New_HttpHandler();
            if(location.equalsIgnoreCase("1")) {
                jsonString = h.makeServiceCall(BASE_URL+"checkstatus.php?location="+location);
            }
            else {
                jsonString = h.makeServiceCall(BASE_URL+"checkstatus.php?location="+location);

            }
            return null;
        }


        /**
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("_________jsonString_____________" + jsonString);


            // making notification bar transparent
            changeStatusBarColor();
            if (jsonString != null && !jsonString.isEmpty() && !jsonString.equals("null")) {
                slider_lay.setVisibility(View.VISIBLE);

                try {

                    JSONObject jsonobject = new JSONObject(jsonString);


                    String status = jsonobject.getString("status");

                    System.out.println("_________status_____________" + status);
                    asm = new ArrayList<Action_Services_Model>();
                    if (status.equals("true")) {
                        array1 = jsonobject.getJSONArray("data");
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject obj = array1.getJSONObject(i);
                            am = new Action_Services_Model();

                         id1 = obj.getString("id");
                            type = obj.getString("type");
                            count = obj.getString("count");
                            ontime = obj.getString("ontime");

                            am.setId(id1);
                            am.setType(type);
                            am.setCount(count);
                            am.setOntime(ontime);


                            asm.add(am);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            recyclerview.setLayoutManager(layoutManager);
                            adapter1 = new Banner_adapter(asm, getApplicationContext());
                            recyclerview.setAdapter(adapter1);
                            addBottomDots(0);
                        }

                    } else {
                        System.out.println("nodots");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }





            }else {
                Snackbar.with(Patient_typeActivity.this, null)
                        .type(Type.ERROR)
                        .message("Something went wrong")
                        .duration(Duration.LONG)

                        .show();
            }
        }


    }



    public class getDetails extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_loader.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            New_HttpHandler h = new New_HttpHandler();
            if(location.equalsIgnoreCase("1")) {
             str = h.makeServiceCall(PIN_URL_ORGINAL+patient_id);
                System.out.println("_________str_____________" + str);

                System.out.println("_________status_____________" + PIN_URL_ORGINAL+patient_id);

            }
            else {
              str = h.makeServiceCall(PIN_URL_ORGINAL_Sharjha+patient_id);
                System.out.println("_________status_____________"+ PIN_URL_ORGINAL_Sharjha+patient_id);


            }
            if(str!=null){

            }
            return str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (s != null) {
                    JSONObject jsonObj = new JSONObject(s);
                    status1 = jsonObj.getString("status");
                    System.out.println("_________status_____________" + status1);
                    if (status1.equalsIgnoreCase("false")) {
                        address_popupwindow.dismiss();

                        new PromptDialog(Patient_typeActivity.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                                .setAnimationEnable(true)
                                .setContentText(getString(R.string.pin_missing))
                                .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                        display_patient_id1();


                                        progress_loader.setVisibility(View.GONE);
                                    }
                                }).show();


                    } else {
                       // address_popupwindow.dismiss();

                        final String data = jsonObj.getString("data");
                        System.out.println("___________data___________" + data);

                        JSONArray array = new JSONArray(data);
                        System.out.println("___________length___________" + array.length());


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobject = array.getJSONObject(i);
                                   /* "id": "1",
                                    "name": "mary kotaparampil thomas",
                                    "pin": "ZH00534600",
                                    "mobile": "5464644646",
                                    "bed": "112",
                                    "Email": "",
                                    "doctor": "Dr. (Col) Ajay Raj Gupta",
                                    "specialty": "Anesthesiology"*/
                            id = jsonobject.getString("id");
                            name = jsonobject.getString("name");
                            mobile = jsonobject.getString("mobile");
                            pin = jsonobject.getString("pin");
                            bed = jsonobject.getString("bed");
                            email = jsonobject.getString("Email");
                            doctor = jsonobject.getString("doctor");
                            specialty = jsonobject.getString("specialty");
                            new Handler().postDelayed(new Runnable() {
                                //

                                @Override
                                public void run() {
                                    address_popupwindow.dismiss();
                                    progress_loader.setVisibility(View.GONE);
                                    display_patient_id1();


                                }
                            }, 2000);
                        }
                    }

                } else {

                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }




        }
    }

}