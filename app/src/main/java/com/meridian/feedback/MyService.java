package com.meridian.feedback;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MyService extends Service {

    String URL_DELETE1 = Constants.BASE_URL+"delete.php?";
String new_user_id;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("START");



        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


            /// requestQueue.getCache().clear();


    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        System.out.println("onTaskRemoved called");
        super.onTaskRemoved(rootIntent);
        SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
        new_user_id = preferences1.getString("user_id", null);
        System.out.println("new_user_id////" + new_user_id);
        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
        boolean i = networkCheckingClass.ckeckinternet();

        if (i) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            //  progress.setVisibility(ProgressBar.GONE);

                            // JSONObject jsonObj = null;
                            //  jsonObj = new JSONObject(response);
                            System.out.println("responseeeee_________delete" + response);
                            // statusd = jsonObj.getString("status");


                            // pd.dismiss();
                            if (response != null) {  //try {


                                if (response.contentEquals("\"success\"")) {
                                    //   progress.setVisibility(ProgressBar.INVISIBLE);
                                    SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences1.edit();
                                    //  editor.putString("log_status", "logout");
                                    editor.putString("user_id", null);
                                    editor.clear();
                                    editor.apply();
                                    editor.commit();


                                }
                                //  JSONArray jsonarray = new JSONArray(response);
                                System.out.println("00");


                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Snackbar.with(QuestionsNw.this,null)
//                                    .type(Type.ERROR)
//                                    .message(error.toString())
//                                    .duration(Duration.LONG)
//
//                                    .show();
////
//                                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

//meridian.net.in/demo/etsdc/response.php?fid=1&email=" + email + "&phone=" + phon + "&name=" + fulnam + "&occupation=" + occ + "&location=" + loc + "&password=" + pass
                    System.out.println("new_user_ids////" + new_user_id);

                    params.put("user_id", new_user_id);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(stringRequest);


            // this.stopSelf();


        }

        //do something you want
        //stop service
    }

}

