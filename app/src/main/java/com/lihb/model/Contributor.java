package com.lihb.model;

/**
 * Created by lihb on 16/1/2.
 */
public class Contributor {

    public  String login;
    public  String avatar_url;
    public  String followers_url;

    public Contributor(String login, String avatar_url, String followers_url) {
        this.login = login;
        this.avatar_url = avatar_url;
        this.followers_url = followers_url;
    }
}
