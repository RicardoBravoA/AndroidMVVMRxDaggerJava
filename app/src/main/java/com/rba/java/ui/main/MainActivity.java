package com.rba.java.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rba.java.R;
import com.rba.java.base.BaseActivity;
import com.rba.java.data.entity.RepositoryEntity;
import com.rba.java.util.ViewModelFactory;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RepositoryListener {

    private static final int LAYOUT = R.layout.activity_main;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @Inject
    ViewModelFactory viewModelFactory;
    private RepositoryViewmodel repositoryViewModel;

    @Override
    protected int layoutRes() {
        return LAYOUT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        repositoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(RepositoryViewmodel.class);

        rvData.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvData.setAdapter(new RepositoryAdapter(repositoryViewModel, this, this));
        rvData.setLayoutManager(new LinearLayoutManager(this));

        observableViewModel();

    }

    @Override
    public void onClickRepository(RepositoryEntity repositoryEntity) {
        Log.i("z- onClick", new Gson().toJson(repositoryEntity));
    }

    private void observableViewModel() {
        repositoryViewModel.getRepositoryList().observe(this, repos -> {
            if (repos != null) rvData.setVisibility(View.VISIBLE);
        });

        repositoryViewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                tvError.setVisibility(View.VISIBLE);
                rvData.setVisibility(View.GONE);
                tvError.setText("An Error Occurred While Loading Data!");
            } else {
                tvError.setVisibility(View.GONE);
                tvError.setText("");
            }
        });

        repositoryViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                pbLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    tvError.setVisibility(View.GONE);
                    rvData.setVisibility(View.GONE);
                }
            }
        });
    }
}
