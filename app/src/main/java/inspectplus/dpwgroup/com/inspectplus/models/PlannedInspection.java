package inspectplus.dpwgroup.com.inspectplus.models;

public class PlannedInspection extends InspectionEvent {
    public String scheduledtime;

    public PlannedInspection() {

    }

    public PlannedInspection(String projectId, String projectName,
                             String projectLocation, String notes,
                             String projectImage, String scheduledTime) {
        super(projectId, projectName,projectLocation, notes, projectImage);
        this.scheduledtime = scheduledTime;
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    public String getScheduledtime() {
        return scheduledtime;
    }

    public void setScheduledtime(String scheduledtime) {
        this.scheduledtime = scheduledtime;
    }

    public String toString() {
        return super.toString() + "Scheduled Time:  " + scheduledtime;
    }

}








