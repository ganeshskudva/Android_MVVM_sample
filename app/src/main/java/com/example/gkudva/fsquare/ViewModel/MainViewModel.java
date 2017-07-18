package com.example.gkudva.fsquare.ViewModel;

import android.content.Context;

import com.example.gkudva.fsquare.Model.Person;
import com.example.gkudva.fsquare.Utils.GetResponse;

import java.util.List;

/**
 * Created by gkudva on 16/07/17.
 */

public class MainViewModel implements ViewModel, GetResponse.AsyncResponse{

    public Context context;
    public DataListener dataListener;
    public List<Person> personList;
    public GetResponse response;

    public MainViewModel(Context context, DataListener dataListener) {
        this.context = context;
        this.dataListener = dataListener;
        response = new GetResponse(context, this);
    }

    public interface DataListener {
        void onPersonListChanged(List<Person> personList);
    }

    @Override
    public void processFinish(List <Person> personList)
    {
        if (dataListener != null && (personList != null))
            dataListener.onPersonListChanged(personList);
    }

    @Override
    public void destroy() {
        context = null;
        dataListener = null;
        response.destroy();
    }

    public void getResponse()
    {
        response.execute();
    }


}
