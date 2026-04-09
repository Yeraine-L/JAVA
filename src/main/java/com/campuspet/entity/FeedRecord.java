package com.campuspet.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FeedRecord {
    private Long id;
    private Long feedPointId;
    private Long volunteerId;
    private String volunteerName;
    private Integer petCount;
    private BigDecimal foodAmount;
    private String foodType;
    private LocalDateTime feedTime;
    private LocalDateTime createTime;
    private FeedPoint feedPoint;
    private User volunteer;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFeedPointId() { return feedPointId; }
    public void setFeedPointId(Long feedPointId) { this.feedPointId = feedPointId; }

    public Long getVolunteerId() { return volunteerId; }
    public void setVolunteerId(Long volunteerId) { this.volunteerId = volunteerId; }

    public String getVolunteerName() { return volunteerName; }
    public void setVolunteerName(String volunteerName) { this.volunteerName = volunteerName; }

    public Integer getPetCount() { return petCount; }
    public void setPetCount(Integer petCount) { this.petCount = petCount; }

    public BigDecimal getFoodAmount() { return foodAmount; }
    public void setFoodAmount(BigDecimal foodAmount) { this.foodAmount = foodAmount; }

    public String getFoodType() { return foodType; }
    public void setFoodType(String foodType) { this.foodType = foodType; }

    public LocalDateTime getFeedTime() { return feedTime; }
    public void setFeedTime(LocalDateTime feedTime) { this.feedTime = feedTime; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public FeedPoint getFeedPoint() { return feedPoint; }
    public void setFeedPoint(FeedPoint feedPoint) { this.feedPoint = feedPoint; }

    public User getVolunteer() { return volunteer; }
    public void setVolunteer(User volunteer) { this.volunteer = volunteer; }
}
