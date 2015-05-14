package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;


import java.util.ArrayList;

import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.models.InspectionEvent;
import inspectplus.dpwgroup.com.inspectplus.models.UnplannedInspection;
import inspectplus.dpwgroup.com.inspectplus.utils.MyAdapter;

/**
 * Created by Barry Dempsey on 01/05/15.
 * DevStream
 */
public class TodoListActivity extends ListActivity {
    private ListView listView;
    private ArrayList<String> todoList = new ArrayList<String>();
    private ArrayList<InspectionEvent> events = new ArrayList<InspectionEvent>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get intent
        Intent i = getIntent();
        final String title = i.getExtras().getString("location");
        String id = i.getExtras().getString("id");
//        // Actionbar
//        ActionBar actionBar = getActionBar();
//        actionBar.setLogo(R.drawable.inspect_logo);
//        actionBar.setDisplayShowTitleEnabled(false);
//      //  actionBar.setTitle(title);
//        actionBar.setHomeButtonEnabled(true);



        // perform a SQL query using the unique ID
        // dummy data
        populateTheData();
        MyAdapter adapter = new MyAdapter(this, todoList);
        setListAdapter(adapter);
    }

    private void populateTheData() {
        // dummy data which will be replaced by JSON response
        UnplannedInspection insp = new UnplannedInspection();
        insp.setId("1111");
        insp.setName("Check Wall insulation");
        insp.setLocation("All Bedrooms");

        UnplannedInspection insp2 = new UnplannedInspection();
        insp2.setId("2222");
        insp2.setName("Check Fire-retardant materials");
        insp2.setLocation("Ground Floor");
        //
        UnplannedInspection insp3 = new UnplannedInspection();
        insp3.setId("3333");
        insp3.setName("Check all glazing on 1st floor");
        insp3.setLocation("First Floor");

        //
        UnplannedInspection insp4= new UnplannedInspection();
        insp4.setId("4444");
        insp4.setName("Check Electrical");
        insp4.setLocation("All Rooms");
        //
        UnplannedInspection insp5 = new UnplannedInspection();
        insp5.setId("5555");
        insp5.setName("Check Ceilings");
        insp5.setLocation("All Rooms");
        //
        UnplannedInspection insp6 = new UnplannedInspection();
        insp6.setId("6666");
        insp6.setName("Check Wall insulation");
        insp6.setLocation("All Rooms");
        //
        UnplannedInspection insp7 = new UnplannedInspection();
        insp7.setId("7777");
        insp7.setName("Check Flooring");
        insp7.setLocation("All Rooms");

        addToEventsList(insp, insp2, insp3, insp4, insp5, insp6, insp7);
    }

    private void addToEventsList(InspectionEvent... es) {
        for(InspectionEvent event : es) {
            events.add(event);
            todoList.add(event.getId() + " - " + event.getName() + " on " + event.getLocation());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, ListViewActivity.class);
                startActivity(i);
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
