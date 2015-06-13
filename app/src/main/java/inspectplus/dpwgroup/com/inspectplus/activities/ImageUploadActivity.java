package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.models.ListProjectsModel;
import inspectplus.dpwgroup.com.inspectplus.utils.SQLiteHandler;
import inspectplus.dpwgroup.com.inspectplus.utils.SessionManager;
import inspectplus.dpwgroup.com.inspectplus.utils.VolleyErrorHelper;
import inspectplus.dpwgroup.com.inspectplus.utils.VolleySingleton;

import static inspectplus.dpwgroup.com.inspectplus.utils.Keys.ProjectKeys.KEY_PROJECTID;
import static inspectplus.dpwgroup.com.inspectplus.utils.Keys.ProjectKeys.KEY_PROJECTLOCATION;
import static inspectplus.dpwgroup.com.inspectplus.utils.Keys.ProjectKeys.KEY_PROJECTNAME;
import static inspectplus.dpwgroup.com.inspectplus.utils.Keys.ProjectKeys.KEY_PROJECTNUMBER;
import static inspectplus.dpwgroup.com.inspectplus.utils.Keys.ProjectKeys.KEY_PROJECTS;

public class ImageUploadActivity extends ActionBarActivity {
    private EditText notes;
    private String projectid, inspectionEventId;
    private SQLiteHandler db;
    // Progress Dialog
    private SessionManager session;
    // Progress Dialog
    private ProgressDialog pDialog;
    private ProgressDialog prgDialog;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public static final String URL_SERVICE = "http://10.0.3.2/servicesample/services.php";
    private String encodedString;
    // RequestParams params = new RequestParams();
    private String imgPath, fileName;
    private Bitmap bitmap;
    private Toolbar toolbar;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static int RESULT_LOAD_IMG = 1;
    private GPSTracker gps;
    private String theLatitude;
    private  String theLongitude;
    private  String imgJsonObjString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        notes = (EditText) findViewById(R.id.notestest);

