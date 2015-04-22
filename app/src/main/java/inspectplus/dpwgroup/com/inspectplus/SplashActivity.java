package inspectplus.dpwgroup.com.inspectplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SplashActivity extends Activity {
    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new ButtonListener());




    }

    private class ButtonListener implements View.OnClickListener {

        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.btn_login:
                    Toast.makeText(getApplicationContext(), "Login Button Selected",
                            Toast.LENGTH_SHORT).show();
                    intent = new Intent(SplashActivity.this, RegisteredProjectsActivity.class);
                    startActivity(intent);
                    break;

                default:
                    Toast.makeText(getApplicationContext(), "No options selected",
                            Toast.LENGTH_SHORT).show();
            }

        }
    }
}









