package inspectplus.dpwgroup.com.inspectplus.models;

/**
 * Created by johndoe on 01/05/15.
 */
public class Project {
    private String id;
    private String name;
    private String location;

    public Project() {

    }

    public Project(String id, String name, String location) {
        this.id = id;
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

    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nLocation: " + location;
    }
}
