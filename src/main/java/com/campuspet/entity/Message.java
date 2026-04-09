package com.campuspet.entity;

import java.time.LocalDateTime;

public class Message {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String type;
    private String isRead;
    private LocalDateTime sendTime;
    private User user;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getIsRead() { return isRead; }
    public void setIsRead(String isRead) { this.isRead = isRead; }

    public LocalDateTime getSendTime() { return sendTime; }
    public void setSendTime(LocalDateTime sendTime) { this.sendTime = sendTime; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
