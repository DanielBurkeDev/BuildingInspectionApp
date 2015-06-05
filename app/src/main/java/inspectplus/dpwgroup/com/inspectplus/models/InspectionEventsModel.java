package inspectplus.dpwgroup.com.inspectplus.models;

/**
 * Created by skatgroovey on 05/05/2015.
 */
public class InspectionEventsModel {
    private String assignedCertifier;
    private String assignedCertifierCompany;
    private String projectId;
    private String ancilliaryCertifier;
    private String ancilliaryCompany;
    private String projectNumber;
    private String projectName;
    private String inspectionPlanReferenceNumber;
    private String inspectionEventNumber;
    private String inspectionEventCategory;
    private String buildingElementClassification;
    private String responseActionOriginator;
    private String mediaId;
    private String scheduledDate;

    public InspectionEventsModel() {
    }

    public InspectionEventsModel(String assignedCertifier, String assignedCertifierCompany, String projectId, String ancilliaryCertifier,
                            String ancilliaryCompany, String projectNumber, String projectName, String inspectionPlanReferenceNumber,
                            String inspectionEventNumber, String inspectionEventCategory, String buildingElementClassification,
                            String responseActionOriginator, String mediaId, String scheduledDate) {
        this.assignedCertifier = assignedCertifier;
        this.assignedCertifierCompany = assignedCertifierCompany;
        this.projectId = projectId;
        this.ancilliaryCertifier = ancilliaryCertifier;
        this.ancilliaryCompany = ancilliaryCompany;
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.inspectionPlanReferenceNumber = inspectionPlanReferenceNumber;
        this.inspectionEventNumber = inspectionEventNumber;
        this.inspectionEventCategory = inspectionEventCategory;
        this.buildingElementClassification = buildingElementClassification;
        this.responseActionOriginator = responseActionOriginator;
        this.mediaId = mediaId;
        this.scheduledDate = scheduledDate;
    }

    public String getAssignedCertifier() {
        return assignedCertifier;
    }

    public void setAssignedCertifier(String assignedCertifier) {
        this.assignedCertifier = assignedCertifier;
    }

    public String getAssignedCertifierCompany() {
        return assignedCertifierCompany;
    }

    public void setAssignedCertifierCompany(String assignedCertifierCompany) {
        this.assignedCertifierCompany = assignedCertifierCompany;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAncilliaryCertifier() {
        return ancilliaryCertifier;
    }

    public void setAncilliaryCertifier(String ancilliaryCertifier) {
        this.ancilliaryCertifier = ancilliaryCertifier;
    }

    public String getAncilliaryCompany() {
        return ancilliaryCompany;
    }

    public void setAncilliaryCompany(String ancilliaryCompany) {
        this.ancilliaryCompany = ancilliaryCompany;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInspectionPlanReferenceNumber() {
        return inspectionPlanReferenceNumber;
    }

    public void setInspectionPlanReferenceNumber(String inspectionPlanReferenceNumber) {
        this.inspectionPlanReferenceNumber = inspectionPlanReferenceNumber;
    }

    public String getInspectionEventNumber() {
        return inspectionEventNumber;
    }

    public void setInspectionEventNumber(String inspectionEventNumber) {
        this.inspectionEventNumber = inspectionEventNumber;
    }

    public String getInspectionEventCategory() {
        return inspectionEventCategory;
    }

    public void setInspectionEventCategory(String inspectionEventCategory) {
        this.inspectionEventCategory = inspectionEventCategory;
    }

    public String getBuildingElementClassification() {
        return buildingElementClassification;
    }

    public void setBuildingElementClassification(String buildingElementClassification) {
        this.buildingElementClassification = buildingElementClassification;
    }

    public String getResponseActionOriginator() {
        return responseActionOriginator;
    }

    public void setResponseActionOriginator(String responseActionOriginator) {
        this.responseActionOriginator = responseActionOriginator;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    @Override
    public String toString() {
        return "InspectionEvents{" +
                "assignedCertifier='" + assignedCertifier + '\'' +
                ", assignedCertifierCompany='" + assignedCertifierCompany + '\'' +
                ", projectId='" + projectId + '\'' +
                ", ancilliaryCertifier='" + ancilliaryCertifier + '\'' +
                ", ancilliaryCompany='" + ancilliaryCompany + '\'' +
                ", projectNumber='" + projectNumber + '\'' +
                ", projectName='" + projectName + '\'' +
                ", inspectionPlanReferenceNumber='" + inspectionPlanReferenceNumber + '\'' +
                ", inspectionEventNumber='" + inspectionEventNumber + '\'' +
                ", inspectionEventCategory='" + inspectionEventCategory + '\'' +
                ", buildingElementClassification='" + buildingElementClassification + '\'' +
                ", responseActionOriginator='" + responseActionOriginator + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", scheduledDate='" + scheduledDate + '\'' +
                '}';
    }
}
