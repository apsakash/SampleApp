package com.example.bookfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class BookDescription extends AppCompatActivity {

    private TextView title;
    private TextView details;
    private TextView description;
    private ImageView bookpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);

        Bundle bundle=(Bundle)getIntent().getBundleExtra("BUNDLE");
        BookInfo book=(BookInfo)bundle.getSerializable("book");

        details=(TextView)findViewById(R.id.details);
        description=(TextView)findViewById(R.id.description);
        bookpic=(ImageView)findViewById(R.id.bookpic);



        details.setText(book.volumeInfo.title+"\n\n"+book.volumeInfo.authors.get(0)+"\n\n"+book.volumeInfo.publisher+"\n\n"+book.volumeInfo.publishedDate+"\n\n");
        //Log.d("BookDescripImageLink",book.volumeInfo.thumbnail);

        description.setText(description.getText().toString()+book.volumeInfo.description);


        details.setMovementMethod(new ScrollingMovementMethod());
        description.setMovementMethod(new ScrollingMovementMethod());

        if(!book.volumeInfo.thumbnail.equals(""))
            new DownloadImageTask(bookpic).execute(book.volumeInfo.thumbnail);

        //bookpic.setImageDrawable(LoadImageFromWebOperations(book.volumeInfo.imagelink));





    }
}
