package com.rba.java.data.api.repository;

import com.rba.java.data.api.ApiService;
import com.rba.java.data.entity.RepositoryEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class Repository {

    private final ApiService apiService;

    @Inject
    public Repository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<List<RepositoryEntity>> getRepositoryList() {
        return apiService.getRepositories();
    }

    public Single<RepositoryEntity> getRepository(String owner, String name) {
        return apiService.getRepo(owner, name);
    }
}