package com.example.gkudva.fsquare.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.example.gkudva.fsquare.Model.Person;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gkudva on 18/07/17.
 */

public class ItemPersonViewModel extends BaseObservable implements ViewModel {

    private Context context;
    private Person person;
    private DateFormat dateFormat;
    private Date date;
    private String reportDate;

    public ItemPersonViewModel(Context context, Person person) {
        this.context = context;
        this.person = person;
        this.dateFormat = new SimpleDateFormat("hh:mm a");
    }

    public String getName()
    {
        return person.getName();
    }

    public int getId()
    {
        return person.getId();
    }

    public String getArrivetime()
    {
        date = new Date(person.getArriveTime() * 1000);
        reportDate = dateFormat.format(date);
        return reportDate +" - ";
    }

    public String getDeparttime()
    {
        date = new Date(person.getLeaveTime() * 1000);
        reportDate = dateFormat.format(date);
        return reportDate;
    }

    public void setPerson(Person person) {
        this.person = person;
        notifyChange();
    }

    @Override
    public void destroy() {
        //In this case destroy doesn't need to do anything because there is not async calls
    }
}
