package moe.notify.animenotifier.network.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RESTMyAnimeList {
    @SerializedName("userName")
    @Expose
    public String mUserName;
}