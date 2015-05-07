package inspectplus.dpwgroup.com.inspectplus.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import inspectplus.dpwgroup.com.inspectplus.activities.ListViewActivity;
import inspectplus.dpwgroup.com.inspectplus.activities.MainActivity;
import inspectplus.dpwgroup.com.inspectplus.models.Project;
import inspectplus.dpwgroup.com.inspectplus.models.UsersModel;

/**
 * Created by johndoe on 30/04/15.
 */
@SuppressWarnings("ALL")
public class JSONParser extends JSONObject {
    private Context context;
    private String url;
    private InputStream is;
    private String flag;
    private boolean doneWithParser = false;
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<UsersModel> users = new ArrayList<UsersModel>();

    public JSONParser(Context context, String url, String flag) {
        this.context = context;
        this.url = url;
        this.flag = flag;
        new NetworkOperation().execute();
    }

    private class NetworkOperation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = null;
            String line = null;
            StringBuilder sb = null;
            String result = null;
            try {
                response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("log_tag", " Connected to database");
                // convert result response to string

                BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, "iso-8859-1"), 8);
                sb = new StringBuilder();
                sb.append(reader.readLine() + "\n");

                while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                }
                Log.d("log_tag", " Got this far.." + sb);
                is.close();
                result = sb.toString();
                } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
                return result;

            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Connected", s);
          //  Log.d("usersFlag", "is true");
            switch (flag){
                case "usersFlag":
                    Log.d("usersFlag", "is true");
                    try {
                        JSONObject obj = new JSONObject(s);
                        JSONArray jArray = obj.getJSONArray("users");
                        populateTheUsersObject(jArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "projectsFlag":
                    Log.d("projectsFlag", "is true");

                    try {
                        JSONObject obj = new JSONObject(s);
                        JSONArray jArray = obj.getJSONArray("projects");
                        populateTheEventObject(jArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }



//            if (flag == "usersFlag") {
//                Log.d("usersFlag", "is true");
//                try {
//                    JSONObject obj = new JSONObject(s);
//                    JSONArray jArray = obj.getJSONArray("users");
//                    populateTheUsersObject(jArray);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }else if (flag == "projectsFlag"){
//                Log.d("projectsFlag", "is true");
//
//                try {
//                    JSONObject obj = new JSONObject(s);
//                    JSONArray jArray = obj.getJSONArray("projects");
//                    populateTheEventObject(jArray);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

    private void populateTheEventObject(JSONArray jArray) {
        JSONObject obj = null;
        String s = null;
        Project project;
        for(int i = 0; i < jArray.length(); i++) {
            try {
                obj = jArray.getJSONObject(i);
                String id = obj.getString("projectId");
                String name = obj.getString("projectName");
                String location = obj.getString("projectLocation");
                String date = null; //
                Log.d("Results", id + ", " + name + ", " + location);
                //
                project = new Project(id, name, location);
                addToList(project);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateTheUsersObject(JSONArray jArray) {
        JSONObject obj = null;
        String s = null;
        UsersModel usersModel;
        for(int i = 0; i < jArray.length(); i++) {
            try {
                obj = jArray.getJSONObject(i);
                String id = obj.getString("userId");
                String name = obj.getString("firstName");
                String surname = obj.getString("surname");
                String email = obj.getString("email");
                String pwd = obj.getString("pwd");
                String date = null; //
                Log.d("Results", id + ", " + name + ", " + pwd + ", " + email);
                //
                usersModel = new UsersModel(id, name, pwd, email);
                addToUsersList(usersModel);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void addToList(Project project) {
        projects.add(project);
        EventsSingleton.getEventsSingleton().setProjects(projects);

        Intent i = new Intent(context, ListViewActivity.class);
        context.startActivity(i);
    }

    private void addToUsersList(UsersModel usersModel) {
        users.add(usersModel);
        UsersSingleton.getUsersSingleton().setUsers(users);

        if (UsersSingleton.getUsersSingleton().flag()) {
            flag();
        }
//        Intent i = new Intent(context, MainActivity.class);
//        context.startActivity(i);
    }

    public boolean flag(){
        doneWithParser = true;
        return  doneWithParser;
    }
}
