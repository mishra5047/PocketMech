package com.mishra.pocketmech.Adapters.MechNearby.OverView;

public class ItemMechanic {

    String address, contact, name, photo, rating, timings, type;
    double latitude, longitude;
    String id;

    public ItemMechanic() {
    }

    public ItemMechanic(String address, String contact, String name, String photo, String rating, String timings, String type, double latitude, double longitude, String id) {
        this.address = address;
        this.contact = contact;
        this.name = name;
        this.photo = photo;
        this.rating = rating;
        this.timings = timings;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
