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
import android.util.Log;
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

public class TodoListDetailActivity extends ActionBarActivity {
    private String projectid, projnum, projname, inspEventCat, buildingElementCat, assignedcertifier,
            assignedcertifiercompany, ancilliarycertifier, ancilliarycompany, inspectionplanrefnum,
            inspectioneventnum, responseactionoriginator, scheduleddate, inspectioneventid;
    private TextView checkLabel, insp_plan_ref_num, insp_event_ref_num, ass_certifier,
            ass_certifier_co, proj_num, proj_name, resp_action_orig, anc_certifier, anc_cert_co;
    private ImageButton camerBtn;
    private Spinner spEventCat, spBuildingElClass;
    private Toolbar toolbar;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private Button btnShowLocation, btncamera, btnupload;

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
        toolbar.setLogo(R.drawable.inspect_logo_080615);
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

        getExtras();
        setUpUI();


        //  checkLabel.setText(todo);

        btnupload = (Button) findViewById(R.id.upload);
        btnupload.setOnClickListener(new ButtonListener());
        btncamera = (Button) findViewById(R.id.camera);
        btncamera.setOnClickListener(new ButtonListener());
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
        getMenuInflater().inflate(R.menu.menu_todolistdetail, menu);
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
        if (id == R.id.upload) {
            Intent intent = new Intent(TodoListDetailActivity.this, ImageUploadActivity.class);
            intent.putExtra("project_id", projectid);
            intent.putExtra("inspection_event_id", inspectioneventid);
            startActivity(intent);


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

    private void getExtras() {
        // get intent
        Intent i = getIntent();
        //  final String title = i.getExtras().getString("location");
        projectid = i.getExtras().getString("project_id");
        inspectioneventid = i.getExtras().getString("insp_event_id");
        projnum = i.getExtras().getString("project_num");
        projname = i.getExtras().getString("project_name");
        inspEventCat = i.getExtras().getString("insp_event_cat");
        buildingElementCat = i.getExtras().getString("building_elem_cat");
        assignedcertifier = i.getExtras().getString("ass_certifier");
        assignedcertifiercompany = i.getExtras().getString("ass_cert_co");
        ancilliarycertifier = i.getExtras().getString("anc_certifier");
        ancilliarycompany = i.getExtras().getString("anc_co");
        inspectionplanrefnum = i.getExtras().getString("insp_plan_ref_num");
        inspectioneventnum = i.getExtras().getString("ins_event_num");
        responseactionoriginator = i.getExtras().getString("resp_act_originator");
        scheduleddate = i.getExtras().getString("sched_date");

        Log.d("detail: ", "" + projectid + " " + inspectionplanrefnum + " " + inspectioneventnum);


    }

    private void setUpUI() {
        insp_plan_ref_num = (TextView) findViewById(R.id.tv_inspection_plan_title);
        insp_plan_ref_num.setText(inspectionplanrefnum);
        insp_event_ref_num = (TextView) findViewById(R.id.tv_inspection_event_refnum_title);
        insp_event_ref_num.setText(inspectioneventnum);
        ass_certifier = (TextView) findViewById(R.id.tv_assigned_certifier_name);
        ass_certifier.setText(assignedcertifier);
        ass_certifier_co = (TextView) findViewById(R.id.tv_assigned_certifier_company_title);
        ass_certifier_co.setText(assignedcertifiercompany);
        proj_num = (TextView) findViewById(R.id.tv_project_num_title);
        proj_num.setText(projnum);
        anc_certifier = (TextView) findViewById(R.id.tv_ancilliary_certifier_title);
        anc_certifier.setText(ancilliarycertifier);
        anc_cert_co = (TextView) findViewById(R.id.tv_ancilliary_certifier_co_title);
        anc_cert_co.setText(ancilliarycompany);
        proj_name = (TextView) findViewById(R.id.tv_project_name_title);
        proj_name.setText(projname);
        resp_action_orig = (TextView) findViewById(R.id.tv_response_action_originator_title);
        resp_action_orig.setText(responseactionoriginator);
    }


    // Button Listener
    private class ButtonListener implements View.OnClickListener {

        public void onClick(View v) {


            switch (v.getId()) {
                case R.id.upload:
                    Intent intent = new Intent(TodoListDetailActivity.this, ImageUploadActivity.class);
                    intent.putExtra("project_id", projectid);
                    intent.putExtra("inspection_event_id", inspectioneventid);
                    startActivity(intent);
                    break;
                case R.id.camera:
                    Intent i = new Intent(TodoListDetailActivity.this, ImageGalleryActivity.class);
                    startActivity(i);
                    break;

                default:
                    Toast.makeText(getApplicationContext(), "No options selected",
                            Toast.LENGTH_SHORT).show();
            }

        }
    }
    //        btnShowLocation.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                gps = new GPSTracker(TodoListDetailActivity.this);
//
//                if (gps.canGetLocation()) {
//                    double latitude = gps.getLatitude();
//                    double longitude = gps.getLongitude();
//
//                    Toast.makeText(getApplicationContext(), "Your location is -\nLat: " + latitude + "\nLong: "
//                            + longitude, Toast.LENGTH_LONG).show();
//                } else {
//                    gps.showSettingsAlert();
//                }
//            }
//        });
//    }
}


