package inspectplus.dpwgroup.com.inspectplus.utils;

import java.util.ArrayList;

import inspectplus.dpwgroup.com.inspectplus.models.InspectionEvent;
import inspectplus.dpwgroup.com.inspectplus.models.Project;

/**
 * Created by Barry Dempsey on 30/04/15.
 */
public class EventsSingleton {
    private static EventsSingleton eventsSingleton;
    private ArrayList<InspectionEvent> events = new ArrayList<InspectionEvent>();
    private ArrayList<Project> projects = new ArrayList<Project>();

    public static EventsSingleton getEventsSingleton() {
        if(eventsSingleton == null) {
            eventsSingleton = new EventsSingleton();
        }
        return eventsSingleton;
    }

    public ArrayList<InspectionEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<InspectionEvent> events) {
        this.events = events;
    }

    public static void setEventsSingleton(EventsSingleton eventsSingleton) {
        EventsSingleton.eventsSingleton = eventsSingleton;
    }


    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

}
