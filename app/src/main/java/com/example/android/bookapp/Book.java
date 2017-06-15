package com.example.android.bookapp;

import android.media.Image;
import android.provider.ContactsContract;

import java.security.PublicKey;

/**
 * Created by Dell on 5/29/2017.
 */

public class Book {
    private String Title;
    private String Author;

    public Book(String title, String author){
        Title = title;
        Author = author;
    }

    public String getTitle(){
        return Title;
    }

    public String getAuthor(){
        return Author;
    }
}
