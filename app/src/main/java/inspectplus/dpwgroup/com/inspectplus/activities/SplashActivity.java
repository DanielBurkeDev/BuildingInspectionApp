package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dpwgroup.inspectplus.model.UsernameKVPairs;
import inspectplus.dpwgroup.com.inspectplus.JSONParser;
import inspectplus.dpwgroup.com.inspectplus.R;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends Activity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    // Progress Dialog
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    //  private ArrayList<HashMap<String, String>> usersList;
    // url to get all products list
    private static String url_all_users = "http://f12.solutions/scrpt/get_all_users.php";
    // products JSONArray
    private JSONArray users = null;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERS = "users";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_FIRSTNAME = "firstName";
    private static final String TAG_PWD = "pwd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Hashmap for ListView
        //   usersList = new ArrayList<HashMap<String, String>>();

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new ButtonListener());
        etUsername.setText("jsmith@dpw-group.com");
        etPassword.setText("f24578209a524f8a47611a5f7f816ae108564df7");
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
                        //Users found
                        // Getting Array of Users
                        users = json.getJSONArray(TAG_USERS);
                        Log.d("Json Test", users.toString());

                        // looping through All Users
                        for (int i = 0; i < users.length(); i++) {
                            //  userValidate();

                        }
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
                Log.d("JSON Response", result.names().toString());
                UsernameKVPairs uvp = new UsernameKVPairs(result);

                System.out.println("map : " + uvp.userMap(result));
                userValidate();
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

    // Button Listener
    private class ButtonListener implements View.OnClickListener {

        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_login:
                    Toast.makeText(getApplicationContext(), "Login Button Selected",
                            Toast.LENGTH_SHORT).show();
                    new Login().execute();
//                    Intent intent = new Intent(SplashActivity.this, RegisteredProjectsActivity.class);
//                    startActivity(intent);
                    break;

                default:
                    Toast.makeText(getApplicationContext(), "No options selected",
                            Toast.LENGTH_SHORT).show();
            }

        }
    }
}










