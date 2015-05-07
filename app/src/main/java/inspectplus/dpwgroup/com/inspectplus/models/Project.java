package inspectplus.dpwgroup.com.inspectplus.models;

/**
 * Created by johndoe on 01/05/15.
 */
public class Project {
    private String projectId;
    private String name;
    private String location;
    private String projectNumber;

    public Project() {

    }

    public Project(String id, String name, String location) {
        this.projectId = id;
        this.name = name;
        this.location = location;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return projectId;
    }

    public void setId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String toString() {
        return "ID: " + projectId + "\nName: " + name + "\nLocation: " + location;
    }
}
