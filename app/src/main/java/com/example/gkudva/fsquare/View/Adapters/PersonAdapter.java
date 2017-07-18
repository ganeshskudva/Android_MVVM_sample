package com.example.gkudva.fsquare.View.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gkudva.fsquare.Model.Person;
import com.example.gkudva.fsquare.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by gkudva on 16/07/17.
 */

final public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<Person> persons;
    public final static String NO_VISITORS = "No Visitors";

    public PersonAdapter()
    {
        this.persons = Collections.emptyList();
    }

    public PersonAdapter(List<Person> persons)
    {
        this.persons = persons;
    }

    public void setPersonList(List<Person> persons)
    {
        this.persons = persons;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvATime;
        public TextView tvDTime;

        public ViewHolder(View itemView) {

            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvATime = (TextView) itemView.findViewById(R.id.tvATime);
            tvDTime = (TextView) itemView.findViewById(R.id.tvDTime);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder holder, int position) {
        Person person = persons.get(position);
        boolean setDimColor = false;
        DateFormat df = new SimpleDateFormat("hh:mm a");
        Date date;

        if (person.getName().contains(NO_VISITORS))
        {
            setDimColor = true;
        }
        holder.tvName.setText(person.getName());

        date = new Date(person.getArriveTime() * 1000);
        String reportDate = df.format(date);
        holder.tvATime.setText(reportDate +" - ");

        date = new Date(person.getLeaveTime() * 1000);
        reportDate = df.format(date);
        holder.tvDTime.setText(reportDate);

        if (setDimColor)
        {
            holder.tvName.setTextColor(Color.LTGRAY);
            holder.tvATime.setTextColor(Color.LTGRAY);
            holder.tvDTime.setTextColor(Color.LTGRAY);
        }
        else
        {
            holder.tvName.setTextColor(Color.BLACK);
            holder.tvATime.setTextColor(Color.BLACK);
            holder.tvDTime.setTextColor(Color.BLACK);
        }

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}

