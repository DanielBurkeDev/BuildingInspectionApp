package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import java.util.ArrayList;

import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.models.InspectionEvent;
import inspectplus.dpwgroup.com.inspectplus.utils.JSONParser;


public class MainActivity extends Activity {
    private ListView listView;
    private ArrayList<InspectionEvent> events;
    private String projectsFlag = "projectsFlag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fade in and out animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //
        setContentView(R.layout.activity_main);
        getActionBar().setLogo(R.drawable.inspect_logo);

        getActionBar().setDisplayShowTitleEnabled(false);

        JSONParser parser = new JSONParser(MainActivity.this,
                "http://f12.solutions/scrpt/dpw/get_all_projects.php", projectsFlag); // WHERE userid = ?
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
