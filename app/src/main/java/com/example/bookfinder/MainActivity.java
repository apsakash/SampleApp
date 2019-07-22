package com.example.bookfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    EditText searchBox;
    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox=(EditText)(findViewById(R.id.searchBox));
        searchButton=(Button)(findViewById(R.id.searchButton));

        searchBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    makeSearchQuery();

                }
                return false;
            }
        });

        searchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){

                Log.d("MainActivity","Search Button Clciked");
                makeSearchQuery();


            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null)
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void makeSearchQuery() {

        Intent showBookList= new Intent(MainActivity.this,DisplayBookList.class);
        showBookList.putExtra("Query",searchBox.getText().toString());
        startActivity(showBookList);

    }

    public class QueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            Log.d("QueryTask","Came inside doInBackground");
            URL searchUrl=urls[0];
            String searchResults=null;

            try{
                searchResults=URLBuilder.getResponseFromHttpUrl(searchUrl);

            }catch (IOException e){
                e.printStackTrace();
            }

            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<BookInfo> books=new ArrayList<BookInfo>();

            if(s!=null && !s.equals(""))
            {
                Log.d("MainActivity",s);

                try{
                    JSONObject jsonObject=new JSONObject(s);

                    JSONArray items=jsonObject.getJSONArray("items");

                    for(int i=0;i<items.length();i++)
                    {
                        BookInfo b=new BookInfo();
                        b.addInfo((JSONObject)items.get(i));
                        books.add(b);
                    }

                    for(int i=0;i<books.size();i++)
                    {
                        Log.d("QueryTask",books.get(i).volumeInfo.title);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }
}
