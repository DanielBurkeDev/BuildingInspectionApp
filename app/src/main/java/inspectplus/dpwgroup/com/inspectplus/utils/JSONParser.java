package inspectplus.dpwgroup.com.inspectplus.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import inspectplus.dpwgroup.com.inspectplus.activities.ListViewActivity;
import inspectplus.dpwgroup.com.inspectplus.models.Project;
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

/**
 * Created by johndoe on 30/04/15.
 */
@SuppressWarnings("ALL")
public class JSONParser extends JSONObject {
    private Context context;
    private String url;
    private InputStream is;
    private ArrayList<Project> projects = new ArrayList<Project>();
    private int count = 0;
    private JSONArray jArray;

    public JSONParser(Context context, String url) {
        this.context = context;
        this.url = url;
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
            try {
                JSONObject obj = new JSONObject(s);
                jArray = obj.getJSONArray("projects");
                populateTheEventObject(jArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                String projectName = obj.getString("projectName");
                double estimateValue = obj.getDouble("estimatedValue");
                String projectAdmin = obj.getString("projectdmin");
                //Date creationDate = new Date();
                //Date changeDated = new Date();

                /**
                project = new Project(id, name, projectName,
                        location, estimateValue, projectAdmin,
                        creationDate, changeDated );**/
                project = new Project();
                project.setId(id);
                project.setName(name);
                project.setProjectName(projectName);
                project.setProjectAdmin(projectAdmin);
                project.setEstimatedValue(estimateValue);
                Log.d("Results", project.toString());
                addToList(project);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addToList(Project project) {
        count++;
        projects.add(project);
        EventsSingleton.getEventsSingleton().setProjects(projects);
        if(count == jArray.length()) {
            Intent i = new Intent(context, ListViewActivity.class);
            context.startActivity(i);
        }
    }
}
