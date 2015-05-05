package inspectplus.dpwgroup.com.inspectplus.models;

public class InspectionEvent {
    private String id;
    private String name;
    private String location;
    private String image;
    private String notes;
    private BuildingElement element;

    public InspectionEvent() {

    }

    public InspectionEvent(String projectId, String projectName, String projectLocation,
                           String projectNotes, String projectImage) {
        id = projectId;
        name = projectName;
        location = projectLocation;
        notes = projectNotes;
        image = projectImage;
        element = new BuildingElement();
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getId() {

        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {

        return location;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;

    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String toString() {
        if(notes == null || image == null) {
            notes = "No info yet";
            image = "No info yet";
        }
        return  "ID: " + id + "\nInspection Name: " + name + "\nLocation: " +
                location + "\nImage Ref: " + image + "\nNotes: " + notes;
    }
}






