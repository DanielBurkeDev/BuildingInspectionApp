package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dpwgroup.inspectplus.model.UsernameKVPairs;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import inspectplus.dpwgroup.com.inspectplus.JSONParser;
import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.utils.SQLiteHandler;
import inspectplus.dpwgroup.com.inspectplus.utils.SessionManager;
import inspectplus.dpwgroup.com.inspectplus.utils.VolleySingleton;
import inspectplus.dpwgroup.com.inspectplus.utils.projListAdapter;


public class SplashActivity extends ActionBarActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    // Progress Dialog
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    private SessionManager session;
    private JSONParser jParser = new JSONParser();
    //  private ArrayList<HashMap<String, String>> usersList;
    // url to get all products list
    private static String url_all_users = "http://f12.solutions/scrpt/dpw/get_all_users.php";
    public static final String URL_IPLUS = "http://10.0.3.2/servicesample/services.php";
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private SQLiteHandler db;

    // products JSONArray
    private JSONArray users = null;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERS = "users";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_FIRSTNAME = "firstName";
    private static final String TAG_PWD = "pwd";
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Fade in and out animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //

        setContentView(R.layout.activity_splash);
//        getActionBar().setLogo(R.drawable.inspect_logo);
//        getActionBar().setDisplayShowTitleEnabled(false);

        // Hashmap for ListView
        //   usersList = new ArrayList<HashMap<String, String>>();

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new ButtonListener());
        etUsername.setText("carlos.pinto@devstream.io");
        etPassword.setText("p@ssw0rd");

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    // Button Listener
    private class ButtonListener implements View.OnClickListener {

        public void onClick(View v) {

                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                switch (v.getId()) {
                    case R.id.btn_login:
                        Toast.makeText(getApplicationContext(), "json Request selected",
                                Toast.LENGTH_SHORT).show();

                        // Check for empty data in the form
                        if (email.trim().length() > 0 && password.trim().length() > 0) {
//                        Toast.makeText(getApplicationContext(),
//                                "email and password" + email+ " " +password, Toast.LENGTH_LONG)
//                                .show();
                            // login user
                            checkLogin(email, password);
                            // new MyAsyncTask().execute(email, password);
                        } else {
                            // Prompt user to enter credentials
                            Toast.makeText(getApplicationContext(),
                                    "Please enter the credentials!", Toast.LENGTH_LONG)
                                    .show();
                        }

//                    Intent intent = new Intent(SplashActivity.this, RegisteredProjectsActivity.class);
//                    startActivity(intent);
                        break;

                default:
                    Toast.makeText(getApplicationContext(), "No options selected",
                            Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void checkLogin(final String memail, final String mpassword) {
        pDialog.setMessage("Logging in ...");
        showDialog();
        Log.d("fields: ",  memail +" " + mpassword);

        //////////////////////////////////// STRING REQUEST
        StringRequest request= new StringRequest(Request.Method.POST, URL_IPLUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                Log.d("Response", response);
//                Toast.makeText(getApplicationContext(), "Response: " + response.toString(),
//                        Toast.LENGTH_SHORT).show();
                //stringToJsonObject(response);

                // convert string to json
                try {

                    JSONObject obj = new JSONObject(response);
                    Log.d("My App", obj.toString());
                    // Check for error in json
                    if (!obj.has("ERROR")){
                        session.setLogin(true);
                        Log.d("My App", obj.toString());

                        String name = obj.getString("username");
                        String token = obj.getString("token");
                        Log.d("check", obj.length() + name + token);

//                        // Inserting row in users table
                        db.addUser(name, token);

                    // Launch main activity
                        Intent intent = new Intent(SplashActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        // Error in login. Get the error message
                        String errorMsg = obj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }


                } catch (Throwable t) {
                    Log.e("login", "Could not parse malformed JSON: \"" + response + "\"");
                }
                //



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
              //  Log.d("Error Response:", error.getMessage());
                Toast.makeText(getApplicationContext(), "Response Error: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "login");
                params.put("username", memail);
                params.put("password",mpassword);

                return params;
            }

        };
        requestQueue.add(request);
//////////////////////////////////////////////////////

    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void stringToJsonObject (String jsonString){

        try {

            JSONObject obj = new JSONObject(jsonString);

            Log.d("My App", obj.toString());

        } catch (Throwable t) {
            Log.e("users", "Could not parse malformed JSON: \"" + jsonString + "\"");
        }
    }


    private class Login extends AsyncTask<String, String, JSONObject> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SplashActivity.this);
            pDialog.setMessage("Finding user. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected JSONObject doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_users, "GET", params);

            // Check your log cat for JSON response
            if(json != null) {
                Log.d("All Users: ", json.toString());
                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        return json;

                    } else {
                        // no users found
                        Log.d("No users found", "None found");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return json;
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(JSONObject result) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if(result != null) {
                //Users found
                // Getting Array of Users
                try {
                    users = result.getJSONArray(TAG_USERS);
                    Log.d("Json Test", users.toString());
                    Log.d("JSON Response", result.names().toString());
                    UsernameKVPairs uvp = new UsernameKVPairs(result);
                    System.out.println("map : " + uvp.userMap(result));
                    userValidate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else {
                Toast.makeText(SplashActivity.this,
                        "No connection - please check Internet",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    private void openActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private  void userValidate(){
        String etemail = etUsername.getText().toString();
        String etpw = etPassword.getText().toString();

        if (users.toString().contains(etemail) && users.toString().contains(etpw)) {
            // User found
            Log.d("user found", "User was found");
            openActivity();
        } else {

            //no User found
            Log.d("No user found", "No one here by that name");
            Toast.makeText(getApplicationContext(), "No User Found Please Try Again",
                    Toast.LENGTH_LONG).show();
        }
    }
}










