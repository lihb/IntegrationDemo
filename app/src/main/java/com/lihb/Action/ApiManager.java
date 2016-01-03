package com.lihb.Action;

import com.lihb.model.Contributor;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by lihb on 16/1/2.
 */
public interface ApiManager {

    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("{user}/followers")
    Observable<List<Contributor>> followers(
            @Path("user") String user);


}
