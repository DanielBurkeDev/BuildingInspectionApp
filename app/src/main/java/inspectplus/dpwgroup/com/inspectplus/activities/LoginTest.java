package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.utils.JSONParser;
import inspectplus.dpwgroup.com.inspectplus.utils.UsersSingleton;

/**
 * Created by skatgroovey on 06/05/2015.
 */
public class LoginTest extends Activity{
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private String usersFlag = "usersFlag";
    private JSONArray users = null;
    JSONParser parser; // WHERE userid = ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etUsername = (EditText) findViewById(R.id.et_username2);
        etPassword = (EditText) findViewById(R.id.et_password2);
        btnLogin = (Button) findViewById(R.id.btn_login2);
        btnLogin.setOnClickListener(new ButtonListener());
        etUsername.setText("jsmith@dpw-group.com");
        etPassword.setText("f5489cd12b8b28f2825bff08c709ae6be7193707");


        parser = new JSONParser(LoginTest.this,
                "http://f12.solutions/scrpt/dpw/get_all_users.php", usersFlag);

    }

    // Button Listener
    private class ButtonListener implements View.OnClickListener {

        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_login2:
                    Toast.makeText(getApplicationContext(), "Login Button Selected",
                            Toast.LENGTH_SHORT).show();

                   // finish();
                    userValidate();
                    break;

                default:
                    Toast.makeText(getApplicationContext(), "No options selected",
                            Toast.LENGTH_SHORT).show();
            }

        }
    }

    private  void userValidate(){
        // EditText contents
        String etemail = etUsername.getText().toString();
        String etpw = etPassword.getText().toString();
        //
        ArrayList usersSingleton = UsersSingleton.getUsersSingleton().getUsers();
        Log.d("User Singleton",usersSingleton.toString() );

        if (parser.flag()) {
            // Validation
            if (usersSingleton.contains(etemail) && usersSingleton.contains(etpw)) {
                // User found

                Log.d("user found", "User was found");
                Intent intent = new Intent(LoginTest.this, MainActivity.class);
                startActivity(intent);
            } else {

                //no User found
                Log.d("No user found", "No one here by that name");
                Toast.makeText(getApplicationContext(), "No User Found Please Try Again",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            Log.d("Parser", "Not Finished");
        }

    }
}
