package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import inspectplus.dpwgroup.com.inspectplus.R;

/**
 * Created by skatgroovey on 06/05/2015.
 */
public class LoginTest extends Activity{
    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new ButtonListener());
        etUsername.setText("jsmith@dpw-group.com");
        etPassword.setText("f24578209a524f8a47611a5f7f816ae108564df7");
    }

    // Button Listener
    private class ButtonListener implements View.OnClickListener {

        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_login:
                    Toast.makeText(getApplicationContext(), "Login Button Selected",
                            Toast.LENGTH_SHORT).show();

                    break;

                default:
                    Toast.makeText(getApplicationContext(), "No options selected",
                            Toast.LENGTH_SHORT).show();
            }

        }
    }
}
