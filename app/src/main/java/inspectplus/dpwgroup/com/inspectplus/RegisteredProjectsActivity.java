package inspectplus.dpwgroup.com.inspectplus;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class RegisteredProjectsActivity extends Activity {
    private Button joinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_projects);

        joinButton = (Button) findViewById(R.id.registered_projects_join_btn);
        joinButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisteredProjectsActivity.this, ProjectHomeActivity.class);
                startActivity(intent);
            }

        });
    }
}