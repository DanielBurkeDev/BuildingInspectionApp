package inspectplus.dpwgroup.com.inspectplus.models;

import java.util.Date;

/**
 * Created by skatgroovey on 23/04/2015.
 */
public class UsersModel {

    private String userId;
    private String password;
    private String firstName;
    private String surName;
    private String organisationId;
    private String email;
    private String mobile;
    private String landline;
    private String roleId;
    private String lastLogin;
    private String failedLoginCount;
    private String locked;
    private String loggedIn;
    private String hasGPSData;
    private String latitude;
    private String longitude;

    public String getUserId() {
        return userId;
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

    public String getOrganisationId() {
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

    public String getRoleId() {
        return roleId;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public String getFailedLoginCount() {
        return failedLoginCount;
    }

    public String getLocked() {
        return locked;
    }

    public String getLoggedIn() {
        return loggedIn;
    }

    public String getHasGPSData() {
        return hasGPSData;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
