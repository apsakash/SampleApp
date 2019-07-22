package com.example.bookfinder;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

class VolumeInfo implements Serializable {

    String title;
    ArrayList<String> authors;
    String publisher;
    String publishedDate;
    String description;
    String smallThumbnail;
    String thumbnail;
    Bitmap smallThumbnailImage;
    VolumeInfo(JSONObject input) throws JSONException{


        if(!input.isNull("title"))
            title=input.getString("title");
        else
            title="Not available";

        if(!input.isNull("publisher"))
            publisher=input.getString("publisher");
        else
            publisher="Not available";

        if(!input.isNull("publishedDate"))
            publishedDate=input.getString("publishedDate");
        else
            publishedDate="Not available";

        if(!input.isNull("description"))
            description=input.getString("description");
        else
            description="Not available";

        if(!input.isNull("imageLinks")){
            JSONObject imageLinks=input.getJSONObject("imageLinks");

            if(!imageLinks.isNull("smallThumbnail"))
            {
                smallThumbnail=imageLinks.getString("smallThumbnail");

            }

            else
                smallThumbnail="";

            if(!imageLinks.isNull("thumbnail"))
                thumbnail=imageLinks.getString("thumbnail");
            else
                thumbnail="";

        }
        else
        {
            smallThumbnail="";
            thumbnail="";
        }



        JSONArray authorList= null;
        authors=new ArrayList<>();

        if(!input.isNull("authors")) {
            authorList = input.getJSONArray("authors");
            for(int i=0;i<authorList.length();i++) {
                authors.add(authorList.get(i).toString());
            }
        }

        else {
            authorList = null;
            authors.add("Not available");
        }




    }
}
