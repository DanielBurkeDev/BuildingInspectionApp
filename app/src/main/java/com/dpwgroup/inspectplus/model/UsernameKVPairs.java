package com.dpwgroup.inspectplus.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by skatgroovey on 27/04/2015.
 */
public class UsernameKVPairs {
    private JSONObject json;

    public UsernameKVPairs(JSONObject json) {
        this.json = json;
        userMap(json);
    }

    public HashMap<String, String> userMap(JSONObject json){
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<?> keys = json.keys();
        String returnedString;

        while( keys.hasNext() ){
            String key = (String)keys.next();
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

    private String returnStringFromJSON(JSONObject json) {
        HashMap<String, String> mapResult = new HashMap<String, String>();
        //
        String resultEmail;
        String resultPwd;
        String mapResultString;

        for (int i = 0; i < json.length(); i ++){
            try {
                resultEmail = json.getJSONObject("users").getString("email");
               resultPwd = json.getJSONObject("users").getString("pwd");
               mapResult.put(resultEmail, resultPwd);

                Log.d("kvpmapResult", "" + mapResult.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
      mapResultString = mapResult.toString();

        return mapResultString;
    }


}
