package inspectplus.dpwgroup.com.inspectplus.utils;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.activities.TodoListActivity;
import inspectplus.dpwgroup.com.inspectplus.models.ListProjectsModel;

/**
 * Created by skatgroovey on 01/06/2015.
 */
public class projListAdapter extends RecyclerView.Adapter<projListAdapter.myViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ClickListener clickListener;
    private ArrayList<ListProjectsModel> listProjects = new ArrayList<>();

    public projListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setProjectsList(ArrayList<ListProjectsModel> listProjects) {
        this.listProjects = listProjects;
        notifyItemRangeChanged(0, listProjects.size());
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder viewHolder, int i) {
        ListProjectsModel current = listProjects.get(i);
        viewHolder.projNum.setText(current.getProjectNumber());
        viewHolder.projName.setText(current.getProjectName());
        viewHolder.projLoc.setText(current.getProjectLocation());

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {

        return listProjects.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView projNum, projName, projLoc;

        public myViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            projNum = (TextView) itemView.findViewById(R.id.tv_projnum);
            projName = (TextView) itemView.findViewById(R.id.tv_projname);
            projLoc = (TextView) itemView.findViewById(R.id.tv_projloc);
        }


        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
                Log.d("position", "" + getPosition());
            }

        }


    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}
