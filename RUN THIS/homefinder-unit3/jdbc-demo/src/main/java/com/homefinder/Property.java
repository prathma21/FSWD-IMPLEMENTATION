package com.homefinder;

import java.math.BigDecimal;

/**
 * One row of the "properties" table = one Property object.
 * Same fields as properties.js in the React app.
 * In Unit 4 this becomes the "Domain model" class.
 */
public class Property {
    private int id;
    private String title;
    private BigDecimal price;
    private String location;
    private int bedrooms;
    private int bathrooms;
    private int area;
    private String type;
    private String status;
    private String imageUrl;
    private String description;
    private int agentId;

    public Property() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getBedrooms() { return bedrooms; }
    public void setBedrooms(int bedrooms) { this.bedrooms = bedrooms; }
    public int getBathrooms() { return bathrooms; }
    public void setBathrooms(int bathrooms) { this.bathrooms = bathrooms; }
    public int getArea() { return area; }
    public void setArea(int area) { this.area = area; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getAgentId() { return agentId; }
    public void setAgentId(int agentId) { this.agentId = agentId; }

    @Override
    public String toString() {
        return String.format("#%d | %-24s | %12s | %-26s | %dBHK %d bath %d sqft | %s | %s",
                id, title, price, location, bedrooms, bathrooms, area, type, status);
    }
}
