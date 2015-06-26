package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import static inspectplus.dpwgroup.com.inspectplus.utils.Keys.ProjectKeys.*;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.models.InspectionEvent;
import inspectplus.dpwgroup.com.inspectplus.models.InspectionEventsModel;
import inspectplus.dpwgroup.com.inspectplus.models.UnplannedInspection;
import inspectplus.dpwgroup.com.inspectplus.utils.DividerItemDecoration;
import inspectplus.dpwgroup.com.inspectplus.utils.InspectionListAdapter;
import inspectplus.dpwgroup.com.inspectplus.utils.MyAdapter;
import inspectplus.dpwgroup.com.inspectplus.utils.SQLiteHandler;
import inspectplus.dpwgroup.com.inspectplus.utils.SessionManager;
import inspectplus.dpwgroup.com.inspectplus.utils.VolleyErrorHelper;
import inspectplus.dpwgroup.com.inspectplus.utils.VolleySingleton;

public class TodoListActivity extends ActionBarActivity implements InspectionListAdapter.ClickListener {
    private RecyclerView recyclerView;
    private InspectionListAdapter inspectionListAdapter;
    private ListView listView;
    private ArrayList<String> todoList = new ArrayList<String>();
    private ArrayList<InspectionEventsModel> listevents = new ArrayList<InspectionEventsModel>();
    private Toolbar toolbar;
    private ProgressDialog pDialog;
    private SessionManager session;
    public static final String URL_LISTEVENTS = "http://10.0.3.2/servicesample/services.php";
    public static final String URL_ONLINE_SERVICE = "http://dpw.developerexpert.com/demo/dpw/services.php";
    public static final String URL_SKAT_SERVICE = "http://skatdev.com/tst/dpw/dpw2606/services.php";
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private SQLiteHandler db;
    private String projectid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fade in and out animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //
        setContentView(R.layout.inspections_activity);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.inspect_logo_080615);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // Volley
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        //
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        // get intent
        Intent i = getIntent();
        //  final String title = i.getExtras().getString("location");
        projectid = i.getExtras().getString("project_id");
        Log.d("todo projid: ", "" + projectid);
        // Fetching user details from sqlite ie: token
        HashMap<String, String> user = db.getUserDetails();
        //
        String name = user.get("name");
        String token = user.get("token");
        sendRequest(name, token);
        // Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.inspectionslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        inspectionListAdapter = new InspectionListAdapter(getApplicationContext());
        inspectionListAdapter.setClickListener((InspectionListAdapter.ClickListener) this);
        recyclerView.setAdapter(inspectionListAdapter);
        // Dividers
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        //
        //

        // perform a SQL query using the unique ID
        // dummy data
        // populateTheData();
//        MyAdapter adapter = new MyAdapter(this, todoList);
//        setListAdapter(adapter);
    }

    private void sendRequest(final String mUserName, final String mToken) {
        pDialog.setMessage("Getting List of Events ...");
        showDialog();
        Log.d("fields: ", mUserName + " " + mToken);

        //////////////////////////////////// STRING REQUEST
        StringRequest request = new StringRequest(Request.Method.POST, URL_ONLINE_SERVICE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                Log.d("Response", response);
//                Toast.makeText(getApplicationContext(), "Response: " + response.toString(),
//                        Toast.LENGTH_SHORT).show();

                // convert string to jsonObject
                try {

                    JSONObject obj = new JSONObject(response);

                    listevents = parseJsonObjectResponse(obj);
                    inspectionListAdapter.setListInspectionEvents(listevents);
                    Log.d("insp events", obj.toString());

                    // Check for error in json
                    if (!obj.equals("ERROR")) {

                        // Launch main activity
//						Intent intent = new Intent(SplashActivity.this,
//								MainActivity.class);
//						startActivity(intent);
//						finish();

                    } else {
                        // Error in login. Get the error message
//						String errorMsg = array.getString("message");
//						Toast.makeText(getApplicationContext(),
//								errorMsg, Toast.LENGTH_LONG).show();
                    }


                } catch (Throwable t) {
                    Log.e("Events", "Could not parse malformed JSON: \"" + response + "\"");
                }
                //


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = VolleyErrorHelper.getMessage(error, TodoListActivity.this);
                // displayYourMessageHere("...");
                Toast.makeText(getApplicationContext(), "Response Error: " + message,
                        Toast.LENGTH_LONG).show();
                logoutUser();
                // error
                Log.d("Error Response:", error.getMessage());
//                Toast.makeText(getApplicationContext(), "Response Error: " + error.getMessage(),
//                        Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "listInspections");
                params.put("token", mToken);


                return params;
            }

        };
        requestQueue.add(request);
