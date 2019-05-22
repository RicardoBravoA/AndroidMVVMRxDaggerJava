package com.rba.java.data.api;

import com.rba.java.data.entity.RepositoryEntity;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("orgs/Google/repos")
    Single<List<RepositoryEntity>> getRepositories();

    @GET("repos/{owner}/{name}")
    Single<RepositoryEntity> getRepo(@Path("owner") String owner, @Path("name") String name);
}
