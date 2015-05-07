package inspectplus.dpwgroup.com.inspectplus.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import inspectplus.dpwgroup.com.inspectplus.R;


/**
 * Created by Barry Dempsey on 01/05/15.
 */
public class TodoListDetailActivity extends Activity {
    private TextView checkLabel;
    private ImageButton camerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_todo_detail_layout);
        checkLabel = (TextView)findViewById(R.id.check_label);
        camerBtn = (ImageButton)findViewById(R.id.camera_icon);
        Intent i = getIntent();
        String todoItem = i.getExtras().getString("todo");
        String todo = i.getExtras().getString("todo2");
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(todoItem);
        actionBar.setIcon(R.drawable.todo);
        actionBar.setHomeButtonEnabled(true);
        checkLabel.setText("Item " + todo);
        camerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TodoListDetailActivity.this);
                builder.setTitle("Activate Camera");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // OK
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Cancel
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Save Inspection Notes?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // save this
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // cancel this
                    }
                });
                builder.show();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
