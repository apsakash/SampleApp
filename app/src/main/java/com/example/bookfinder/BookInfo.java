package com.example.bookfinder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class BookInfo implements Serializable {

    String id;
    String etag;
    VolumeInfo volumeInfo;


    public void addInfo(JSONObject result) throws JSONException {

        if(!result.isNull("id"))
            id=result.getString("id");
        else
            id="";

        if(!result.isNull("etag"))
            etag=result.getString("etag");
        else
            etag="";

        volumeInfo=new VolumeInfo(result.getJSONObject("volumeInfo"));


    }

}
