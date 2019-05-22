package com.rba.java.data.entity;

import com.google.gson.annotations.SerializedName;

public class RepositoryEntity {

    public final long id;
    public final String name;
    public final String description;
    public final UserEntity owner;
    @SerializedName("stargazers_count")
    public final long stars;
    @SerializedName("forks_count")
    public final long forks;

    public RepositoryEntity(long id, String name, String description, UserEntity owner, long stars, long forks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.stars = stars;
        this.forks = forks;
    }
}
