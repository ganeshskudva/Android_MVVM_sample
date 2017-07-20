package com.example.gkudva.fsquare.Utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.gkudva.fsquare.Model.PeopleHereJsonResponse;
import com.example.gkudva.fsquare.Model.Person;
import com.example.gkudva.fsquare.Model.Venue;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by gkudva on 16/07/17.
 */

public class GetResponse extends AsyncTask<Void, Void, Venue> implements Utils{

    private Context mContext;
    private Venue venueFull;
    private List<Person> tempList;
    private List<Person> personList;
    private AsyncResponse delegate;
    public final static String NO_VISITORS = "No Visitors";
    public final static String INPUT_JSON_FILENAME = "people.json";

    public GetResponse(Context mContext, AsyncResponse mResponse) {
        this.mContext = mContext;
        this.delegate = mResponse;
        this.tempList = Collections.emptyList();
        this.personList = new ArrayList<Person>();
    }

    public interface AsyncResponse {
        void processFinish(List<Person> personList);
    }

    @Override
    protected Venue doInBackground(Void... params) {
        try {
            InputStream is = mContext.getAssets().open(INPUT_JSON_FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(is);

            PeopleHereJsonResponse response = new Gson().fromJson(inputStreamReader, PeopleHereJsonResponse.class);
            return response.getVenue();
        } catch (Exception e) {}

        return null;
    }

    @Override
    protected void onPostExecute(Venue venue) {
        this.venueFull = venue;
        sortPeople();

        if (delegate != null)
        {
            delegate.processFinish(personList);
        }
    }

    public void sortPeople() {
        //get venue start time
        long startTime = venueFull.getOpenTime();
        //get venue close time
        long closeTime = venueFull.getCloseTime();

        tempList = venueFull.getVisitors();

        /*
         * Sort the list of customers based on their arrival times in non-decreasing order
         */
        Collections.sort(tempList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                if (p1.getArriveTime() < p2.getArriveTime()) {
                    return -1;
                } else if (p1.getArriveTime() > p2.getArriveTime()) {
                    return 1;
                } else {
                    /*
                     * If Arrival times of both customers the same,
                     * then sort based on their Leave Time. The customer
                     * who stays for the shortest period of time comes ahead.
                     */
                    if (p1.getLeaveTime() < p2.getLeaveTime())
                    {
                        return -1;
                    }
                    else if (p1.getLeaveTime() > p2.getLeaveTime())
                    {
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
            }
        });

        /*
         * Once the list of customers is sorted iterate over the list to calculate idle time
         * To begin with, startTime venue open time
         */
        for (Person person: tempList)
        {
            /*
             * If startTime is less than the incoming customer's startTime
             * then insert a dummy Person into the Arraylist. This dummy Person
             * is to show the "No Visitors" card in the Recycler View
             */
            if (startTime < person.getArriveTime())
            {
                // The place is idle until the ArriveTime of this person
                Person psn = new Person(NO_VISITORS, startTime, person.getArriveTime());
                personList.add(psn);
            }
            personList.add(person);

            /*
             * Update startTime to the LeaveTime of the customer
             * who stays for longer time than the previous customer
             */
            if (person.getLeaveTime() > startTime) {
                startTime = person.getLeaveTime();
            }
        }

        if (startTime < closeTime)
        {
            Person psn = new Person(NO_VISITORS, startTime, closeTime);
            personList.add(psn);
        }
    }

    @Override
    public void destroy()
    {
        mContext = null;
        delegate = null;
        venueFull = null;
    }

}