        prgDialog = new ProgressDialog(this);
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.inspect_logo_080615);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //
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
        getExtras();

    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    // When Image is selected from Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Php web app
                // params.put("filename", fileName);



            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    // When Upload button is clicked
    public void uploadImage(View v) {
        // Get text from notes and check if empty
        String et_notes = notes.getText().toString().trim();
        // When Image is selected from Gallery
        Log.d("imgPath ", imgPath);

        if (imgPath != null && !imgPath.isEmpty() && !et_notes.isEmpty()) {
            prgDialog.setMessage("Converting Image to Binary Data");
            prgDialog.show();
            // Convert image to String using Base64
            //encodeImagetoString();
            imageEncodingToString();
            // Fetching user details from sqlite ie: token
            HashMap<String, String> user = db.getUserDetails();
            //
            String name = user.get("name");
            String token = user.get("token");
            String imgname = fileName;
            sendRequest(name, token, imgname);

            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void sendRequest(final String mUserName, final String mToken, final String mImgName) {
        pDialog.setMessage("Getting List of Projects ...");
        showDialog();
        Log.d("fields: ", mUserName + " " + mToken + " " + mImgName);
        buildInspectionEntry();


        //////////////////////////////////// STRING REQUEST
        StringRequest request = new StringRequest(Request.Method.POST, URL_SERVICE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                Log.d("Response", response);
//                Toast.makeText(getApplicationContext(), "Response: " + response.toString(),
//                        Toast.LENGTH_SHORT).show();

                // convert string to jsonObject
                try {

                    JSONObject obj = new JSONObject(response);

//                    listProjects = parseJsonObjectResponse(obj);
//                    adapterProjList.setProjectsList(listProjects);
                    Log.d("imgupload resp", obj.toString());

                    // Check for error in json
                    if (!obj.equals("ERROR")) {
                        String imgtoken = obj.getString("responseToken");
                        Log.d("check imgtoken",  mImgName + " " + imgtoken);

                        // Add to sqlite db

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
                String message = VolleyErrorHelper.getMessage(error, ImageUploadActivity.this);
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
                Log.d("Error Response:", "" + error.getMessage());
//                Toast.makeText(getApplicationContext(), "Response Error: " + error.getMessage(),
//                        Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "uploadInspectionEntry");
                params.put("token", mToken);
                params.put("projectId", projectid);
                params.put("inspectionEventId", inspectionEventId);
                params.put("inspectionEntry", imgJsonObjString);


                return params;
            }

        };
        requestQueue.add(request);
//////////////////////////////////////////////////////

    }

    private String buildInspectionEntry() {
        String mnotes = notes.getText().toString();
        String mlatitude = theLatitude;
        String mlongitude = theLongitude;
        String imgcurTime = dateFormat.format(new Date());
        String encImage ="data:image/jpg;base64," + encodedString;
        String maccess = "";


        try {
            JSONObject inspEntryObj = new JSONObject();
            inspEntryObj.put("notes", mnotes );
            inspEntryObj.put("dateAcquired", imgcurTime);
            inspEntryObj.put("image", encImage);
            inspEntryObj.put("access", maccess);

            JSONObject metaDataObj = new JSONObject();
            metaDataObj.put("Latitude", mlatitude);
            metaDataObj.put("Longitude", mlongitude);

            inspEntryObj.put("metaData", metaDataObj);

            imgJsonObjString = inspEntryObj.toString();

            Log.d("created json ", "" + inspEntryObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return imgJsonObjString;
    }

    private String imageEncodingToString() {
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        bitmap = BitmapFactory.decodeFile(imgPath,
                options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Must compress the Image to reduce image size to make upload easy
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byte_arr = stream.toByteArray();
        // Encode Image to String
        encodedString =  Base64.encodeToString(byte_arr, 0);
        return "";

    }

//    private ArrayList<ListProjectsModel> parseJsonObjectResponse(JSONObject response) {
//        ArrayList<ListProjectsModel> listProjects = new ArrayList<>();
//
//        if (response == null || response.length() == 0) {
//
//        }
//        try {
//            StringBuilder data = new StringBuilder();
//            JSONArray arrayProjects = response.getJSONArray(KEY_PROJECTS);
//            for (int i = 0; i < arrayProjects.length(); i++) {
//                JSONObject currentProject = arrayProjects.getJSONObject(i);
//                String projnum = currentProject.getString(KEY_PROJECTNUMBER);
//                String projname = currentProject.getString(KEY_PROJECTNAME);
//                String projloc = currentProject.getString(KEY_PROJECTLOCATION);
//                String projid = currentProject.getString(KEY_PROJECTID);
//                data.append(projnum + "\n");
//
//                ListProjectsModel listprojectmodel = new ListProjectsModel();
//                listprojectmodel.setProjectNumber(projnum);
//                listprojectmodel.setProjectName(projname);
//                listprojectmodel.setProjectLocation(projloc);
//                listprojectmodel.setProjectId(projid);
//                listProjects.add(listprojectmodel);
//            }
//
//            Log.d("proj List", listProjects.toString());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return listProjects;
//    }

    private void getExtras() {
        // get intent
        Intent i = getIntent();
        //  final String title = i.getExtras().getString("location");
        projectid = i.getExtras().getString("project_id");
        inspectionEventId = i.getExtras().getString("inspection_event_id");


        Log.d("Extras : ", "" + projectid + " " + inspectionEventId);


    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    // AsyncTask - To convert Image to String
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = "data:image/jpg;base64," + Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                // Put converted Image string into Async Http Post param
                // params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
        prgDialog.setMessage("Invoking Php");
//        AsyncHttpClient client = new AsyncHttpClient();
//        // Don't forget to change the IP address to your LAN address. Port no as well.
//        client.post("http://192.168.2.5:9000/imgupload/upload_image.php",
//                params, new AsyncHttpResponseHandler() {
//                    // When the response returned by REST has Http
//                    // response code '200'
//                    @Override
//                    public void onSuccess(String response) {
//                        // Hide Progress Dialog
//                        prgDialog.hide();
//                        Toast.makeText(getApplicationContext(), response,
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                    // When the response returned by REST has Http
//                    // response code other than '200' such as '404',
//                    // '500' or '403' etc
//                    @Override
//                    public void onFailure(int statusCode, Throwable error,
//                                          String content) {
//                        // Hide Progress Dialog
//                        prgDialog.hide();
//                        // When Http response code is '404'
//                        if (statusCode == 404) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Requested resource not found",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                        // When Http response code is '500'
//                        else if (statusCode == 500) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Something went wrong at server end",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                        // When Http response code other than 404, 500
//                        else {
//                            Toast.makeText(
//                                    getApplicationContext(),
//                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
//                                            + statusCode, Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    }
//                });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // Dismiss the progress bar when application is closed
        if (prgDialog != null) {
            prgDialog.dismiss();
        }
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
        if (id == R.id.gallery) {
            Intent intent = new Intent(ImageUploadActivity.this, ImageGalleryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(ImageUploadActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }
    private void getGPS(){
        gps = new GPSTracker(ImageUploadActivity.this);

        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            theLatitude = String.valueOf(latitude);
            theLongitude = String.valueOf(longitude);



            Toast.makeText(getApplicationContext(), "Your location is -\nLat: " + latitude + "\nLong: "
                    + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
    }
}