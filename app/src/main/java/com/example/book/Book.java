package com.example.book;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Book implements Parcelable {
    public String id;
    public String title;
    public String subTitle;
    public String authors;
    public String publisher;
    public String publishedDate;
    public String description;
    public String thumbnail;

    public Book(String id, String title, String subTitle, String[] authors, String publisher,
                String publishedDate,String description,String thumbnail) {

        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.authors = (TextUtils.join(", ", authors));
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description=description;
        this.thumbnail=thumbnail;
    }

    public Book() {
    }

    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = (TextUtils.join(", ", authors));
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Creator<Book> getCREATOR() {
        return CREATOR;
    }

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        authors = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        description=in.readString();
        thumbnail=in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(authors);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(description);
        dest.writeString(thumbnail);
    }
//    public static void loadImage(ImageView view, String imageUrl) {
//
//        Picasso.get()
//                .load(imageUrl)
//                .placeholder(R.drawable.book_open)
//                .into(view);
//    }
}
