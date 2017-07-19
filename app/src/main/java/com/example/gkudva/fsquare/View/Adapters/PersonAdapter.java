package com.example.gkudva.fsquare.View.Adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.gkudva.fsquare.Model.Person;
import com.example.gkudva.fsquare.R;
import com.example.gkudva.fsquare.ViewModel.ItemPersonViewModel;
import com.example.gkudva.fsquare.databinding.ItemPersonBinding;

import java.util.Collections;
import java.util.List;

/**
 * Created by gkudva on 16/07/17.
 */

final public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<Person> persons;

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

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPersonBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_person,
                parent,
                false);
        return new PersonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.bindPerson(persons.get(position));
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        final ItemPersonBinding binding;

        public PersonViewHolder(ItemPersonBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

        void bindPerson(Person person) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemPersonViewModel(itemView.getContext(), person));
            } else {
                binding.getViewModel().setPerson(person);
            }
        }
    }

}

