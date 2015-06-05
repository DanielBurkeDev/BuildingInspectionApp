package inspectplus.dpwgroup.com.inspectplus.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import inspectplus.dpwgroup.com.inspectplus.R;
import inspectplus.dpwgroup.com.inspectplus.models.InspectionEventsModel;

/**
 * Created by skatgroovey on 03/06/2015.
 */
public class InspectionListAdapter extends RecyclerView.Adapter<InspectionListAdapter.inspViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ClickListener clickListener;
    private ArrayList<InspectionEventsModel> listInspectionEvents = new ArrayList<>();

    public InspectionListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setListInspectionEvents(ArrayList<InspectionEventsModel> listInspectionEvents) {
        this.listInspectionEvents = listInspectionEvents;
        notifyItemRangeChanged(0, listInspectionEvents.size());
    }

    @Override
    public inspViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_row_inspections, viewGroup, false);
        inspViewHolder holder = new inspViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(inspViewHolder viewHolder, int i) {
        InspectionEventsModel current = listInspectionEvents.get(i);
        viewHolder.inspEventNum.setText(current.getProjectNumber());
        viewHolder.inspEventName.setText(current.getProjectName());
        viewHolder.inspEventCat.setText(current.getInspectionEventCategory());
        viewHolder.buildingElementCat.setText(current.getBuildingElementClassification());

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {

        return listInspectionEvents.size();
    }

    class inspViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView inspEventNum, inspEventName, inspEventCat, buildingElementCat;

        public inspViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            inspEventNum = (TextView) itemView.findViewById(R.id.tv_projnum);
            inspEventName = (TextView) itemView.findViewById(R.id.tv_projname);
            inspEventCat = (TextView) itemView.findViewById(R.id.tv_inspection_event_cat);
            buildingElementCat = (TextView) itemView.findViewById(R.id.tv_building_element_cat);
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
