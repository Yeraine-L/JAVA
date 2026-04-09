package com.campuspet.entity;

import java.time.LocalDateTime;

public class ExceptionReport {
    private Long id;
    private Long volunteerId;
    private String volunteerName;
    private String location;
    private String area;
    private String type;
    private String description;
    private String photo;
    private String status;
    private String handleOpinion;
    private LocalDateTime handleTime;
    private LocalDateTime createTime;
    private User volunteer;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVolunteerId() { return volunteerId; }
    public void setVolunteerId(Long volunteerId) { this.volunteerId = volunteerId; }

    public String getVolunteerName() { return volunteerName; }
    public void setVolunteerName(String volunteerName) { this.volunteerName = volunteerName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getHandleOpinion() { return handleOpinion; }
    public void setHandleOpinion(String handleOpinion) { this.handleOpinion = handleOpinion; }

    public LocalDateTime getHandleTime() { return handleTime; }
    public void setHandleTime(LocalDateTime handleTime) { this.handleTime = handleTime; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public User getVolunteer() { return volunteer; }
    public void setVolunteer(User volunteer) { this.volunteer = volunteer; }
}
