package com.example.androidlabs;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class parseXmlAsync extends AsyncTask<String,String,String> {

    @SuppressLint("RestrictedApi")
    @Override
    protected String doInBackground(String... strings) {

        try {


            // initialize our input source variable
            InputSource inputSource = null;

            // XML from URL

                // specify a URL
                // make sure you are connected to the internet
                URL url = new URL(
                        "http://demo.codeofaninja.com/AndroidXml/sample.xml");
                inputSource = new InputSource(url.openStream());




            // instantiate SAX parser
            SAXParserFactory saxParserFactory = SAXParserFactory
                    .newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            // get the XML reader
            XMLReader xmlReader = saxParser.getXMLReader();

            // prepare and set the XML content or data handler before
            // parsing
            XmlContentHandler xmlContentHandler = new XmlContentHandler();
            xmlReader.setContentHandler(xmlContentHandler);

            // parse the XML input source
            xmlReader.parse(inputSource);

            // put the parsed data to a List
            List<ParsedDataSet> parsedDataSet = xmlContentHandler
                    .getParsedData();

            // we'll use an iterator so we can loop through the data
            Iterator<ParsedDataSet> i = parsedDataSet.iterator();
            ParsedDataSet dataItem;

            while (i.hasNext()) {

                dataItem = (ParsedDataSet) i.next();

                /*
                 * parentTag can also represent the main type of data, in
                 * our example, "Owners" and "Dogs"
                 */
                String parentTag = dataItem.getParentTag();
                Log.v(LOG_TAG, "parentTag: " + parentTag);

                if (parentTag.equals("Owners")) {
                    Log.v(LOG_TAG, "Name: " + dataItem.getName());
                    Log.v(LOG_TAG, "Age: " + dataItem.getAge());
                    Log.v(LOG_TAG,
                            "EmailAddress: " + dataItem.getEmailAddress());
                }

                else if (parentTag.equals("Dogs")) {
                    Log.v(LOG_TAG, "Name: " + dataItem.getName());
                    Log.v(LOG_TAG, "Birthday: " + dataItem.getBirthday());
                }

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String lengthOfFile) {
        // your do stuff after parsing the XML
    }
}


