package com.example.bookfinder;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<BookInfo>> mBookList=new MutableLiveData<>();

    //Probable place of an error that live data cannot be setValued in a background thread

    private void loadData(URL url) {

        new AsyncTask<URL, Void, ArrayList<BookInfo>>() {


            @Override
            protected ArrayList<BookInfo> doInBackground(URL... urls) {

                Log.d("QueryTask","Came inside doInBackground");
                URL searchUrl=urls[0];
                String searchResults=null;

                try{
                    searchResults=URLBuilder.getResponseFromHttpUrl(searchUrl);

                }catch (IOException e){
                    e.printStackTrace();
                }

                ArrayList<BookInfo> books=new ArrayList<>();

                if(searchResults!=null && !searchResults.equals(""))
                {
                    Log.d("MainActivity",searchResults);

                    try{
                        JSONObject jsonObject=new JSONObject(searchResults);

                        JSONArray items=jsonObject.getJSONArray("items");

                        for(int i=0;i<items.length();i++)
                        {
                            BookInfo b=new BookInfo();
                            b.addInfo((JSONObject)items.get(i));
                            books.add(b);
                        }

                        for(int i=0;i<books.size();i++) {
                            Log.d("QueryTask", books.get(i).volumeInfo.title);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                return books;
            }

            @Override
            protected void onPostExecute(ArrayList<BookInfo> bookInfos) {
                mBookList.setValue(bookInfos);
            }
        }.execute(url);

    }

    public LiveData<ArrayList<BookInfo>> getBookList(String searchQuery)
    {
        URL url = URLBuilder.buildUrl(searchQuery);
        loadData(url);
        return mBookList;

    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}



