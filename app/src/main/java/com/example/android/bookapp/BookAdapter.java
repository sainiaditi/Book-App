package com.example.android.bookapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 5/29/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context,ArrayList<Book> book){
        super(context,0,book);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
        }
        Book currentBook = getItem(position);

        TextView currentTitle = (TextView) listItemView.findViewById(R.id.title);
        currentTitle.setText(currentBook.getTitle());

        TextView authors = (TextView) listItemView.findViewById(R.id.author);
        authors.setText(currentBook.getAuthor());

        return listItemView;
    }
}