//////////////////////////////////////////////////////

    }

    private ArrayList<InspectionEventsModel> parseJsonObjectResponse(JSONObject response) {
        ArrayList<InspectionEventsModel> listevents = new ArrayList<>();

        if (response == null || response.length() == 0) {

        }
        try {
            StringBuilder data = new StringBuilder();
            JSONArray arrayInspections = response.getJSONArray(KEY_INSPECTIONS);
            for (int i = 0; i < arrayInspections.length(); i++) {
                JSONObject currentInspection = arrayInspections.getJSONObject(i);
                String projid = currentInspection.getString(KEY_PROJECTID);
                String projnum = currentInspection.getString(KEY_PROJECTNUMBER);
                String projname = currentInspection.getString(KEY_PROJECTNAME);
                String inspEventCat = currentInspection.getString(KEY_INSPECTION_EVENT_CATEGORY);
                String buildingElementCat = currentInspection.getString(KEY_BUILDING_ELEMENT_CLASSIFICATION);

                String inspectioneventid = currentInspection.getString(KEY_INSPECTION_EVENT_ID);
                String assignedcertifier = currentInspection.getString(KEY_ASSIGNED_CERTIFIER);
                String assignedcertifiercompany = currentInspection.getString(KEY_ASSIGNED_CERTIFIER_COMPANY);
                String ancilliarycompany = currentInspection.getString(KEY_ANCILLIARY_COMPANY);
                String ancilliarycertifier = currentInspection.getString(KEY_ANCILLIARY_CERTIFIER);
                String inspectionplanrefnum = currentInspection.getString(KEY_INSPECTIONPLAN_REFERENCE_NUMBER);
                String inspectioneventnum = currentInspection.getString(KEY_INSPECTION_EVENT_NUMBER);
                String responseactionoriginator = currentInspection.getString(KEY_RESPONSE_ORIGINATOR);
                String scheduleddate = currentInspection.getString(KEY_SCHEDULED_DATE);


                data.append(projnum + "\n");
                // Show events with same project id as item clicked
                Log.d("todo projid: ", "within json forloop" + projectid + " " + projid);
                if (projectid.equals(projid)) {
                    InspectionEventsModel listeventsmodel = new InspectionEventsModel();
                    listeventsmodel.setProjectId(projid);
                    listeventsmodel.setProjectNumber(projnum);
                    listeventsmodel.setProjectName(projname);
                    listeventsmodel.setInspectionEventCategory(inspEventCat);
                    listeventsmodel.setBuildingElementClassification(buildingElementCat);

                    listeventsmodel.setInspectionEventId(inspectioneventid);
                    listeventsmodel.setAssignedCertifier(assignedcertifier);
                    listeventsmodel.setAssignedCertifierCompany(assignedcertifiercompany);
                    listeventsmodel.setAncilliaryCompany(ancilliarycompany);
                    listeventsmodel.setAncilliaryCertifier(ancilliarycertifier);
                    listeventsmodel.setInspectionPlanReferenceNumber(inspectionplanrefnum);
                    listeventsmodel.setInspectionEventNumber(inspectioneventnum);
                    listeventsmodel.setResponseActionOriginator(responseactionoriginator);
                    listeventsmodel.setScheduledDate(scheduleddate);


                    listevents.add(listeventsmodel);
                }

            }

            Log.d("events List", listevents.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listevents;
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
        getMenuInflater().inflate(R.menu.menu_todolist, menu);
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
        Intent intent = new Intent(TodoListActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void itemClicked(View view, int position) {

        InspectionEventsModel inspEventsModel = listevents.get(position);
        String projectid = inspEventsModel.getProjectId();
        String projnum = inspEventsModel.getProjectNumber();
        String projname = inspEventsModel.getProjectName();
        String inspEventCat = inspEventsModel.getInspectionEventCategory();
        String buildingElementCat = inspEventsModel.getBuildingElementClassification();

        String inspectioneventid = inspEventsModel.getInspectionEventId();
        String assignedcertifier = inspEventsModel.getAssignedCertifier();
        String assignedcertifiercompany = inspEventsModel.getAncilliaryCompany();
        String ancilliarycertifier = inspEventsModel.getAncilliaryCertifier();
        String ancilliarycompany = inspEventsModel.getAncilliaryCompany();
        String inspectionplanrefnum = inspEventsModel.getInspectionPlanReferenceNumber();
        String inspectioneventnum = inspEventsModel.getInspectionEventNumber();
        String responseactionoriginator = inspEventsModel.getResponseActionOriginator();
        String scheduleddate = inspEventsModel.getScheduledDate();

        Intent intent = new Intent(TodoListActivity.this, TodoListDetailActivity.class);
        intent.putExtra("project_id", projectid);

        intent.putExtra("insp_event_id", inspectioneventid);
        intent.putExtra("project_num", projnum);
        intent.putExtra("project_name", projname);
        intent.putExtra("insp_event_cat", inspEventCat);
        intent.putExtra("building_elem_cat", buildingElementCat);
        intent.putExtra("ass_certifier", assignedcertifier);
        intent.putExtra("ass_cert_co", assignedcertifiercompany);
        intent.putExtra("anc_certifier", ancilliarycertifier);
        intent.putExtra("anc_co", ancilliarycompany);
        intent.putExtra("insp_plan_ref_num", inspectionplanrefnum);
        intent.putExtra("ins_event_num", inspectioneventnum);
        intent.putExtra("resp_act_originator", responseactionoriginator);
        intent.putExtra("sched_date", scheduleddate);

        Log.d("put extras: ", "" + ancilliarycertifier + inspectioneventnum + " " + inspectionplanrefnum + projectid + projname + inspEventCat + buildingElementCat + assignedcertifier);
        startActivity(intent);

    }

//    private void populateTheData() {
//        // dummy data which will be replaced by JSON response
//        UnplannedInspection insp = new UnplannedInspection();
//        insp.setId("1111");
//        insp.setName("Check Wall insulation");
//        insp.setLocation("All Bedrooms");
//
//        UnplannedInspection insp2 = new UnplannedInspection();
//        insp2.setId("2222");
//        insp2.setName("Check Fire-retardant materials");
//        insp2.setLocation("Ground Floor");
//        //
//        UnplannedInspection insp3 = new UnplannedInspection();
//        insp3.setId("3333");
//        insp3.setName("Check all glazing on 1st floor");
//        insp3.setLocation("First Floor");
//
//        //
//        UnplannedInspection insp4 = new UnplannedInspection();
//        insp4.setId("4444");
//        insp4.setName("Check Electrical");
//        insp4.setLocation("All Rooms");
//        //
//        UnplannedInspection insp5 = new UnplannedInspection();
//        insp5.setId("5555");
//        insp5.setName("Check Ceilings");
//        insp5.setLocation("All Rooms");
//        //
//        UnplannedInspection insp6 = new UnplannedInspection();
//        insp6.setId("6666");
//        insp6.setName("Check Wall insulation");
//        insp6.setLocation("All Rooms");
//        //
//        UnplannedInspection insp7 = new UnplannedInspection();
//        insp7.setId("7777");
//        insp7.setName("Check Flooring");
//        insp7.setLocation("All Rooms");
//
//        addToEventsList(insp, insp2, insp3, insp4, insp5, insp6, insp7);
//    }

//    private void addToEventsList(InspectionEvent... es) {
//        for (InspectionEvent event : es) {
//            events.add(event);
//            todoList.add(event.getId() + " - " + event.getName() + " on " + event.getLocation());
//        }
//    }
}
