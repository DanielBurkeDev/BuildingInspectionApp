package inspectplus.dpwgroup.com.inspectplus.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.activities.TodoListDetailActivity;

public class MyAdapter extends BaseAdapter {
	private ArrayList<String> events;
	private LayoutInflater inflater;
	private Context context;


	public MyAdapter(Context context, ArrayList<String> events) {
		this.context = context;
		this.events = events;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return events.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return events.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		if(convertView == null) {
			view = inflater.inflate(R.layout.row_layout, parent, false);
			TextView text1 = (TextView)view.findViewById(R.id.name);
			TextView text2 = (TextView)view.findViewById(R.id.latest_message);
			ImageView image = (ImageView)view.findViewById(R.id.todo_image);
			text1.setText(events.get(position));
			view.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view) {
					Intent i = new Intent(context,TodoListDetailActivity.class);
					i.putExtra("todo", events.get(position));
					i.putExtra("todo2",events.get(position));
					context.startActivity(i);
				}
			});

		}else {
			view = convertView;
		}
		return view;
	}
}
