package com.example.bookfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class DisplayBookList extends AppCompatActivity implements BookListAdapter.BookListAdapterOnClickHandler {


    private RecyclerView mRecyclerView;

    private BookListAdapter mBookListAdapter;

    private TextView queryHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_book_list);

        String query=getIntent().getStringExtra("Query");



        queryHeading=(TextView)findViewById(R.id.query);
        queryHeading.setText(query);

        BookListViewModel bookListViewModel= ViewModelProviders.of(this).get(BookListViewModel.class);

        bookListViewModel.getBookList(query).observe(this, new Observer<ArrayList<BookInfo>>() {
            @Override
            public void onChanged(ArrayList<BookInfo> mBookList) {

                    mRecyclerView=(RecyclerView) findViewById(R.id.book_list);

                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DisplayBookList.this,RecyclerView.VERTICAL,false); /////the sumshine example differs here with the package that has been included
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mBookListAdapter=new BookListAdapter(DisplayBookList.this::onClick);
                    mRecyclerView.setAdapter(mBookListAdapter);

                    Log.d("DisplayBookList"," "+mBookList.size());

                    mBookListAdapter.setBooks(mBookList);


            }
        });
    }


    @Override
    public void onClick(BookInfo currentBook) {
        Context context = this;
        Toast.makeText(context, currentBook.volumeInfo.title, Toast.LENGTH_SHORT)
                .show();

        Intent showBookDescription= new Intent(DisplayBookList.this,BookDescription.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("book",(Serializable)currentBook);
        showBookDescription.putExtra("BUNDLE",bundle);

        startActivity(showBookDescription);
    }


}
