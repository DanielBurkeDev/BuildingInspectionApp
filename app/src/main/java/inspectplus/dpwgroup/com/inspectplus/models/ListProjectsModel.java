package inspectplus.dpwgroup.com.inspectplus.models;


/**
 * Created by skatgroovey on 31/05/2015.
 */
public class ListProjectsModel {
    private String projectId;
    private String projectNumber;
    private String projectName;
    private String projectLocation;
    private String projectDescription;
    private String roleDescription;

    public ListProjectsModel() {
    }

    public ListProjectsModel(String projectId, String projectName,String projectNumber, String projectLocation, String projectDescription, String roleDescription) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectLocation = projectLocation;
        this.projectDescription = projectDescription;
        this.roleDescription = roleDescription;
        this.projectNumber = projectNumber;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    @Override
    public String toString() {
        return "ListProjectsModel{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectLocation='" + projectLocation + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                '}';
    }
}
