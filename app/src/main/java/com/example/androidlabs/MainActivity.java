package com.example.androidlabs;

import org.xmlpull.v1.XmlPullParserException;
import static java.lang.System.in;
import java.io.IOException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;

    private ArrayList<String> favourites = new ArrayList<>(Arrays.asList());

    private ArrayList<String> newsList = new ArrayList<>(Arrays.asList());
    private MyListAdapter myAdapter;
    private ListView lvNews;


    private int i;
    private View newView;
    private TextView row, old;
    private LinearLayout parent;
    private Button addButton;

    private  AlertDialog.Builder alertDialogBuilder;

    private AlertDialog.Builder builder;
    private View view1;

    private EditText input;


    private int listAdapterCount;
    private int position;

//    private ListView temp;

    private TextView temp;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        // setContentView loads objects onto the screen.
        // Before this function, the screen is empty.
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temp);

//        try {
//
//            // parse our XML
//            new parseXmlAsync().execute();
//
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



        MyHTTPRequest req = new MyHTTPRequest();
        req.execute("https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");  //Type 1

//        lvNews = findViewById(R.id.lvNews);

     //   input = findViewById(R.id.etTypeHere);




        Button btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener( click -> {

            String itemText = input.getText().toString();

            // replace input.getText() with the item that you click on to add a favourite to the favourites list.

            favourites.add(itemText);
            myAdapter.notifyDataSetChanged();
        });

        //this part works when I have no xml async task
        ListView myList = findViewById(R.id.lvNews);

        myList.setAdapter( myAdapter = new MyListAdapter());

        myList.setOnItemLongClickListener( (parent, view, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message:
                    .setMessage("The selected row is: "+pos)

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        favourites.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { })

                    //You can add extra layout elements:
                    .setView(getLayoutInflater().inflate(R.layout.row_layout, null) )

                    //Show the dialog
                    .create().show();
            return true;
        });

    }


    //Type1     Type2   Type3
    private class MyHTTPRequest extends AsyncTask< String, Integer, String>
    {
        //Type3                Type1
        public String doInBackground(String ... args) {
        try {

            publishProgress(25);
            publishProgress(50);
            publishProgress(75);

            //create a URL object of what server to contact:
            URL url = new URL(args[0]);

            //open the connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //wait for data:
            InputStream response = urlConnection.getInputStream();


            //From part 3: slide 19
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(response, "UTF-8");



            // put xml parsing here....
            // use youtube video https://www.youtube.com/watch?v=-deKKeEdpbw   starting at time 8:02



        }
        catch (Exception e) {
            return "Exception occurred while parsing XML call. " + e.toString();
        }

        return "XML call Done";
    }

    //Type 2
    public void onProgressUpdate(Integer... args) {

    }

    //Type3
    public void onPostExecute(String fromDoInBackground) {
            // add to list view
       temp.setText(fromDoInBackground);

//        temp.setAdapter(itemsAdapter);
        //or
//        temp.setAdapter(myAdapter);



        Log.i("HTTP", fromDoInBackground);

    }


    }



    private class MyListAdapter extends BaseAdapter{

        public int getCount() { return favourites.size();}

        public Object getItem(int position) { return favourites.get(position); }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            ListView myList = findViewById(R.id.lvNews);

            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            View newView = inflater.inflate(R.layout.row_layout, parent, false);

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textGoesHere);
            tView.setText( getItem(position).toString() );

            //return it to be put in the table
            return newView;
        }
    }

}

