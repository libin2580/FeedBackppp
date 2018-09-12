package com.meridian.feedback;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.google.gson.Gson;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionsNwnw extends AppCompatActivity {
    ImageButton but_excelnt, but_good, but_poor,but_excelntlrg, but_poorlrg, but_goodlrg;
    Button submit_feedback;
    int cnt_exclnt = 0, cnt_good = 0, cnt_poor = 0, cnt_verypoor = 0;
    ArrayList<QuestionModel> questionsArrayList;
    TextView txt_questn_number, txt_questn_name;
    ProgressBar progress;
    ImageView ic_send;
    String q;
    String starttext ="";
    int id = 0;
    StringBuffer sb_questionid, sb_answer, sb_department, sb_suggestions, sb_recommend,sb_poor_department,sb_overall_satisfcation;

    ArrayList<JsonModel> jsonModelArrayList;
    String userid, user_id, question;
    EditText  edt_suggestins;

    RadioButton radio_yes, radio_no;
    TextView txt_appreciate;
    String  suggestions,name;
    Animation animation;
    HTextView department_name;


    RadioGroup radio_recommend;
    String newtext="";
    boolean btngoodclicked = false;
    boolean btnexcellentclicked = false;

    boolean btnpoorclicked = false;

    private static String[]stockArr;
    AutoCompleteTextView actv;
    String place;
    LinearLayout linearLayout_recommend, linearLayout_smily, lay_disatis, lay_suggestions, lay_questions, lay_department,lay_appreciate;
    String agentid,new_user_id,pinno;
    PopupWindow  mPopupWindow ;
    TextView popup_txt_thanks,popup_txt_inpatient_count,popup_txt_outpatient_count;
    LinearLayout popup_txt_ok;

    RelativeLayout questnz;
    String department="",username="",email="",location="",usertype="",phone="";
    SharedPreferences langusge_pref;
    String selected_language="";

    //new Changes 5/14/2018
    ImageButton but_vgood,but_vgoodlrg,but_fairlrg,but_fair;
    ArrayList<String> dept=new ArrayList<>();
    ArrayList<String> ans=new ArrayList<>();
    ArrayList<String> qst_id=new ArrayList<>();
    boolean btnvgoodclicked = false;
    boolean btnfairclicked=false;
    int cnt_vgood = 0, cnt_fair = 0;
    ImageView back;
    SharedPreferences preferences;
    LinearLayout coordinatorLayout,disast_coordinatorLayout;
    View custompopuprecomend_view,custom_disast_view;
    PopupWindow recomend_popupwindow;
    RadioGroup radio_recommends,radio_recommends_d;
    RadioButton radio_checked_yess_d,radio_checked_noo_d;
    boolean isChecked;
    RadioButton radio_checked_yess,radio_checked_noo;
    LinearLayout pop_back;
    EditText popup_edt_suggestins,popup_edt_suggestins_d;
    Button popup_submit_feedback,popup_submit_feedback_d;
    ProgressBar progress_bar_questions,popup_progress_bar_questions_d;
    AutoCompleteTextView pop_actv;
    ImageView disast_back,send_deprt;
    boolean flag=false;
    RelativeLayout poor_sup;
    LinearLayout activity_questions;
    String str_radio_categry;

    String status="";
    String inpatient_count="",outpatient_count="";
    ArrayList<String> nw_array=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_questions);



        langusge_pref = getSharedPreferences("LangPref", MODE_PRIVATE);
        selected_language=langusge_pref.getString("language",null);
        System.out.println("selected_language : "+selected_language);


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        new_user_id=getIntent().getStringExtra("user_ids");
        place=getIntent().getStringExtra("place");
        System.out.println("placeeeeee"+place);
        but_good = (ImageButton) findViewById(R.id.but_good);
        but_poor = (ImageButton) findViewById(R.id.but_poor);


        //new Changes 5/14/2018
        but_vgood = (ImageButton) findViewById(R.id.but_vgood);
        but_fair= (ImageButton) findViewById(R.id.but_fair);
        but_vgoodlrg = (ImageButton) findViewById(R.id.but_vgoodlrg);
        but_fairlrg = (ImageButton) findViewById(R.id.but_fairlrg);
        submit_feedback = (Button) findViewById(R.id.but_submit_feedback);
        sb_questionid = new StringBuffer();
        sb_answer = new StringBuffer();
        sb_department = new StringBuffer();
        sb_suggestions = new StringBuffer();
        sb_recommend = new StringBuffer();
        sb_poor_department = new StringBuffer();
        sb_overall_satisfcation = new StringBuffer();
        questionsArrayList = new ArrayList<>();
        questionsArrayList = LoginActivity.questn();
        poor_sup= (RelativeLayout) findViewById(R.id.poor_sup);
        activity_questions= (LinearLayout) findViewById(R.id.activity_questions);

         preferences = getSharedPreferences("MyPref_login", MODE_PRIVATE);
        userid = preferences.getString("user_id", null);
        department = preferences.getString("department", null);
        username = preferences.getString("name", null);
        email = preferences.getString("email", null);
        location = preferences.getString("location", null);
        usertype= preferences.getString("usertype", null);
        phone= preferences.getString("phone", null);

        System.out.println("useriddddd............." + department+userid+username+usertype+location+phone+email);
        actv= (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);
        ic_send= (ImageView) findViewById(R.id.send_deprt);
        questnz= (RelativeLayout) findViewById(R.id.questnz);
        progress = (ProgressBar) findViewById(R.id.progress_bar_questions);
        department_name = (HTextView) findViewById(R.id.quest_department);
        lay_questions = (LinearLayout) findViewById(R.id.layout_questn);
        lay_department = (LinearLayout) findViewById(R.id.layout_departmnt);
        radio_recommend = (RadioGroup) findViewById(R.id.radio_recommends);
        radio_yes = (RadioButton) findViewById(R.id.radio_checked_yess);
        radio_no = (RadioButton) findViewById(R.id.radio_checked_noo);
        linearLayout_recommend = (LinearLayout) findViewById(R.id.layout_recommend);
        linearLayout_smily = (LinearLayout) findViewById(R.id.layout_smily);


        lay_suggestions = (LinearLayout) findViewById(R.id.lay_suggestions);

        lay_disatis = (LinearLayout) findViewById(R.id.lay_disatisfied);
       lay_appreciate= (LinearLayout) findViewById(R.id.lay_back_appreciate);
        txt_questn_name = (TextView) findViewById(R.id.quest_name);
       txt_appreciate = (TextView) findViewById(R.id.text_backk_appreciate);
      //  edt_deprmnt_name = (EditText) findViewById(R.id.disat_departmnt_name);
        edt_suggestins = (EditText) findViewById(R.id.edt_suggestins);
        txt_questn_number = (TextView) findViewById(R.id.quest_number);
        but_excelnt = (ImageButton) findViewById(R.id.but_excelnt);
        but_excelntlrg = (ImageButton) findViewById(R.id.but_excelntLRG);
        but_goodlrg = (ImageButton) findViewById(R.id.but_goodlrg);
        but_poorlrg = (ImageButton) findViewById(R.id.but_poorlarge);
      //  but_nalrg = (ImageButton) findViewById(R.id.but_nalarge);
        lay_appreciate.setVisibility(View.GONE);
        final LayoutInflater inflator1 = (LayoutInflater) QuestionsNwnw.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        custompopuprecomend_view = inflator1.inflate(R.layout.recomend_layout, null);
        coordinatorLayout=(LinearLayout) custompopuprecomend_view. findViewById(R.id.coordinatorLayout);
        radio_recommends = (RadioGroup)custompopuprecomend_view. findViewById(R.id.radio_recommends);
        radio_checked_yess = (RadioButton)custompopuprecomend_view. findViewById(R.id.radio_checked_yess);
        radio_checked_noo = (RadioButton)custompopuprecomend_view. findViewById(R.id.radio_checked_noo);
        popup_edt_suggestins = (EditText)custompopuprecomend_view. findViewById(R.id.edt_suggestins);
        popup_submit_feedback = (Button)custompopuprecomend_view. findViewById(R.id.popup_submit_feedback);
        progress_bar_questions= (ProgressBar)custompopuprecomend_view. findViewById(R.id.popup_progress_bar_questions);

        pop_back= (LinearLayout) custompopuprecomend_view. findViewById(R.id.pop_back);
        pop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("<<<<<<<<<<<<ttttttttttt>>>>>>>>>>>>>>>>>>>>>"+flag);
                recomend_popupwindow.dismiss();




            }
        });



        departmnt(location);

        final LayoutInflater inflator3 = (LayoutInflater) QuestionsNwnw.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        custom_disast_view = inflator3.inflate(R.layout.disast_popup_layout, null);
        disast_coordinatorLayout=(LinearLayout) custom_disast_view.findViewById(R.id.disast_coordinatorLayout);
        pop_actv= (AutoCompleteTextView)custom_disast_view.findViewById(R.id.popup_autoCompleteTextView);
        if (!isFinishing()) {
            pop_actv.setThreshold(1);
        }

        back = (ImageView) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         lay_department.setVisibility(View.VISIBLE);

                System.out.println("<<<<<<<<<<<<sb_answer>>>>>>>>>>>>>>>>>>>>>"+sb_answer);

                System.out.println("<<<<<<<<<<<<id>>>>>>>>>>>>>>>>>>>>>"+id);

                System.out.println("<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>"+questionsArrayList.size());
                if(id<=0){
                    Snackbar.with(QuestionsNwnw.this, null)
                            .type(Type.SUCCESS)
                            .message("Not Possible")
                            .duration(Duration.SHORT)

                            .show();
                }
                else {

                    id--;

                    if(id <=questionsArrayList.size()) {


                        if (id == questionsArrayList.size()) {
                         lay_department.setVisibility(View.GONE);

                            txt_questn_name.setText(getResources().getString(R.string.overall_satisfaction));


                        } else {
                            lay_department.setVisibility(View.VISIBLE);

                            dept.remove(id);
                            qst_id.remove(id);
                            ans.remove(id);




                            txt_questn_number.setText("" + id);
                            txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                            department_name.animateText(questionsArrayList.get(id).getDepartment());
                            System.out.println("++++++++++++++++++ questionname : " + questionsArrayList.get(id).getQuestname());
                            System.out.println("++++++++++++++++++ department name : " + questionsArrayList.get(id).getDepartment());
                        }

                    }
                }
            }
        });











        department_name.setAnimateType(HTextViewType.TYPER);
        department_name.setSingleLine(false);
        //  department_name = (TextView) findViewById(R.id.quest_department);
        Typeface myFont5 = Typeface.createFromAsset(getApplicationContext().getAssets(), "Roboto-Regular.ttf");
        department_name.setTypeface(myFont5);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom);

        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                progress.setVisibility(View.INVISIBLE);
                return false;
            }
        });




        if (questionsArrayList != null) {
            for (int i = 0; i < questionsArrayList.size(); i++) {

                String qid = questionsArrayList.get(i).getQuestid();

                question = questionsArrayList.get(i).getQuestname();
                System.out.println("qusetionzzz"+ question);

                agentid = questionsArrayList.get(i).getAgentid();
                 pinno= questionsArrayList.get(i).getPinno();
                String department = questionsArrayList.get(i).getDepartment();
                user_id = questionsArrayList.get(i).getUser_id();
                name = questionsArrayList.get(i).getPerson_name();
                System.out.println("user...id" + user_id);

            }
            if (id <=questionsArrayList.size()) {

                if (id == 0) {
                    id = 0;

                    txt_questn_number.setText("" + id + 1);
                    txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                    department_name.animateText(questionsArrayList.get(id).getDepartment());

                    System.out.println("++++++++++++++++++ questionname : "+questionsArrayList.get(id).getQuestname());
                    System.out.println("++++++++++++++++++ department name : "+questionsArrayList.get(id).getDepartment());


                }


            } else {

                submit_feedback.setVisibility(View.VISIBLE);

            }
        }


        popup_submit_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_checked_yess.isChecked()) {
                    str_radio_categry= "Yes ";
                    sb_recommend.append("Yes");

                } else if (radio_checked_noo.isChecked()) {
                   str_radio_categry = "No";
                    sb_recommend.append("No");

                }
                if (agentid != null) {
                    but_excelnt.setEnabled(false);
                    but_good.setEnabled(false);
                    but_poor.setEnabled(false);
                    but_excelntlrg.setEnabled(false);
                    but_poorlrg.setEnabled(false);
                    but_goodlrg.setEnabled(false);

                                    NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
                                    final boolean i = networkCheckingClass.ckeckinternet();
                                    System.out.println("login....i" + i);
                                    if (i) {

                                       fun();
                                       // popup_submit_feedback.setVisibility(View.INVISIBLE);


                                    }
                                    else {
                                        Snackbar.with(QuestionsNwnw.this, null)
                                                .type(Type.SUCCESS)
                                                .message(getResources().getString(R.string.no_internet))
                                                .duration(Duration.SHORT)
                                                .show();


                                    }
                                }

                else {
               new MaterialStyledDialog.Builder(QuestionsNwnw.this)
                            .setDescription(getResources().getString(R.string.please_login_to))
                            .setIcon(R.drawable.logo)
                            .setStyle(Style.HEADER_WITH_TITLE)
                           // .withDialogAnimation(true)
                            .setHeaderColor(R.color.colorPrimary)
                            .setCancelable(true)
                            .setPositive(getResources().getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                    langusge_pref.edit().putString("language","en").commit();
                                    LanguageCheck lc=new LanguageCheck(getApplicationContext());

                                    Intent s = new Intent(getApplicationContext(), Patient_typeActivity.class);
                                    startActivity(s);
                                    finish();
                                }
                            })
                         .show();


                }

            }
        });

        but_excelnt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnexcellentclicked=true;
                    btngoodclicked=false;
                    btnpoorclicked=false;
                  //  btnnaclicked=false;
                    btnfairclicked=false;
                    btnvgoodclicked=false;
                    if(id <questionsArrayList.size())
                {

                    System.out.println("countexcellent." + id + ".............."+questionsArrayList.get(id).getQuestname()+"...." +"......"+ sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                    but_excelnt.setVisibility(View.INVISIBLE);
                    but_excelntlrg.setVisibility(View.VISIBLE);

                    //new Changes 5/14/2018

                    dept.add(questionsArrayList.get(id).getDepartment());
                    qst_id.add(questionsArrayList.get(id).getQuestid());
                    ans.add("Excellent");



                    cnt_exclnt++;

                    System.out.println("countexcellent." + id + ".............."+questionsArrayList.get(id).getQuestname()+"...." +"......"+ sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                    new Handler().postDelayed(new Runnable() {
                        //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            but_excelnt.setVisibility(View.VISIBLE);
                            but_excelntlrg.setVisibility(View.INVISIBLE);

                            // close this activity

                        }
                    }, 500);
//
                    id++;
                    if(id <=questionsArrayList.size())
                    {


                        if(id==questionsArrayList.size())
                        {   lay_department.setVisibility(View.GONE);

                                txt_questn_name.setText(getResources().getString(R.string.overall_satisfaction));



                        }
                        else {
                            txt_questn_number.setText("" + id);
                            txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                            department_name.animateText(questionsArrayList.get(id).getDepartment());
                            System.out.println("++++++++++++++++++ questionname : "+questionsArrayList.get(id).getQuestname());
                            System.out.println("++++++++++++++++++ department name : "+questionsArrayList.get(id).getDepartment());
                        }


                    }





                    System.out.println("countexcellent." + id + "..............");

                }else if (id==questionsArrayList.size()) {

                        but_excelnt.setVisibility(View.INVISIBLE);
                        but_excelntlrg.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity
                                but_excelnt.setVisibility(View.VISIBLE);
                                but_excelntlrg.setVisibility(View.INVISIBLE);

                                if (btnpoorclicked == false && btngoodclicked == false && btnexcellentclicked == true&&btnfairclicked==false) {

                                    sb_overall_satisfcation = new StringBuffer();
                                    sb_poor_department = new StringBuffer();

                                    sb_overall_satisfcation.append("Excellent");
                                    sb_poor_department.append("N/A");
                                    //flag=false;

                                    display_recomend();

                                    txt_questn_name.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
                               }

                                System.out.println("button..excellent....would youuuu recmdddd");

                                // close this activity

                            }
                        }, 500);




                    }
                   // increaseSize();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {

                    but_excelnt.setAlpha((float)1.0);


                    new Handler().postDelayed(new Runnable() {
                        //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            but_excelnt.setVisibility(View.VISIBLE);
                            but_excelntlrg.setVisibility(View.INVISIBLE);

                            // close this activity

                        }
                    }, 500);


                    //  resetSize();
                }
                return true;
            }
        });

        //new Changes 5/14/2018

        but_vgood.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnexcellentclicked=false;
                    btngoodclicked=false;
                    btnvgoodclicked=true;
                    btnpoorclicked=false;
                  //  btnnaclicked=false;
                    btnfairclicked=false;

                    try {

                        if (id < questionsArrayList.size()) {

                            but_vgood.setVisibility(View.INVISIBLE);
                            but_vgoodlrg.setVisibility(View.VISIBLE);



                            dept.add(questionsArrayList.get(id).getDepartment());
                            qst_id.add(questionsArrayList.get(id).getQuestid());
                            ans.add("Very Good");



                            cnt_vgood++;

                            System.out.println("countgood." + id + ".............." + questionsArrayList.get(id).getQuestname() + "...." + "......" + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                            new Handler().postDelayed(new Runnable() {
                                //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    // Start your app main activity
                                    but_vgood.setVisibility(View.VISIBLE);
                                    but_vgoodlrg.setVisibility(View.INVISIBLE);

                                    // close this activity

                                }
                            }, 500);
//
                            id++;
                            if (id <= questionsArrayList.size()) {


                                if (id == questionsArrayList.size()) {
                                  //  lay_department.setVisibility(View.GONE);
                                    lay_department.setVisibility(View.GONE);
                                    txt_questn_name.setText(getResources().getString(R.string.overall_satisfaction));


                                } else {
                                    txt_questn_number.setText("" + id);
                                    txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                                    department_name.animateText(questionsArrayList.get(id).getDepartment());
                                    System.out.println("++++++++++++++++++ questionname : "+questionsArrayList.get(id).getQuestname());
                                    System.out.println("++++++++++++++++++ department name : "+questionsArrayList.get(id).getDepartment());
                                }


                            }





                            System.out.println("countgood." + id + "..............");

                        } else if (id == questionsArrayList.size()) {



                            but_vgood.setVisibility(View.INVISIBLE);
                            but_vgoodlrg.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    // Start your app main activity
                                    but_vgood.setVisibility(View.VISIBLE);
                                    but_vgoodlrg.setVisibility(View.INVISIBLE);

                                    if (btnpoorclicked == false && btnvgoodclicked == true && btnexcellentclicked == false&&btnfairclicked==false) {

                                        sb_overall_satisfcation = new StringBuffer();
                                        sb_poor_department = new StringBuffer();

                                        sb_overall_satisfcation.append("Very Good");
                                        sb_poor_department.append("N/A");
                                       display_recomend();

                                        txt_questn_name.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));

                                        System.out.println("button..gooodddd....would youuuu recmdddd");
                                    }


                                    // close this activity

                                }
                            }, 500);




                            System.out.println("countgoodq." + id + "..............");


                        }

                    }
                    catch (Exception e)
                    {

                    }

                }else if (event.getAction() == MotionEvent.ACTION_UP) {

                    but_vgood.setAlpha((float)1.0);


                    new Handler().postDelayed(new Runnable() {
                        //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            but_vgood.setVisibility(View.VISIBLE);
                            but_vgoodlrg.setVisibility(View.INVISIBLE);

                            // close this activity

                        }
                    }, 500);


                    //  resetSize();
                }

                return true;
            }
        });
        but_good.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnexcellentclicked=false;
                    btngoodclicked=true;
                    btnpoorclicked=false;
                  //  btnnaclicked=false;
                    btnvgoodclicked=false;
                    btnfairclicked=false;

                    try {

                        if (id < questionsArrayList.size()) {

                            but_good.setVisibility(View.INVISIBLE);
                            but_goodlrg.setVisibility(View.VISIBLE);
                            //new Changes 5/14/2018

                            dept.add(questionsArrayList.get(id).getDepartment());
                            qst_id.add(questionsArrayList.get(id).getQuestid());
                            ans.add("Good");




                            cnt_good++;

                            System.out.println("countgood." + id + ".............." + questionsArrayList.get(id).getQuestname() + "...." + "......" + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                            new Handler().postDelayed(new Runnable() {
                                //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    // Start your app main activity
                                    but_good.setVisibility(View.VISIBLE);
                                    but_goodlrg.setVisibility(View.INVISIBLE);

                                    // close this activity

                                }
                            }, 500);
//
                            id++;
                            if (id <= questionsArrayList.size()) {


                                if (id == questionsArrayList.size()) {
                                  //  lay_department.setVisibility(View.GONE);
                                    lay_department.setVisibility(View.GONE);

                                        txt_questn_name.setText(getResources().getString(R.string.overall_satisfaction));


                                } else {
                                    txt_questn_number.setText("" + id);
                                    txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                                    department_name.animateText(questionsArrayList.get(id).getDepartment());
                                    System.out.println("++++++++++++++++++ questionname : "+questionsArrayList.get(id).getQuestname());
                                    System.out.println("++++++++++++++++++ department name : "+questionsArrayList.get(id).getDepartment());
                                }


                            }


//                        else {
//                            txt_questn_name.setText("Overall Satisfation of the Hospital?");
//                        }


                            System.out.println("countgood." + id + "..............");

                        } else if (id == questionsArrayList.size()) {


                                but_good.setVisibility(View.INVISIBLE);
                                but_goodlrg.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                                    @Override
                                    public void run() {
                                        // This method will be executed once the timer is over
                                        // Start your app main activity
                                        but_good.setVisibility(View.VISIBLE);
                                        but_goodlrg.setVisibility(View.INVISIBLE);

                                        if (btnpoorclicked == false && btngoodclicked == true && btnexcellentclicked == false) {

                                            sb_overall_satisfcation = new StringBuffer();
                                            sb_poor_department = new StringBuffer();

                                            sb_overall_satisfcation.append("Good");
                                            sb_poor_department.append("N/A");

                                            display_recomend();


                                            txt_questn_name.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));

                                            System.out.println("button..gooodddd....would youuuu recmdddd");
                                        }


                                        // close this activity

                                    }
                                }, 500);




                            System.out.println("countgoodq." + id + "..............");


                        }

                    }
                    catch (Exception e)
                    {

                    }

                }else if (event.getAction() == MotionEvent.ACTION_UP) {

                    but_good.setAlpha((float)1.0);


                    new Handler().postDelayed(new Runnable() {
                        //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            but_good.setVisibility(View.VISIBLE);
                            but_goodlrg.setVisibility(View.INVISIBLE);

                            // close this activity

                        }
                    }, 500);


                    //  resetSize();
                }

                return true;
            }
        });

        //new Changes 5/14/2018

        but_fair.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnexcellentclicked=false;
                    btngoodclicked=false;
                    btnpoorclicked=false;
                    btnvgoodclicked=false;
                    //btnnaclicked=false;
                    btnfairclicked=true;
                    try {

                        if (id < questionsArrayList.size()) {

                            but_fair.setVisibility(View.INVISIBLE);
                            but_fairlrg.setVisibility(View.VISIBLE);
                            dept.add(questionsArrayList.get(id).getDepartment());
                            qst_id.add(questionsArrayList.get(id).getQuestid());
                            ans.add("Fair");



                            cnt_fair++;

                            System.out.println("countgood." + id + ".............." + questionsArrayList.get(id).getQuestname() + "...." + "......" + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                            new Handler().postDelayed(new Runnable() {
                                //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    // Start your app main activity
                                    but_fair.setVisibility(View.VISIBLE);
                                    but_fairlrg.setVisibility(View.INVISIBLE);

                                    // close this activity

                                }
                            }, 500);
//
                            id++;
                            if (id <= questionsArrayList.size()) {


                                if (id == questionsArrayList.size()) {
                                  //  lay_department.setVisibility(View.GONE);
                                    lay_department.setVisibility(View.GONE);
                                    txt_questn_name.setText(getResources().getString(R.string.overall_satisfaction));


                                } else {
                                    txt_questn_number.setText("" + id);
                                    txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                                    department_name.animateText(questionsArrayList.get(id).getDepartment());
                                    System.out.println("++++++++++++++++++ questionname : "+questionsArrayList.get(id).getQuestname());
                                    System.out.println("++++++++++++++++++ department name : "+questionsArrayList.get(id).getDepartment());
                                }


                            }




                            System.out.println("countgood." + id + "..............");

                        } else if (id == questionsArrayList.size()) {



                            but_fair.setVisibility(View.INVISIBLE);
                            but_fairlrg.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    // Start your app main activity
                                    but_fair.setVisibility(View.VISIBLE);
                                    but_fairlrg.setVisibility(View.INVISIBLE);

                                    if (btnpoorclicked == false && btnfairclicked == true && btnexcellentclicked == false && btngoodclicked==false && btnvgoodclicked==false) {

                                        sb_overall_satisfcation = new StringBuffer();
                                        sb_poor_department = new StringBuffer();

                                        sb_overall_satisfcation.append("Fair");
                                        sb_poor_department.append("N/A");

display_recomend();


                                        txt_questn_name.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
                                   //     linearLayout_smily.setVisibility(View.VISIBLE);
                                    //    linearLayout_recommend.setVisibility(View.VISIBLE);
                                        System.out.println("button..gooodddd....would youuuu recmdddd");
                                    }


                                    // close this activity

                                }
                            }, 500);




                            System.out.println("countgoodq." + id + "..............");


                        }

                    }
                    catch (Exception e)
                    {

                    }

                }else if (event.getAction() == MotionEvent.ACTION_UP) {

                    but_fair.setAlpha((float)1.0);


                    new Handler().postDelayed(new Runnable() {
                        //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            but_fair.setVisibility(View.VISIBLE);
                            but_fairlrg.setVisibility(View.INVISIBLE);

                            // close this activity

                        }
                    }, 500);


                    //  resetSize();
                }

                return true;
            }
        });

        but_poor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                  //  btnnaclicked=false;
                    btnexcellentclicked=false;
                    btnfairclicked=false;
                    btnvgoodclicked=false;
                    btngoodclicked=false;
                    btnpoorclicked=true;
                    but_poor.setAlpha((float) 0.5);
                if(btnpoorclicked==true&&btngoodclicked==false&&btnexcellentclicked==false &&btnfairclicked==false&& btnvgoodclicked==false)
                {

                    if(txt_questn_name.getText().toString().contentEquals(getResources().getString(R.string.overall_satisfaction))) {

                        dialog_popup();
                        //  System.out.println("item.........."+arg0+"....."+arg1+"....."+arg2+"...."+arg3+"...."+actv.getText().toString());
                    }





                    }



                    System.out.println("feedback....questionsArrayList.size()"+questionsArrayList.size());



                    if(id <questionsArrayList.size())
                    {

                        but_poor.setVisibility(View.INVISIBLE);
                        but_poorlrg.setVisibility(View.VISIBLE);

                        //new Changes 5/14/2018

                        dept.add(questionsArrayList.get(id).getDepartment());
                        qst_id.add(questionsArrayList.get(id).getQuestid());
                        ans.add("Poor");


                        cnt_poor++;

                        System.out.println("countpoor." + id + ".............."+questionsArrayList.get(id).getQuestname()+"...." +"......"+ sb_questionid.toString() + sb_answer.toString() + sb_department.toString());


//
                        id++;
                        if(id <=questionsArrayList.size())
                        {


                            if(id==questionsArrayList.size())

                            {
                              lay_department.setVisibility(View.GONE);
                                    txt_questn_name.setText(getResources().getString(R.string.overall_satisfaction));


                            }
                            else {
                                txt_questn_number.setText("" + id);
                                txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                                department_name.animateText(questionsArrayList.get(id).getDepartment());
                                System.out.println("++++++++++++++++++ questionname : "+questionsArrayList.get(id).getQuestname());
                                System.out.println("++++++++++++++++++ department name : "+questionsArrayList.get(id).getDepartment());
                            }


                        }






                        System.out.println("countpoor." + id + "..............");

                    }



                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    but_poor.setAlpha((float)1.0);


                    new Handler().postDelayed(new Runnable() {
                        //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            but_poor.setVisibility(View.VISIBLE);
                            but_poorlrg.setVisibility(View.INVISIBLE);

                            // close this activity

                        }
                    }, 500);
                }
                return true;
            }
        });



    }

    private void dialog_popup() {
        btnexcellentclicked=true;
        btnfairclicked=true;
        btnvgoodclicked=true;
        btngoodclicked=true;
activity_questions.setVisibility(View.GONE);
poor_sup.setVisibility(View.VISIBLE);

            pop_actv= (AutoCompleteTextView)findViewById(R.id.popup_autoCompleteTextView);
            radio_recommends_d = (RadioGroup)findViewById(R.id.radio_recommends_d);
            radio_checked_yess_d = (RadioButton)findViewById(R.id.radio_checked_yess_d);
            radio_checked_noo_d = (RadioButton) findViewById(R.id.radio_checked_noo_d);
            popup_edt_suggestins_d = (EditText)findViewById(R.id.popup_edt_suggestins_d);
            popup_submit_feedback_d = (Button)findViewById(R.id.popup_submit_feedback_d);
            popup_progress_bar_questions_d= (ProgressBar)findViewById(R.id.popup_progress_bar_questions_d);

            popup_submit_feedback_d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (agentid != null) {
                        but_excelnt.setEnabled(false);
                        but_good.setEnabled(false);
                        but_poor.setEnabled(false);

                        but_excelntlrg.setEnabled(false);
                        but_poorlrg.setEnabled(false);
                        but_goodlrg.setEnabled(false);
                        if (radio_checked_yess_d.isChecked()) {
                         str_radio_categry= "yes";
                            sb_recommend.append("Yes");

                        } else if (radio_checked_noo_d.isChecked()) {
                           str_radio_categry = "No";
                            sb_recommend.append("No");

                        }
                        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
                        final boolean i = networkCheckingClass.ckeckinternet();
                        System.out.println("login....i" + i);
                        if (i) {
                            sb_overall_satisfcation=new StringBuffer();
                            sb_overall_satisfcation.append("Poor");
                            sb_poor_department.append(pop_actv.getText().toString());
                            if(starttext.equalsIgnoreCase("")&&newtext.equalsIgnoreCase("")&&pop_actv.getText().toString().equalsIgnoreCase("")) {

                                Snackbar.with(QuestionsNwnw.this,null)
                                        .type(Type.ERROR)
                                        .message(getResources().getString(R.string.please_mention_your)	)
                                        .duration(Duration.SHORT)

                                        .show();

                            }
                            else if(isChecked){
                                Snackbar.with(QuestionsNwnw.this, null)
                                        .type(Type.SUCCESS)
                                        .message(starttext+" "+getResources().getString(R.string.would_you_recommend))
                                        .duration(Duration.LONG)

                                        .show();
                            }
                            else {

                                fun1();

                            }



                        }
                        else {
                            Snackbar.with(QuestionsNwnw.this, null)
                                    .type(Type.SUCCESS)
                                    .message(getResources().getString(R.string.no_internet))
                                    .duration(Duration.SHORT)
                                    .show();


                        }
                    }

                    else {
                        new MaterialStyledDialog.Builder(QuestionsNwnw.this)
                                .setDescription(getResources().getString(R.string.please_login_to))
                                .setIcon(R.drawable.logo)
                                .setStyle(Style.HEADER_WITH_TITLE)
                                .setHeaderColor(R.color.colorPrimary)
                                .setCancelable(true)
                                .setPositive(getResources().getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(MaterialDialog dialog, DialogAction which) {
                                        langusge_pref.edit().putString("language","en").commit();
                                        LanguageCheck lc=new LanguageCheck(getApplicationContext());

                                        Intent s = new Intent(getApplicationContext(), Patient_typeActivity.class);
                                        startActivity(s);
                                    }
                                })
                                .show();


                    }

                }
            });

            disast_back= (ImageView)findViewById(R.id.disast_back);
            disast_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity_questions.setVisibility(View.VISIBLE);
                    poor_sup.setVisibility(View.GONE);
                }
            });
            send_deprt= (ImageView)findViewById(R.id.send_deprt);
            send_deprt.setVisibility(View.GONE);
            if (!isFinishing()) {
                pop_actv.setThreshold(1);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.new_auto_inflate,stockArr);
            pop_actv.setAdapter(adapter);
            pop_actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    starttext = String.valueOf(arg0.getItemAtPosition(arg2));
                    System.out.println("item.........." + arg0 + "....." + arg1 + "....." + arg2 + "...." + arg3 + "...." + starttext);

                    if (starttext != null) {
                        pop_actv.setTag("yes");
                        System.out.println("item.........." + pop_actv.getTag());
                        System.out.println("item.........." + starttext);


                        sb_overall_satisfcation = new StringBuffer();

                        txt_questn_number.setText("" + id);

                        if(selected_language.equals("en")){
                            Snackbar.with(QuestionsNwnw.this, null)
                                    .type(Type.SUCCESS)
                                    .message(getResources().getString(R.string.would_you_recommend) +" " + starttext)
                                    .duration(Duration.LONG)

                                    .show();
                        }else{
                            Snackbar.with(QuestionsNwnw.this, null)
                                    .type(Type.SUCCESS)
                                    .message(starttext+" "+getResources().getString(R.string.would_you_recommend))
                                    .duration(Duration.LONG)

                                    .show();
                        }





                        System.out.println("sb_overallll..satisfaction...." + sb_overall_satisfcation.toString() + " sb_poor_department....." + sb_poor_department.toString());

                    } else if (pop_actv.getText().toString() != null) {
                        newtext = pop_actv.getText().toString();
                        sb_overall_satisfcation = new StringBuffer();
                        sb_poor_department = new StringBuffer();

                        txt_questn_number.setText("" + id);
                        if(selected_language.equals("en")){
                            Snackbar.with(QuestionsNwnw.this, null)
                                    .type(Type.SUCCESS)
                                    .message(getResources().getString(R.string.would_you_recommend) +" " + newtext)
                                    .duration(Duration.LONG)

                                    .show();
                        }else{
                            Snackbar.with(QuestionsNwnw.this, null)
                                    .type(Type.SUCCESS)
                                    .message(newtext+" "+getResources().getString(R.string.would_you_recommend))
                                    .duration(Duration.LONG)

                                    .show();
                        }




                        System.out.println("sb_overallll..satisfactions...." + sb_overall_satisfcation.toString() + " sb_poor_department....." + sb_poor_department.toString());

                    } else {

                    }


                }
            });

            send_deprt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.dismiss();
                     System.out.println("appended valueee onclick..........."+pop_actv.getText().toString());
                    System.out.println("appended starttext..........."+ starttext);
                    sb_overall_satisfcation=new StringBuffer();
                    sb_overall_satisfcation.append("Poor");
                    sb_poor_department.append(pop_actv.getText().toString());
                    if(starttext.equalsIgnoreCase("")&&newtext.equalsIgnoreCase("")&&pop_actv.getText().toString().equalsIgnoreCase("")) {

                        Snackbar.with(QuestionsNwnw.this,null)
                                .type(Type.ERROR)
                                .message(getResources().getString(R.string.please_mention_your)	)
                                .duration(Duration.SHORT)

                                .show();

                    }
                    else {



                    }
                }
            });



    }




    private void display_recomend() {
        try {

            recomend_popupwindow = new PopupWindow(custompopuprecomend_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (Build.VERSION.SDK_INT >= 21) {
                recomend_popupwindow.setElevation(5.0f);
            }
            recomend_popupwindow.setFocusable(true);
            recomend_popupwindow.update();

            recomend_popupwindow.setAnimationStyle(R.style.AppTheme);
            recomend_popupwindow.showAtLocation(coordinatorLayout, Gravity.CENTER, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void   fun1()
    {
        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
        boolean i = networkCheckingClass.ckeckinternet();
        popup_progress_bar_questions_d.setVisibility(View.VISIBLE);
        jsonModelArrayList = new ArrayList<JsonModel>();
        Gson gson = new Gson();
        //new Changes 5/14/2018
        System.out.println("---libin.................." + edt_suggestins.getText().toString());

        JsonModel jsonModel = new JsonModel();
        jsonModel.setDepartment(dept.toString());
        jsonModel.setAnswers(ans.toString());
        jsonModel.setQuestions(qst_id.toString());
       // edt_suggestins
        suggestions = popup_edt_suggestins_d.getText().toString();
        sb_suggestions.append(suggestions);

        jsonModelArrayList.add(jsonModel);
        final String string_apnd = gson.toJson(jsonModelArrayList);
        System.out.println("--------------- string_apnd" + string_apnd);
        String new_list=string_apnd.replaceAll("\\[|\\]", "");
        System.out.println("--------------- string_apnd" + new_list);
        nw_array.add(new_list);
        if (i == true) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL +"answer.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("--------------- responseeeee" + response);

                           try {
                                JSONObject jsonObj = new JSONObject(response);
                                status=jsonObj.getString("status");
                                System.out.println("---------------status" +status);

                                JSONObject dataObj=jsonObj.getJSONObject("data");
                                inpatient_count=dataObj.getString("inpatient_count");
                                outpatient_count=dataObj.getString("outpatient_count");
                                System.out.println("--------------- inpatient_count isss : "+inpatient_count);
                                System.out.println("--------------- outpatient_count isss : "+outpatient_count);

                            }catch (Exception e){
                                e.printStackTrace();
                            }


                            if (status.contentEquals("success")) {

                                System.out.println("--------------- Libinnnnnnnnnnnnnn" + "hiiiiiiiiiiiiiiiii");

                                   popup_progress_bar_questions_d.setVisibility(View.GONE);




                                displayPopup();





                            }
                            else
                            {
                                System.out.println("--------------- hiiiiiiiiiiiiiiiiiiiiiiiiiiiii2222222222222222" + "hiiiiiiiiiiiiiiiii");

                                  popup_progress_bar_questions_d.setVisibility(View.GONE);

                                displayPopup();




                            }




                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {


                    System.out.println("buffered string value" + string_apnd+".........."+sb_suggestions.toString()+"....."+sb_recommend.toString()+"...."+sb_overall_satisfcation.toString()+"...."+sb_poor_department.toString());
                    System.out.println("countexc" + cnt_exclnt + "good" + cnt_good + "poor" + cnt_poor + "verypoor" + cnt_verypoor);

                    System.out.println("DDDDDDDDDDDDD" + name + "email" +email+ "poor" + phone + "verypoor" + location+usertype);
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("name",name);
                    params.put("email",email);
                    params.put("phoneno",phone);
                    params.put("pinno",pinno);
                    params.put("patienttype",preferences.getString("patienttype",null));

                    if(preferences.getString("doctor",null)!=null){
                        params.put("doctor",preferences.getString("doctor",null));

                    }else {
                        params.put("doctor","-");

                    }
                    if(preferences.getString("specialty",null)!=null){
                        params.put("specialty",preferences.getString("specialty",null));

                    }else {
                        params.put("specialty","-");

                    }
                    params.put("location",location);
                    params.put("usertype",usertype);
                    params.put("someJSON", String.valueOf(nw_array));
                    params.put("agentid",agentid);
                    params.put("suggestions",suggestions);
                    params.put("recommend",str_radio_categry);
                    params.put("overall_satisfaction",sb_overall_satisfcation.toString());
                    params.put("poor_department_name",sb_poor_department.toString());
                    params.put("roomno",langusge_pref.getString("roomno",null));
                    params.put("language",langusge_pref.getString("language",null).toUpperCase());
                    System.out.println("-----------------params : "+params);
                    return params;
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(QuestionsNwnw.this);
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
            requestQueue.getCache().clear();
        } else {
            Snackbar.with(QuestionsNwnw.this,null)
                    .type(Type.SUCCESS)
                    .message(getResources().getString(R.string.no_internet)		)
                    .duration(Duration.LONG)

                    .show();

        }


    }

    public  void   fun()
    {
        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
        boolean i = networkCheckingClass.ckeckinternet();
        progress_bar_questions.setVisibility(ProgressBar.VISIBLE);

        jsonModelArrayList = new ArrayList<JsonModel>();
        Gson gson = new Gson();
        //new Changes 5/14/2018
        System.out.println("---ansal.................." + edt_suggestins.getText().toString());

        JsonModel jsonModel = new JsonModel();
        jsonModel.setDepartment(dept.toString());
        jsonModel.setAnswers(ans.toString());
        jsonModel.setQuestions(qst_id.toString());

        suggestions = popup_edt_suggestins.getText().toString();
        sb_suggestions.append(suggestions);

        jsonModelArrayList.add(jsonModel);
        final String string_apnd = gson.toJson(jsonModelArrayList);
        String new_list=string_apnd.replaceAll("\\[|\\]", "");
        System.out.println("--------------- string_apnd" + new_list);
        nw_array.add(new_list);

        if (i == true) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL +"answer.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("--------------- responseeeee" + response);

                             try {
                                JSONObject jsonObj = new JSONObject(response);
                                status=jsonObj.getString("status");
                                System.out.println("---------------status" +status);

                                JSONObject dataObj=jsonObj.getJSONObject("data");
                                inpatient_count=dataObj.getString("inpatient_count");
                                outpatient_count=dataObj.getString("outpatient_count");
                                System.out.println("--------------- inpatient_count isss : "+inpatient_count);
                                System.out.println("--------------- outpatient_count isss : "+outpatient_count);

                            }catch (Exception e){
                                e.printStackTrace();
                            }


                            if (status.contentEquals("success")) {

                                System.out.println("--------------- hiiiiiiiiiiiiiiiiiiiiiiiiiiiii" + "hiiiiiiiiiiiiiiiii");

                                progress_bar_questions.setVisibility(ProgressBar.GONE);




                                displayPopup();





                            }
                            else
                                {
                                    System.out.println("--------------- hiiiiiiiiiiiiiiiiiiiiiiiiiiiii2222222222222222" + "hiiiiiiiiiiiiiiiii");

                                    progress_bar_questions.setVisibility(ProgressBar.GONE);

                                        popup_txt_inpatient_count.setText("IP : "+inpatient_count);
                                        popup_txt_outpatient_count.setText("OP : "+outpatient_count);
                                        popup_txt_thanks.setText(getResources().getString(R.string.thank_you)+" "+name+" "+getResources().getString(R.string.we_appreaciate));

                                    displayPopup();
                                    popup_txt_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            mPopupWindow.dismiss();
                                            System.out.println("goo..to loginnn");
                                            System.out.println("goo..to loginnn");
                                            questionsArrayList.clear();


                                            langusge_pref.edit().putString("language","en").commit();
                                            LanguageCheck lc=new LanguageCheck(getApplicationContext());

                                            SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences1.edit();
                                            editor.putString("user_id", null);
                                            editor.clear();
                                            editor.apply();
                                            editor.commit();
                                            Intent is = new Intent(getApplicationContext(), LoginPage.class);
                                            startActivity(is);
                                            finish();
                                        }
                                    });



                                }




                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {


                    System.out.println("buffered string value" + string_apnd+".........."+sb_suggestions.toString()+"....."+sb_recommend.toString()+"...."+sb_overall_satisfcation.toString()+"...."+sb_poor_department.toString());
                    System.out.println("countexc" + cnt_exclnt + "good" + cnt_good + "poor" + cnt_poor + "verypoor" + cnt_verypoor);

                    System.out.println("DDDDDDDDDDDDD" + name + "email" +email+ "poor" + phone + "verypoor" + location+usertype);
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("name",name);
                    params.put("email",email);
                    params.put("phoneno",phone);
                    params.put("pinno",pinno);
                    params.put("patienttype",preferences.getString("patienttype",null));

                    if(preferences.getString("doctor",null)!=null){
                        params.put("doctor",preferences.getString("doctor",null));

                    }else {
                        params.put("doctor","-");

                    }
                    if(preferences.getString("specialty",null)!=null){
                        params.put("specialty",preferences.getString("specialty",null));

                    }else {
                        params.put("specialty","-");

                    }
                    params.put("location",location);
                    params.put("usertype",usertype);
                    params.put("someJSON", String.valueOf(nw_array));
                    params.put("agentid",agentid);
                    params.put("suggestions",suggestions);
                    params.put("recommend",sb_recommend.toString());
                    params.put("overall_satisfaction",sb_overall_satisfcation.toString());
                    params.put("poor_department_name",sb_poor_department.toString());
                    params.put("roomno",langusge_pref.getString("roomno",null));
                    params.put("language",langusge_pref.getString("language",null).toUpperCase());
                    System.out.println("-----------------params : "+params);
                    return params;
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(QuestionsNwnw.this);
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
            requestQueue.getCache().clear();
        } else {
            Snackbar.with(QuestionsNwnw.this,null)
                    .type(Type.SUCCESS)
                    .message(getResources().getString(R.string.no_internet)		)
                    .duration(Duration.LONG)

                    .show();

        }


    }

    public  void departmnt(final String place)
    {
        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
        boolean i = networkCheckingClass.ckeckinternet();

        if (i) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL +"department.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {

                            System.out.println("+++++++++++++++ responseeeee_________department" + response);

                            if(response != null && !response.isEmpty() && !response.equals("null"))

                            {  try {

                                JSONArray jsonarray = new JSONArray(response);
                                System.out.println("00");
                                ArrayList<String> department_name=new ArrayList<>();
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String department_id=jsonobject.getString("deptId");
                                    String divId=jsonobject.getString("divId");
                                    String deptName=jsonobject.getString("deptName");
                                    System.out.println("+++++++++++++++ depname in service : " +deptName);
                                    department_name.add(deptName);



                                }
                                stockArr  = new String[department_name.size()];
                                stockArr= department_name.toArray(stockArr);
                              //  spinner.setItems(stockArr);
                                System.out.println("departmentname.stockArr............."+stockArr);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                    }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();



                    params.put("branch_id",location);

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
            Snackbar.with(QuestionsNwnw.this,null)
                    .type(Type.ERROR)
                    .message(getResources().getString(R.string.no_internet)		)
                    .duration(Duration.LONG)

                    .show();

        }
    }
    @Override
    public void onBackPressed() {


        new MaterialStyledDialog.Builder(QuestionsNwnw.this)
               .setTitle(getResources().getString(R.string.exit_or_continue))
                .setDescription(getResources().getString(R.string.do_you_want_to_continue))
                .setStyle(Style.HEADER_WITH_TITLE)
                // .withDialogAnimation(true)
                .setHeaderColor(R.color.colorPrimary)
                .setCancelable(true)
                .setPositive(getResources().getString(R.string.yes), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(final MaterialDialog dialog, DialogAction which) {

                   dialog.dismiss();

                    }
                })
                .setNegative(getResources().getString(R.string.no), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                       sb_department.setLength(0);
                        sb_answer.setLength(0);
                        sb_overall_satisfcation.setLength(0);
                        sb_questionid.setLength(0);
                        sb_poor_department.setLength(0);
                        sb_suggestions.setLength(0);

            deleteuser();

                    }
                })
                .show();

    }
    public  void deleteuser()
    {

                                    progress.setVisibility(ProgressBar.INVISIBLE);

        langusge_pref.edit().putString("language","en").commit();
        LanguageCheck lc=new LanguageCheck(getApplicationContext());

                                    SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences1.edit();
                                    //  editor.putString("log_status", "logout");
                                    editor.putString("user_id", null);
                                    editor.clear();
                                    editor.apply();
                                    editor.commit();
                                    Intent is = new Intent(getApplicationContext(), LoginPage.class);
                                    is.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(is);
                                    finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("on destroyyyyyy");

        sb_department.setLength(0);
        sb_answer.setLength(0);
        sb_overall_satisfcation.setLength(0);
        sb_questionid.setLength(0);
        sb_poor_department.setLength(0);
        sb_suggestions.setLength(0);

        deleteuser();
    }



    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        System.out.println("on destroyyyyyy");

        sb_department.setLength(0);
        sb_answer.setLength(0);
        sb_overall_satisfcation.setLength(0);
        sb_questionid.setLength(0);
        sb_poor_department.setLength(0);
        sb_suggestions.setLength(0);

        deleteuser();
    }

    @Override
    protected void onStop() {



        super.onStop();
        System.out.println("on stopppp");


    }
    public void displayPopup() {
        final Dialog dialog = new Dialog(QuestionsNwnw.this);

        //  dialog.setContentView(R.layout.loc_popup);
        final Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setContentView(R.layout.popup_success);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dialog.setCancelable(false);

        popup_txt_thanks=(TextView)dialog.findViewById(R.id.popup_txt_thanks);
        popup_txt_inpatient_count=(TextView)dialog.findViewById(R.id.popup_txt_inpatient_count);
        popup_txt_outpatient_count=(TextView)dialog.findViewById(R.id.popup_txt_outpatient_count);
        popup_txt_ok=(LinearLayout)dialog.findViewById(R.id.popup_txt_ok);
        if(selected_language.equals("en")){
            popup_txt_inpatient_count.setText("IP : "+inpatient_count);
            popup_txt_outpatient_count.setText("OP : "+outpatient_count);
            popup_txt_thanks.setText(getResources().getString(R.string.thank_you)+" "+name+" "+getResources().getString(R.string.we_appreaciate));
        }else{
            popup_txt_inpatient_count.setText("IP : "+inpatient_count);
            popup_txt_outpatient_count.setText("OP : "+outpatient_count);
            popup_txt_thanks.setText(getResources().getString(R.string.we_appreaciate)+" "+name+" "+getResources().getString(R.string.thank_you));
        }

        popup_txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                System.out.println("goo..to loginnn");
                System.out.println("goo..to loginnn");
                questionsArrayList.clear();


                langusge_pref.edit().putString("language","en").commit();
                LanguageCheck lc=new LanguageCheck(getApplicationContext());


                SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                //  editor.putString("log_status", "logout");
                editor.putString("user_id", null);
                editor.clear();
                editor.apply();
                editor.commit();
                Intent is = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(is);
                finish();
            }
        });
        dialog.show();


    }
}
