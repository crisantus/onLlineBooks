package com.example.book;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ProgressBar mLoadingProgress;
    private RecyclerView rvBooks;
    private URL mBookUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        rvBooks = (RecyclerView) findViewById(R.id.rv_books);
        mLoadingProgress = (ProgressBar) findViewById(R.id.pb_loading);
        try {
            mBookUrl = ApiUtil.buildUrl("cooking");
            new BooksQueryTask().execute(mBookUrl);
        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        //create the layoutManager for the books (linear in this case, scrolling vertically
        LinearLayoutManager booksLayoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvBooks.setLayoutManager(booksLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        final MenuItem searchItem=menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_advanceSearch:
                Intent intent=new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
             default:
                 int position = item.getItemId() + 1 ;
                 String preferenceName = spUtil.QUERY + String.valueOf(position);
                 String query = spUtil.getPreferenceString(getApplicationContext(), preferenceName);
                 String[] prefParams = query.split("\\,");
                 String[] queryParams = new String[4];

                 for (int i=0; i<prefParams.length;i++) {
                     queryParams[i] = prefParams[i];
                 }


                 mBookUrl = ApiUtil.buildUrl(
                         (queryParams[0] == null)?"" : queryParams[0],
                         (queryParams[1] == null)?"" : queryParams[1],
                         (queryParams[2] == null)?"" : queryParams[2],
                         (queryParams[3] == null)?"" : queryParams[3]
                 );
                 new BooksQueryTask().execute(mBookUrl);
                 return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            URL bookUrl = ApiUtil.buildUrl(query);
            new BooksQueryTask().execute(bookUrl);
        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    public class BooksQueryTask extends AsyncTask<URL, Void, String> {

        @Override//this where u perform the networking Task, in the backGround
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try {
                result = ApiUtil.getJson(searchURL);
            }
            catch (IOException e) {
                Log.e("Error", e.getMessage());
            }
            return result;
        }

        @Override//called when the doing background work is completed,u can pass the result of the longRunningTask to the UI
        protected void onPostExecute(String result) {
            TextView tvError = (TextView) findViewById(R.id.tv_error);
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null) {
                rvBooks.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }
            else {
                rvBooks.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }
            ArrayList<Book> books = ApiUtil.getBooksFromJson(result);
            String resultString = "";

            BooksAdapter adapter = new BooksAdapter(books);
            rvBooks.setAdapter(adapter);
        }

        @Override//called on the UI thread before the task is execute
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
