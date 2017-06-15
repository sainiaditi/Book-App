package com.example.android.bookapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    public static final String LOG_TAG = BookActivity.class.getName();


    private String REQUEST_URL_1 = "https://www.googleapis.com/books/v1/volumes?q=";
    private String REQUEST_URL_2 = "&maxResults=10";
    private BookAdapter adapter;
    private TextView emptyTextView;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        String toText = getIntent().getStringExtra("Query");
        REQUEST_URL_1 += toText;
        REQUEST_URL_1 += REQUEST_URL_2;

        pb = (ProgressBar) findViewById(R.id.progressBar);


        ListView listView = (ListView) findViewById(R.id.ListView);
        adapter = new BookAdapter(this,new ArrayList<Book>());

        emptyTextView = (TextView) findViewById(R.id.emptyTextView);
        listView.setEmptyView(emptyTextView);
        listView.setAdapter(adapter);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();
        Log.e("BookActivity","All ok......");
        if (ni != null && ni.isConnected()) {
            BookAsyncTask task = new BookAsyncTask();
            task.execute(REQUEST_URL_1);
        } else {
            pb.setVisibility(View.GONE);
            emptyTextView.setText("No Internet Connection");
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {


        @Override
        protected List<Book> doInBackground(String... urls) {
            Log.d(LOG_TAG, "doInBackground process started.....");
            if (urls.length < 1 || urls[0] == null)
                return null;
            List<Book> books_list = QueryUtils.fetchBookData(urls[0]);
            if(books_list == null)
                return null;
            else
                return books_list;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            Log.d(LOG_TAG, "onPostExecute process started.....");
            adapter.clear();
            pb = (ProgressBar) findViewById(R.id.progressBar);
            if (books != null && !books.isEmpty()) {
                pb.setVisibility(View.INVISIBLE);
                adapter.addAll(books);
                Log.d(LOG_TAG, "Books added.....");
            }
            else{
                pb.setVisibility(View.INVISIBLE);
                emptyTextView.setText(R.string.noResult);
                emptyTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
