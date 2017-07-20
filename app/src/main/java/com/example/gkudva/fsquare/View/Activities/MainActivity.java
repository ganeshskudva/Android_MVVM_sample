package com.example.gkudva.fsquare.View.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.gkudva.fsquare.Model.Person;
import com.example.gkudva.fsquare.R;
import com.example.gkudva.fsquare.View.Adapters.PersonAdapter;
import com.example.gkudva.fsquare.ViewModel.MainViewModel;
import com.example.gkudva.fsquare.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener{

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private RecyclerView.LayoutManager layoutManager;

    public final static String LIST_STATE_KEY = "recycler_list_state";
    public Parcelable listState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new MainViewModel(this, this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(mainViewModel);
        setupRecyclerView(binding.rvRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
    }

    @Override
    public void onPersonListChanged(List<Person> personList) {
        PersonAdapter adapter =
                (PersonAdapter) binding.rvRecyclerView.getAdapter();
        adapter.setPersonList(personList);
        adapter.notifyDataSetChanged();
    }

    public void setupRecyclerView(RecyclerView recyclerView)
    {
        PersonAdapter adapter = new PersonAdapter();

        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mainViewModel.getResponse();
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        listState = layoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state != null)
            listState = state.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            layoutManager.onRestoreInstanceState(listState);
        }
    }
}
