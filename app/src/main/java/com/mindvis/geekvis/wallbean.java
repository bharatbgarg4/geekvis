package com.mindvis.geekvis;

import java.util.ArrayList;

/**
 * Created by Deepak Bansal on 6/22/2016.
 */
public class wallbean {

    String name;
    String status;



    public String getImgposturl() {
        return imgposturl;
    }

    public void setImgposturl(String imgposturl) {
        this.imgposturl = imgposturl;
    }

    String imgposturl;

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    String profilepic;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    int likes,comment;

    public wallbean(String name, String status, String imgposturl,String profilepic, int likes, int comment) {
        this.name = name;
        this.status = status;
        this.imgposturl=imgposturl;
        this.profilepic = profilepic;
        this.likes = likes;
        this.comment = comment;
    }
}
