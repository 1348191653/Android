package com.example.personal_blog.fragment_boke;

import java.io.Serializable;

public class BlogInfo implements Serializable {
    public static final String SKETCH = "草稿";          //草稿
    public static final String PUBLISH = "已发布";         //已发布
    private long id;
    private String title;
    private String content;
    private String time;
    private String writer;
    private String state;
    private String category;

    public BlogInfo(){}

    public BlogInfo(long id, String title, String content, String time, String writer,
                    String state, String category){
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.writer = writer;
        this.state = state;
        this.category = category;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
