package inspectplus.dpwgroup.com.inspectplus.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static inspectplus.dpwgroup.com.inspectplus.utils.Keys.ProjectKeys.*;


import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
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
import inspectplus.dpwgroup.com.inspectplus.models.ListProjectsModel;
import inspectplus.dpwgroup.com.inspectplus.models.Project;
import inspectplus.dpwgroup.com.inspectplus.utils.DividerItemDecoration;
import inspectplus.dpwgroup.com.inspectplus.utils.EventsSingleton;
import inspectplus.dpwgroup.com.inspectplus.utils.Keys;
import inspectplus.dpwgroup.com.inspectplus.utils.L;
import inspectplus.dpwgroup.com.inspectplus.utils.SQLiteHandler;
import inspectplus.dpwgroup.com.inspectplus.utils.SessionManager;
import inspectplus.dpwgroup.com.inspectplus.utils.VolleyErrorHelper;
import inspectplus.dpwgroup.com.inspectplus.utils.VolleySingleton;
import inspectplus.dpwgroup.com.inspectplus.utils.projListAdapter;


public class ListViewActivity extends ActionBarActivity implements projListAdapter.ClickListener {

    private RecyclerView recyclerView;
    private projListAdapter adapterProjList;
    private ListView listView;
    private ArrayList<Project> projects;
    private ArrayList<ListProjectsModel> listProjects = new ArrayList<>();
    private ArrayList<String> projectString = new ArrayList<String>();
    private static boolean isShowing = false;
    private Toolbar toolbar;
    private SQLiteHandler db;
    // Progress Dialog
    private ProgressDialog pDialog;
    private SessionManager session;
    public static final String URL_LISTPROJECTS = "http://10.0.3.2/servicesample/services.php";
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fade in and out animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //
        setContentView(R.layout.projects_activity);

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

        // Fetching user details from sqlite ie: token
        HashMap<String, String> user = db.getUserDetails();
        //
        String name = user.get("name");
        String token = user.get("token");
        sendRequest(name, token);
        // Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.projlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterProjList = new projListAdapter(getApplicationContext());

        adapterProjList.setClickListener((projListAdapter.ClickListener) this);
        recyclerView.setAdapter(adapterProjList);
        // Dividers
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        //
        //


//        listView = (ListView) findViewById(R.id.list);

//		projects = EventsSingleton.getEventsSingleton().getProjects();
//		Log.d("List", "Should be called!");
//		// get a String representation of the Projects
//		for(Project project : projects) {
//			String theId = project.getId();
//			if(theId.length() < 4) theId += "  ";
//			projectString.add("\n" + theId + " | " + project.getName() + ", " + project.getLocation());
//		}
//
//		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_textview, projectString);
//		listView.setAdapter(adapter);
//
//		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//				Toast.makeText(ListViewActivity.this, projects.get(i).toString(), Toast.LENGTH_LONG).show();

//				Intent intent = new Intent(ListViewActivity.this, TodoListActivity.class);
//				intent.putExtra("location", projects.get(i).getName() + ", " + projects.get(i).getLocation());
//				Log.d("location",projects.get(i).getName() );
//				intent.putExtra("id", projects.get(i).getId());
//				startActivity(intent);
//			}
//		});
    }

    private void sendRequest(final String mUserName, final String mToken) {
        pDialog.setMessage("Getting List of Projects ...");
        showDialog();
        Log.d("fields: ", mUserName + " " + mToken);

        //////////////////////////////////// STRING REQUEST
        StringRequest request = new StringRequest(Request.Method.POST, URL_LISTPROJECTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                Log.d("Response", response);
//                Toast.makeText(getApplicationContext(), "Response: " + response.toString(),
//                        Toast.LENGTH_SHORT).show();

                // convert string to jsonObject
                try {

                    JSONObject obj = new JSONObject(response);

                    listProjects = parseJsonObjectResponse(obj);
                    adapterProjList.setProjectsList(listProjects);
                    Log.d("My App", obj.toString());

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
                    Log.e("projects", "Could not parse malformed JSON: \"" + response + "\"");
                }
                //


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = VolleyErrorHelper.getMessage(error, ListViewActivity.this);
                // displayYourMessageHere("...");
                Toast.makeText(getApplicationContext(), "Response Error: " + message,
                        Toast.LENGTH_LONG).show();
                logoutUser();

//                if(error instanceof NoConnectionError) {
//                    logoutUser();
//                    Toast.makeText(getApplicationContext(), "No internet Access, Check your internet connection.",
//                            Toast.LENGTH_LONG).show();
//                }
                // error
                 Log.d("Error Response:", ""+ error.getMessage());
//                Toast.makeText(getApplicationContext(), "Response Error: " + error.getMessage(),
//                        Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "listProjects");
                params.put("token", mToken);


                return params;
            }

        };
        requestQueue.add(request);
//////////////////////////////////////////////////////

    }

    private ArrayList<ListProjectsModel> parseJsonObjectResponse(JSONObject response) {
        ArrayList<ListProjectsModel> listProjects = new ArrayList<>();

        if (response == null || response.length() == 0) {

        }
        try {
            StringBuilder data = new StringBuilder();
            JSONArray arrayProjects = response.getJSONArray(KEY_PROJECTS);
            for (int i = 0; i < arrayProjects.length(); i++) {
                JSONObject currentProject = arrayProjects.getJSONObject(i);
                String projnum = currentProject.getString(KEY_PROJECTNUMBER);
                String projname = currentProject.getString(KEY_PROJECTNAME);
                String projloc = currentProject.getString(KEY_PROJECTLOCATION);
                String projid = currentProject.getString(KEY_PROJECTID);
                data.append(projnum + "\n");

                ListProjectsModel listprojectmodel = new ListProjectsModel();
                listprojectmodel.setProjectNumber(projnum);
                listprojectmodel.setProjectName(projname);
                listprojectmodel.setProjectLocation(projloc);
                listprojectmodel.setProjectId(projid);
                listProjects.add(listprojectmodel);
            }

            Log.d("proj List", listProjects.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listProjects;
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
        if(id==R.id.gallery){
            Intent intent = new Intent(ListViewActivity.this, ImageUploadActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(ListViewActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void itemClicked(View view, int position) {

        ListProjectsModel projModel = listProjects.get(position);
        String projectid = projModel.getProjectId();
        Intent intent = new Intent(ListViewActivity.this, TodoListActivity.class);
        intent.putExtra("project_id", projectid);
        Log.d("project id: ", "" + projectid);
        startActivity(intent);

    }


}
