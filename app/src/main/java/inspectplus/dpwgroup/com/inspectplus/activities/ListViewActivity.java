package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.models.Project;
import inspectplus.dpwgroup.com.inspectplus.utils.EventsSingleton;


public class ListViewActivity extends Activity {
	private ListView listView;
	private ArrayList<Project> projects;
	private ArrayList<String> projectString = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		listView = (ListView)findViewById(R.id.list);
		projects = EventsSingleton.getEventsSingleton().getProjects();
		Log.d("List", "Should be called!");
		// get a String representation of the Projects
		for(Project project : projects) {
			projectString.add("\n" + project.getId() + " - " + project.getName() + ", " + project.getLocation());
		}

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_textview, projectString);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(ListViewActivity.this, projects.get(i).toString(), Toast.LENGTH_LONG).show();
				Intent intent = new Intent(ListViewActivity.this, TodoListActivity.class);
				intent.putExtra("location", projects.get(i).getName() + ", " + projects.get(i).getLocation());
				Log.d("location",projects.get(i).getName() );
				intent.putExtra("id", projects.get(i).getId());
				startActivity(intent);
			}
		});
	}
}
