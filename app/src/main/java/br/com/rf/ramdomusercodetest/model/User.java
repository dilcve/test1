package br.com.rf.ramdomusercodetest.model;

import android.support.annotation.NonNull;

/**
 * Created by rodrigoferreira on 11/06/2017.
 */

public class User implements Comparable<User> {


    private String gender;
    private Name name;
    private Location location;
    private String email;
    private String registered;
    private String phone;
    private String cell;
    private Picture picture;

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Name getName() {
        return this.name;
    }

    public String getFullname() {
        return this.name.getFirst() + " " + this.name.getLast();
    }

    public void setName(Name name) {
        this.name = name;
    }


    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistered() {
        return this.registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return this.cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public class Name {
        private String first;

        public String getFirst() {
            return this.first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        private String last;

        public String getLast() {
            return this.last;
        }

        public void setLast(String last) {
            this.last = last;
        }
    }

    public class Location {
        private String street;

        public String getStreet() {
            return this.street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        private String city;

        public String getCity() {
            return this.city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        private String state;

        public String getState() {
            return this.state;
        }

        public void setState(String state) {
            this.state = state;
        }

//        private int postcode;
//
//        public int getPostcode() {
//            return this.postcode;
//        }
//
//        public void setPostcode(int postcode) {
//            this.postcode = postcode;
//        }
    }

    public class Picture {
        private String large;

        public String getLarge() {
            return this.large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        private String medium;

        public String getMedium() {
            return this.medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        private String thumbnail;

        public String getThumbnail() {
            return this.thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    @Override
    public int compareTo(@NonNull User user) {
        return this.getFullname().compareTo(user.getFullname());
    }
}
