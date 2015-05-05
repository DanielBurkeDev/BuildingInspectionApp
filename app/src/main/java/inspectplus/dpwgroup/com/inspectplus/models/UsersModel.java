package inspectplus.dpwgroup.com.inspectplus.models;

import java.util.Date;

/**
 * Created by skatgroovey on 23/04/2015.
 */
public class UsersModel {

    private int userId;
    private String password;
    private String firstName;
    private String surName;
    private int organisationId;
    private String email;
    private String mobile;
    private String landline;
    private int roleId;
    private Date lastLogin;
    private int failedLoginCount;
    private boolean locked;
    private boolean loggedIn;
    private boolean hasGPSData;
    private String latitude;
    private String longitude;

    public UsersModel() {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLandline() {
        return landline;
    }


    public int getRoleId() {
        return roleId;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(int failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isHasGPSData() {
        return hasGPSData;
    }

    public void setHasGPSData(boolean hasGPSData) {
        this.hasGPSData = hasGPSData;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
