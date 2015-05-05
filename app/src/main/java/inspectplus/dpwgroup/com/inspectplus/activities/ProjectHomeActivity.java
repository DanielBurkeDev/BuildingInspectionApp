package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import inspectplus.dpwgroup.com.inspectplus.R;


public class ProjectHomeActivity extends Activity {
    private Button btnInspectionEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_home);

        btnInspectionEvents = (Button) findViewById(R.id.btn_inspection_events_page);
        btnInspectionEvents.setOnClickListener(new ButtonListener());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ButtonListener implements View.OnClickListener {

        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.btn_inspection_events_page:
                    Toast.makeText(getApplicationContext(), "Inspection Events Button Selected",
                            Toast.LENGTH_SHORT).show();

                    intent = new Intent(ProjectHomeActivity.this,InspectionEventsActivity.class);
                    startActivity(intent);
                    break;

                default:
                    Toast.makeText(getApplicationContext(), "No options selected",
                            Toast.LENGTH_SHORT).show();
            }

        }
    }
}
