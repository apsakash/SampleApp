package com.example.bookfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookListAdapterViewHolder> {

    private ArrayList<BookInfo> books;
    private final BookListAdapterOnClickHandler mClickHandler;

    BookListAdapter(BookListAdapterOnClickHandler clickHandler){
        mClickHandler=clickHandler;

    }

    @NonNull
    @Override
    public BookListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new BookListAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapterViewHolder holder, int position) {

        BookInfo currentBook=books.get(position);
        String currentBookTitle = currentBook.volumeInfo.title;
        holder.mBookData.setText(currentBookTitle);

        if(!currentBook.volumeInfo.smallThumbnail.equals(""))
            new DownloadImageTask(holder.mThumbnail).execute(books.get(position).volumeInfo.smallThumbnail);

    }

    @Override
    public int getItemCount() {
        if (books == null)
            return 0;
        return books.size();
    }

    public void setBooks(ArrayList<BookInfo> bookData)
    {
        books=bookData;
        notifyDataSetChanged();
    }

    public interface BookListAdapterOnClickHandler{
        void onClick(BookInfo currentBook);
    }

    public class BookListAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final TextView mBookData;
        public final ImageView mThumbnail;

        BookListAdapterViewHolder(View view) {
            super(view);

            mBookData=(TextView)view.findViewById(R.id.book_data);
            mThumbnail=(ImageView)view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            BookInfo currentBook = books.get(adapterPosition);
            mClickHandler.onClick(currentBook);
        }
    }

}
