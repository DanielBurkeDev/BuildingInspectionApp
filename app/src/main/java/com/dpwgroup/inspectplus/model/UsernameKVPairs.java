package com.dpwgroup.inspectplus.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Iterator;


public class UsernameKVPairs {
    private JSONObject json;
    private static final String TAG_USERS = "users";
    private static final String TAG_USERID = "userId";
    private static final String TAG_FIRSTNAME = "firstName";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PWD = "pwd";

    public UsernameKVPairs(JSONObject json) {
        this.json = json;
        userMap(json);
    }

    public HashMap<String, String> userMap(JSONObject json) {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<?> keys = json.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = null;

            try {
                value = json.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }

           map.put(key, value);

        }
        return map;
    }
//
//    public String returnStringFromJSON(JSONObject json) {
//        HashMap<String, String> mapEmail = new HashMap<String, String>();
//        HashMap<String, String> mapPwd = new HashMap<String, String>();
//        //
//        String resultEmail;
//        String resultPwd;
//        String mapResultString;
//
//        for (int i = 0; i < json.length(); i++) {
//            try {
//                resultEmail = json.getString(TAG_EMAIL);
//                resultPwd = json.getString(TAG_PWD);
//                mapEmail.put(TAG_EMAIL, resultEmail);
//                mapPwd.put(TAG_PWD, resultPwd);
//
//                Log.d("kvpmapResult", "" + mapEmail.toString());
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        mapResultString = mapEmail.toString();
//
//        return mapResultString;
//    }

}
