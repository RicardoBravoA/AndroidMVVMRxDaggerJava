package com.rba.java.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.rba.java.R;
import com.rba.java.data.entity.RepositoryEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private RepositoryListener repositoryListener;
    private final List<RepositoryEntity> data = new ArrayList<>();

    RepositoryAdapter(RepositoryViewmodel viewModel, LifecycleOwner lifecycleOwner, RepositoryListener repositoryListener) {
        this.repositoryListener = repositoryListener;
        viewModel.getRepositoryList().observe(lifecycleOwner, repos -> {
            data.clear();
            if (repos != null) {
                data.addAll(repos);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_item, parent, false);
        return new RepositoryViewHolder(view, repositoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    static final class RepositoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView repoNameTextView;
        @BindView(R.id.tv_description)
        TextView repoDescriptionTextView;
        @BindView(R.id.tv_forks)
        TextView forksTextView;
        @BindView(R.id.tv_stars)
        TextView starsTextView;

        private RepositoryEntity repositoryEntity;

        RepositoryViewHolder(View itemView, RepositoryListener repositoryListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (repositoryEntity != null) {
                    repositoryListener.onClickRepository(repositoryEntity);
                }
            });
        }

        void bind(RepositoryEntity repositoryEntity) {
            this.repositoryEntity = repositoryEntity;
            repoNameTextView.setText(repositoryEntity.name);
            repoDescriptionTextView.setText(repositoryEntity.description);
            forksTextView.setText(String.valueOf(repositoryEntity.forks));
            starsTextView.setText(String.valueOf(repositoryEntity.stars));
        }
    }
}