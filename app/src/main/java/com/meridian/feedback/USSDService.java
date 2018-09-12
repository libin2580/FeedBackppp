//package com.meridian.feedback;
//
//import android.accessibilityservice.AccessibilityService;
//import android.accessibilityservice.AccessibilityServiceInfo;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.accessibility.AccessibilityEvent;
//import android.widget.ProgressBar;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.RetryPolicy;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.chootdev.csnackbar.Duration;
//import com.chootdev.csnackbar.Snackbar;
//import com.chootdev.csnackbar.Type;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class USSDService extends AccessibilityService {
// String TAG="USSDService";
//
//    String URL_DELETE1 = "http://zulekhahospitals.com/feedback/delete.php";
//
//
//
////    @Override
////    public void onAccessibilityEvent(AccessibilityEvent event) {
////   //In my mobile the class name has been looks like this.
////        if (event.getClassName().equals("com.mediatek.phone.UssdAlertActivity")) {
//// //Method performGlobalAction (GLOBAL_ACTION_BACK) requires Android
////  //  4.1+
////            performGlobalAction(GLOBAL_ACTION_BACK);
////        }
////    }
//
//    @Override
//    public void onInterrupt() {
//
//
//    }
//@Override
//protected void onServiceConnected() {
//    super.onServiceConnected();
//
//    System.out.println("responseeeee_________delete");
//      Log.v(TAG, "onServiceConnected");
//        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//        info.flags = AccessibilityServiceInfo.DEFAULT;
//        info.packageNames = new String[]
//                {"com.android.phone"};
//        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
//        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
//        setServiceInfo(info);
// }
//    @Override
//    public void onAccessibilityEvent(AccessibilityEvent event) {
//        System.out.println("responseeeee_________delete" + event);
//
//        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || event.getClassName() == null)
//            return;
//
//        String className = String.valueOf(event.getClassName());
//
//        if (className.equals("com.android.internal.policy.impl.RecentApplicationsDialog")
//                || className.equals("com.android.systemui.recent.RecentsActivity")
//                || className.equals("com.android.systemui.recents.RecentsActivity")){
//
//
//                NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
//                boolean i = networkCheckingClass.ckeckinternet();
//
//                if (i) {
//
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_DELETE1,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(final String response) {
//                                    //  progress.setVisibility(ProgressBar.GONE);
//
//                                    // JSONObject jsonObj = null;
//                                    //  jsonObj = new JSONObject(response);
//                                    System.out.println("responseeeee_________delete" + response);
//                                    // statusd = jsonObj.getString("status");
//
//
//                                    // pd.dismiss();
//                                    if(response!=null)
//
//                                    {  //try {
//
//
//
//                                        if (response.contentEquals("\"success\""))
//                                        {
//
//
//                                            SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
//                                            SharedPreferences.Editor editor = preferences1.edit();
//                                            //  editor.putString("log_status", "logout");
//                                            editor.putString("user_id", null);
//                                            editor.clear();
//                                            editor.apply();
//                                            editor.commit();
//                                            Intent is = new Intent(getApplicationContext(), LoginActivity.class);
//                                            startActivity(is);
//
//                                            //  String named = jsonObj.getString("name");
//
//                                            //   pd.dismiss();
//
//                                        }
//                                        //  JSONArray jsonarray = new JSONArray(response);
//                                        System.out.println("00");
//
//
//                                        //  ArrayList<String> department_name=new ArrayList<>();
////                                for (int i = 0; i < jsonarray.length(); i++) {
//////                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
//////                                    String department_id=jsonobject.getString("deptId");
//////                                    String divId=jsonobject.getString("divId");
//////                                    String deptName=jsonobject.getString("deptName");
//////                                    department_name.add(deptName);
////
////
////
////                                }
////                                stockArr  = new String[department_name.size()];
////                                stockArr= department_name.toArray(stockArr);
////                                //  spinner.setItems(stockArr);
////                                System.out.println("departmentname.stockArr............."+stockArr);
////
////                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
////                                        (QuestionsNw.this,R.layout.new_auto_inflate,stockArr);
////                                actv.setAdapter(adapter);
//
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
//
//
//
//                                    }
//
//                                }
//                            },
//                            new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
////                            Snackbar.with(QuestionsNw.this,null)
////                                    .type(Type.ERROR)
////                                    .message(error.toString())
////                                    .duration(Duration.LONG)
////
////                                    .show();
//////
////                                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                                }
//                            }) {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> params = new HashMap<String, String>();
//
////meridian.net.in/demo/etsdc/response.php?fid=1&email=" + email + "&phone=" + phon + "&name=" + fulnam + "&occupation=" + occ + "&location=" + loc + "&password=" + pass
//
//
//                            params.put("user_id", String.valueOf(10));
//
//                            return params;
//                        }
//
//                    };
//
//                    RequestQueue requestQueue = Volley.newRequestQueue(this);
//                    int socketTimeout = 30000;//30 seconds - change to what you want
//                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//                    stringRequest.setRetryPolicy(policy);
//                    requestQueue.add(stringRequest);
//                    requestQueue.getCache().clear();
//
//
//
//
//                }
//
//
//
//            //Recent button was pressed. Do something.
//        }
//    }
//
//
//}