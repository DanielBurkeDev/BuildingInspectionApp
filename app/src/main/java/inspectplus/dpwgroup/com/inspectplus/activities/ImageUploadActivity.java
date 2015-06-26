package inspectplus.dpwgroup.com.inspectplus.activities;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.entity.ContentType;
import ch.boye.httpclientandroidlib.entity.mime.HttpMultipartMode;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;
import ch.boye.httpclientandroidlib.entity.mime.content.FileBody;
import ch.boye.httpclientandroidlib.entity.mime.content.StringBody;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import inspectplus.dpwgroup.com.inspectplus.R;

import inspectplus.dpwgroup.com.inspectplus.models.ImageUploadModel;
import inspectplus.dpwgroup.com.inspectplus.utils.Config;

import inspectplus.dpwgroup.com.inspectplus.utils.ImagesAPI;
import inspectplus.dpwgroup.com.inspectplus.utils.SQLiteHandler;
import inspectplus.dpwgroup.com.inspectplus.utils.ServiceGenerator;
import inspectplus.dpwgroup.com.inspectplus.utils.SessionManager;
import inspectplus.dpwgroup.com.inspectplus.utils.VolleyErrorHelper;
import inspectplus.dpwgroup.com.inspectplus.utils.VolleySingleton;
import inspectplus.dpwgroup.com.inspectplus.utils.uploadLoopJ;
import retrofit.Callback;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.mime.TypedFile;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loopj.android.http.*;


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
    public static final String URL_ONLINE_SERVICE = "http://dpw.developerexpert.com/demo/dpw/services.php";
    private String encodedString;
    // RequestParams params = new RequestParams();
    private String imgPath;
    private Bitmap bitmap;
    private Toolbar toolbar;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static int RESULT_LOAD_IMG = 1;
    private GPSTracker gps;
    private String theLatitude;
    private String theLongitude;
    private String imgJsonObjString = "";
    long totalSize = 0;
    private ProgressBar progressBar;
    private ProgressDialog mProgressDialog;
    private TextView txtPercentage;
    private String filePath = null;
    private String name = "";
    private String token = "";
    private String fileName = "";
    private String thecommand = "uploadInspectionEntry";
    private String metaData = "";
    private String access = "readWrite";
    private String et_notes = "";
    private String imgcurTime = "";
    private String trimmedprojid = "";
    // Retrofit
    private static final String ENDPOINT = "http://10.0.3.2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        notes = (EditText) findViewById(R.id.notestest);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressDialog = new ProgressDialog(this);

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
        // Fetching user details from sqlite ie: token
        HashMap<String, String> user = db.getUserDetails();
        //
        name = user.get("name");
        token = user.get("token");


        if (!session.isLoggedIn()) {
            logoutUser();
        }
        getExtras();
        getGPS();

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
        et_notes = notes.getText().toString().trim();
        // When Image is selected from Gallery
        Log.d("imgPath ", imgPath);

        if (imgPath != null && !imgPath.isEmpty() && !et_notes.isEmpty()) {
//            pDialog.setMessage("Converting Image to Binary Data");
//            pDialog.show();
            // Convert image to String using Base64
            //encodeImagetoString();
            //imageEncodingToString();

            //sendRequest(name, token, imgname);
            // new ImageUpload().execute();
            postImageToServer();
            //  postParamsToServer();

            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }


    private void postImageToServer() {
        imgcurTime = dateFormat.format(new Date()).toString();
        metaData = theLatitude + theLongitude;
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("command", "uploadInspectionEntry");
        params.put("token", token);
        params.put("projectId", projectid);
        params.put("inspectionEventId", inspectionEventId);
        params.put("notes", et_notes);
        params.put("metaData", "some data");
        params.put("dateAcquired", imgcurTime);
        params.put("access", "readWrite");

        //  Log.d("params ", thecommand + " " + token + " " + projectid + " " + inspectionEventId + " " + et_notes + " " + metaData + " " + imgcurTime + " " + access + " " + imgPath);

        ImagesAPI service = ServiceGenerator.createService(ImagesAPI.class, ImagesAPI.BASE_URL);
        TypedFile typedFile = new TypedFile("multipart/form-data", new File(imgPath));
        Log.d("params ", params.toString() + " " + imgPath);


        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        service.upload(params, typedFile, new Callback<ImageUploadModel>() {
            @Override
            public void success(ImageUploadModel r, retrofit.client.Response response) {
                Log.d("Retro Response", response.toString());
                Log.d("Retro Response", r.getResult());

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();


            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("retro err", error.toString());
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });
        {

        }


    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private class ImageUpload extends AsyncTask<String, Integer, String> {

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(String... params) {


            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

            File file = new File(imgPath);
            StringBody thecommand = new StringBody("uploadInspectionEntry", ContentType.MULTIPART_FORM_DATA);
            StringBody thetoken = new StringBody(token, ContentType.MULTIPART_FORM_DATA);
            StringBody theprojid = new StringBody(projectid, ContentType.MULTIPART_FORM_DATA);
            StringBody theinspeventid = new StringBody(inspectionEventId, ContentType.MULTIPART_FORM_DATA);
            StringBody thenotes = new StringBody(et_notes, ContentType.MULTIPART_FORM_DATA);
            StringBody theaccess = new StringBody("readWrite", ContentType.MULTIPART_FORM_DATA);
            StringBody themetadata = new StringBody("some meta data", ContentType.MULTIPART_FORM_DATA);
            StringBody thedate = new StringBody(imgcurTime, ContentType.MULTIPART_FORM_DATA);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            // Extra parameters if you want to pass to server
            builder.addPart("command", thecommand);
            builder.addPart("token", thetoken);
            builder.addPart("projectId", theprojid);
            builder.addPart("inspectionEventId", theinspeventid);
            builder.addPart("notes", thenotes);
            builder.addPart("Access", theaccess);
            builder.addPart("metaData", themetadata);
            builder.addPart("dateAcquired ", thedate);


            HttpEntity entity = builder.build();
            httppost.setEntity(entity);
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseString = response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Response from server: ", result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);

        }

    }

    private String buildInspectionEntry() {
        String mnotes = notes.getText().toString();
        String mlatitude = theLatitude;
        String mlongitude = theLongitude;
        String imgcurTime = dateFormat.format(new Date());
        String encImage = "data:image/jpg;base64," + encodedString;
        String maccess = "readWrite";


        try {
            JSONObject inspEntryObj = new JSONObject();
            inspEntryObj.put("notes", mnotes);
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
        encodedString = Base64.encodeToString(byte_arr, 0);
        return "";

    }


    private void getExtras() {
        // get intent
        Intent i = getIntent();
        //  final String title = i.getExtras().getString("location");
        projectid = i.getExtras().getString("project_id");
        inspectionEventId = i.getExtras().getString("inspection_event_id");
        trimmedprojid = projectid.trim();


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


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // Dismiss the progress bar when application is closed
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_upload, menu);
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
        Intent intent = new Intent(ImageUploadActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    private void getGPS() {
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