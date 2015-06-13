package inspectplus.dpwgroup.com.inspectplus.models;

/**
 * Created by skatgroovey on 12/06/2015.
 */
public class UploadsModel {

    private String projectId;
    private String inspectionEventId;
    private String inspectionEntry;

    public UploadsModel() {
    }

    public UploadsModel(String projectId, String inspectionEventId, String inspectionEntry) {
        this.projectId = projectId;
        this.inspectionEventId = inspectionEventId;
        this.inspectionEntry = inspectionEntry;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getInspectionEventId() {
        return inspectionEventId;
    }

    public void setInspectionEventId(String inspectionEventId) {
        this.inspectionEventId = inspectionEventId;
    }

    public String getInspectionEntry() {
        return inspectionEntry;
    }

    public void setInspectionEntry(String inspectionEntry) {
        this.inspectionEntry = inspectionEntry;
    }

    @Override
    public String toString() {
        return "UploadsModel{" +
                "projectId='" + projectId + '\'' +
                ", inspectionEventId='" + inspectionEventId + '\'' +
                ", inspectionEntry='" + inspectionEntry + '\'' +
                '}';
    }
}
