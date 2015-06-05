package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.utils.SQLiteHandler;
import inspectplus.dpwgroup.com.inspectplus.utils.SessionManager;


/**
 * Created by Barry Dempsey on 01/05/15.
 */
public class TodoListDetailActivity extends ActionBarActivity {
    private TextView checkLabel;
    private ImageButton camerBtn;
    private Spinner spEventCat, spBuildingElClass;
    private Toolbar toolbar;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    Button btnShowLocation;
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fade in and out animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //
        setContentView(R.layout.list_todo_detail_layout);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.inspect_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        spEventCat = (Spinner) findViewById(R.id.sp_inspection_event_cat_title);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.regulations, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spEventCat.setAdapter(adapter);

        spBuildingElClass = (Spinner) findViewById(R.id.sp_building_element_cat_title);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.regulations, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spBuildingElClass.setAdapter(adapter2);

        checkLabel = (TextView) findViewById(R.id.check_label);
        //camerBtn = (ImageButton)findViewById(R.id.camera_icon);
        Intent i = getIntent();
        String todoItem = i.getExtras().getString("todo");
        String todo = i.getExtras().getString("todo2");


        checkLabel.setText(todo);

        btnShowLocation = (Button) findViewById(R.id.show_location);

        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gps = new GPSTracker(TodoListDetailActivity.this);

                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Toast.makeText(getApplicationContext(), "Your location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();
                } else {
                    gps.showSettingsAlert();
                }
            }
        });
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
        if (id == R.id.logout) {
            logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(TodoListDetailActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(TodoListDetailActivity.this, ImageGalleryActivity.class);
        startActivity(intent);
    }


}


