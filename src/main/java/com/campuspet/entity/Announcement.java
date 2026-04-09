package com.campuspet.entity;

import java.time.LocalDateTime;

public class Announcement {
    private Long id;
    private String title;
    private String content;
    private Long publisherId;
    private String publisherName;
    private LocalDateTime publishTime;
    private LocalDateTime updateTime;
    private User publisher;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getPublisherId() { return publisherId; }
    public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }

    public String getPublisherName() { return publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }

    public LocalDateTime getPublishTime() { return publishTime; }
    public void setPublishTime(LocalDateTime publishTime) { this.publishTime = publishTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }

    public User getPublisher() { return publisher; }
    public void setPublisher(User publisher) { this.publisher = publisher; }
}
