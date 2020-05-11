package com.example.book;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {

    private ApiUtil() {
    }

    public static final String BASE_API_URL =
            "https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY = "q";//query
    public static final String KEY = "key";
    public static final String API_KEY = "AIzaSyCK9Z5fuQBLM0RXV58u1Wmkt9zznb0269c";
    public static final String TITLE = "intitle:";
    public static final String AUTHOR= "inauthor:";
    public static final String PUBLISHER = "inpublisher:";
    public static final String ISBN = "isbn:";

    public static URL buildUrl(String title) {
        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, title)
                .appendQueryParameter(KEY, API_KEY)
                .build();
        try {
            //convert URL to url
            url = new URL(uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrl(String title, String author, String publisher, String isbn) {
        URL url = null;
        StringBuilder sb = new StringBuilder();

        if (!title.isEmpty()) sb.append(TITLE + title + "+");
        if (!author.isEmpty())  sb.append(AUTHOR+ author + "+");
        if (!publisher.isEmpty())  sb.append(PUBLISHER + publisher + "+");
        if (!isbn.isEmpty())  sb.append(ISBN + isbn + "+");
        //removes the last character
        sb.setLength(sb.length() - 1);
        String query = sb.toString();
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, query)
                .appendQueryParameter(KEY, API_KEY)
                .build();
        try {
            url = new URL(uri.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
        // your not allowed to perform any networking on the mainThread
    public static String getJson(URL url) throws IOException {
        //setup connection to api
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            //inputsStream allows us to read data
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();//hasNext is use to check if we have data
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }


    public static ArrayList<Book> getBooksFromJson(String json) {
        //constant we want to retrieve from jsons
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUMEINFO = "volumeInfo";
        final String DESCRIPTION ="description";
        final String IMAGEINFO = "imageLinks";
        final String THUMBNAIL = "thumbnail";


        ArrayList<Book> books = new ArrayList<Book>();
        try {
            JSONObject jsonBooks = new JSONObject(json);
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);//array that contains all the books
            int numberOfBooks = arrayBooks.length();//how many  books we have

            for (int i = 0; i < numberOfBooks; i++) {
                JSONObject bookJSON = arrayBooks.getJSONObject(i);//here its getting all those json items one after the other
                JSONObject volumeInfoJSON =
                        bookJSON.getJSONObject(VOLUMEINFO);
                JSONObject imageLinksJSON = volumeInfoJSON.getJSONObject(IMAGEINFO);
                int authorNum = volumeInfoJSON.getJSONArray(AUTHORS).length();
                String[] authors = new String[authorNum];
                for (int j = 0; j < authorNum; j++) {
                    authors[j] = volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();
                }

                Book book=new Book();
                book.setId(bookJSON.getString(ID));
                book.setAuthors(authors);
                book.setPublishedDate(volumeInfoJSON.getString(PUBLISHED_DATE));
                book.setPublisher(volumeInfoJSON.getString(PUBLISHER));
                book.setSubTitle((volumeInfoJSON.isNull(SUBTITLE) ? "" : volumeInfoJSON.getString(SUBTITLE)));
                book.setTitle(volumeInfoJSON.getString(TITLE));
                book.setDescription(volumeInfoJSON.getString(DESCRIPTION));
                book.setThumbnail(imageLinksJSON.getString(THUMBNAIL));
                books.add(book);
//               Book book = new Book(
//                        bookJSON.getString(ID),
//                       volumeInfoJSON.getString(TITLE),
//                       (volumeInfoJSON.isNull(SUBTITLE) ? "" : volumeInfoJSON.getString(SUBTITLE)),
//                       authors,
//                        volumeInfoJSON.getString(PUBLISHER),
//                       volumeInfoJSON.getString(DESCRIPTION),
//                       volumeInfoJSON.getString(PUBLISHED_DATE));
//               books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

/*
Short Note:
JSONObject is use to store objects from json.. locate it with its name from the json, getJSONArray()
JSONArray is use to store Arrays from json.. locate it with its name from the json,getJSONObject()
 */

}
