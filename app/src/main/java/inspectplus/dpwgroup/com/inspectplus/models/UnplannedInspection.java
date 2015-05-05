package inspectplus.dpwgroup.com.inspectplus.models;


public class UnplannedInspection extends InspectionEvent {

    public UnplannedInspection() {

    }

    public UnplannedInspection(String projectId, String projectName,
                               String projectLocation, String notes,
                               String projectImage) {
        super(projectId, projectName, projectLocation, notes, projectImage);

    }

    public String toString() {
        return super.toString();
    }

}

