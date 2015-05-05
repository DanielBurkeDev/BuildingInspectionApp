package inspectplus.dpwgroup.com.inspectplus.models;

/**
 * Created by Windows 8 on 30/04/2015.
 * model class that looks after the recording of
 * a site visit regarding a specific building element
 */

public class BuildingElement {
    private String id;
    private String name;
    private String location;
    private String notes;

    public BuildingElement(String id, String name, String location, String notes){
        this.id = id;
        this.name = name;
        this.location = location;
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BuildingElement() {

    }

    public String toString() {
        return "ID: " + id + " Element Name: " + name + " Location: " + location + " Notes: " + notes;
    }
}
