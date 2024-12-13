package vttp.batch5.ssf.noticeboard.models;


import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Notice {


    @NotNull
    @Size(min = 3, max = 128,message="Title must be between 3-128 characters")
    private String title;
    @Email
    @NotEmpty(message="Please input email!")
    private String poster;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;
    @NotNull(message="At least 1 category must be selected")
    private List<String> categories;
    @NotEmpty(message="Please input contents of the notice!")
    private String text;


    public Notice() {
    }

    public Notice(String title, String poster, Date postDate, List<String> categories, String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }


    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", postDate=" + postDate +
                ", categories=" + categories +
                ", text='" + text + '\'' +
                '}';
    }
}
