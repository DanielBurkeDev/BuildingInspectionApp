package inspectplus.dpwgroup.com.inspectplus.models;

import java.util.Date;

/**
 * Created by johndoe on 01/05/15.
 */
public class Project {
    private String id;
    private String name;
    private String projectName;
    private String location;
    private double estimatedValue;
    private String projectAdmin;
    private Date creationDate;
    private Date changeDate;


    public Project() {

    }

    public Project(String id, String name, String projectName,
                   String location, double estimatedValue,
                   String projectAdmin, Date creationDate,
                   Date changeDate) {
        this.id = id;
        this.name = name;
        this.projectName = projectName;
        this.location = location;
        this.estimatedValue = estimatedValue;
        this.projectAdmin = projectAdmin;
        this.creationDate = creationDate;
        this.changeDate = changeDate;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public String getProjectAdmin() {
        return projectAdmin;
    }

    public void setProjectAdmin(String projectAdmin) {
        this.projectAdmin = projectAdmin;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nLocation: " + location + " Project Name: " + projectName +
                "Project Admin: " + projectAdmin + " Estimated Value: " + estimatedValue + " Date Created: " + creationDate +
                "Change Date: " + changeDate;
    }
}
