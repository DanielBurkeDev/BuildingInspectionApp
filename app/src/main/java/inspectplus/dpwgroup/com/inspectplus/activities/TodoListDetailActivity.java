package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import inspectplus.dpwgroup.com.inspectplus.R;


/**
 * Created by Barry Dempsey on 01/05/15.
 */
public class TodoListDetailActivity extends Activity {
    private TextView checkLabel;
    private ImageButton camerBtn;


    Button btnShowLocation;
            GPSTracker gps;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.list_todo_detail_layout);
            checkLabel = (TextView) findViewById(R.id.check_label);
            //camerBtn = (ImageButton)findViewById(R.id.camera_icon);
            Intent i = getIntent();
            String todoItem = i.getExtras().getString("todo");
            String todo = i.getExtras().getString("todo2");

            // Actionbar
            ActionBar actionBar = getActionBar();
            actionBar.setTitle(todoItem);
            actionBar.setIcon(R.drawable.todo);
            actionBar.setHomeButtonEnabled(true);
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
                            + longitude, Toast.LENGTH_LONG).    show();
                } else {
                    gps.showSettingsAlert();
                }
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(TodoListDetailActivity.this, ImageGalleryActivity.class);
        startActivity(intent);
    }




}


