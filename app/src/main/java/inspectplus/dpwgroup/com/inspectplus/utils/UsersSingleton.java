package inspectplus.dpwgroup.com.inspectplus.utils;

import android.util.Log;

import java.util.ArrayList;

import inspectplus.dpwgroup.com.inspectplus.models.UsersModel;

/**
 * Created by skatgroovey on 06/05/2015.
 */
public class UsersSingleton {
    private static UsersSingleton usersSingleton;
    private ArrayList<UsersModel> users = new ArrayList<UsersModel>();
    private boolean doneWithflag = false;

    public static UsersSingleton getUsersSingleton(){

        if(usersSingleton == null) {
            usersSingleton = new UsersSingleton();
        }
        return usersSingleton;
    }

    public  ArrayList<UsersModel> getUsers(){

        return users;}

    public void setUsers(ArrayList<UsersModel> users) {
        this.users = users;
        flag();

    }

    public boolean flag() {
        doneWithflag = true;
        return  doneWithflag;
    }

    public  static void setUsersSingleton(UsersSingleton usersSingleton){
        UsersSingleton.usersSingleton = usersSingleton;
    }

}
