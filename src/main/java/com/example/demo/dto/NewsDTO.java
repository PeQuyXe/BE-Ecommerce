package com.example.demo.dto;

import com.example.demo.model.News;

import java.time.LocalDateTime;

public class NewsDTO {
    private Integer id;
    private Integer userId;
    private String authorName;
    private String title;
    private String content;
    private String thumb;
    private String slug;
    private Integer view;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    // Constructor không tham số
    public NewsDTO() {}

    // Constructor để chuyển từ News entity sang NewsDTO
    public NewsDTO(News news) {
        this.id = news.getId();
        this.userId = news.getUserId();
        this.title = news.getTitle();
        this.content = news.getContent();
        this.thumb = news.getThumb();
        this.slug = news.getSlug();
        this.view = news.getView();
        this.status = news.getStatus();

        // Dùng trực tiếp LocalDateTime từ entity News
        this.createAt = news.getCreateAt();
        this.updateAt = news.getUpdateAt();
    }

    // Getter và Setter
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
