package com.example.book;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class BookDetail extends AppCompatActivity {

    private TextView txtTitle,txtSubTitle,txtAutor,txtPublisher,txtDescription,txtPublisherData;
    private ImageView txtImage;
    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        if (getIntent().hasExtra("Book")){
            mBook = getIntent().getParcelableExtra("Book");
        }
        else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }


        txtTitle=(TextView)findViewById(R.id.tvTitle);
        txtSubTitle=(TextView)findViewById(R.id.tvSubtitle);
        txtAutor=(TextView)findViewById(R.id.tvAuthors);
        txtPublisher=(TextView)findViewById(R.id.tvPublisher);
        txtDescription=(TextView)findViewById(R.id.tvDescription);
        txtPublisherData=(TextView)findViewById(R.id.tvPublishedDate);
        txtImage=(ImageView)findViewById(R.id.imageView);

        txtTitle.setText(mBook.getTitle());
        txtSubTitle.setText(mBook.getSubTitle());
        txtPublisherData.setText(mBook.getPublishedDate());
        txtPublisher.setText(mBook.getPublisher());
        txtDescription.setText(mBook.getDescription());
        txtAutor.setText(mBook.getAuthors());

        Picasso.get()
                .load(mBook.getThumbnail())
                .placeholder(R.drawable.book_open)
                .into(txtImage);


    }
}
