package bg.adastragrp.adastrafaceapp.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import java.io.Serializable;

import bg.adastragrp.adastrafaceapp.BR;

@Entity
public class User extends BaseObservable implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private @NonNull String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "avatar_url")
    private String avatarUrl;
    @ColumnInfo(name = "avatar_short_path_name")
    private String avatarShortPathName;
    @ColumnInfo(name = "position")
    private String position;
    @ColumnInfo(name = "department")
    private String department;
    @ColumnInfo(name = "seniority")
    private String seniority;
    @ColumnInfo(name = "office")
    private String office;
    @ColumnInfo(name = "room")
    private String room;
    @ColumnInfo(name = "when_joined")
    private Long whenJoined;
    @ColumnInfo(name = "date_user_created")
    private Long dateUserCreated;
    private String password;

    public User() {
    }

    public User(String name,
                String email,
                String avatarUrl,
                String position,
                String department,
                String seniority,
                String office,
                String room,
                Long whenJoined,
                String password) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.position = position;
        this.department = department;
        this.seniority = seniority;
        this.office = office;
        this.room = room;
        this.whenJoined = whenJoined;
        this.password = password;
    }

    public User(@NonNull String id,
                String name,
                String email,
                String avatarUrl,
                String position,
                String department,
                String seniority,
                String office,
                String room,
                Long whenJoined,
                Long dateUserCreated) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.position = position;
        this.department = department;
        this.seniority = seniority;
        this.office = office;
        this.room = room;
        this.whenJoined = whenJoined;
        this.dateUserCreated = dateUserCreated;
    }

    public User(@NonNull String id,
                String name,
                String email,
                String avatarUrl,
                String avatarShortPathName,
                String position,
                String department,
                String seniority,
                String office,
                String room,
                Long whenJoined,
                Long dateUserCreated) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.avatarShortPathName = avatarShortPathName;
        this.position = position;
        this.department = department;
        this.seniority = seniority;
        this.office = office;
        this.room = room;
        this.whenJoined = whenJoined;
        this.dateUserCreated = dateUserCreated;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Bindable
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getEmail() {
        return email;
    }

    @Bindable
    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Bindable
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        notifyPropertyChanged(BR.avatarUrl);
    }

    public String getAvatarShortPathName() {
        return avatarShortPathName;
    }

    @Bindable
    public void setAvatarShortPathName(String avatarShortPathName) {
        this.avatarShortPathName = avatarShortPathName;
        notifyPropertyChanged(BR.avatarShortPathName);
    }

    public String getPosition() {
        return position;
    }

    @Bindable
    public void setPosition(String position) {
        this.position = position;
        notifyPropertyChanged(BR.position);
    }

    public String getDepartment() {
        return department;
    }

    @Bindable
    public void setDepartment(String department) {
        this.department = department;
        notifyPropertyChanged(BR.department);
    }

    public String getSeniority() {
        return seniority;
    }

    @Bindable
    public void setSeniority(String seniority) {
        this.seniority = seniority;
        notifyPropertyChanged(BR.seniority);
    }

    public String getOffice() {
        return office;
    }

    @Bindable
    public void setOffice(String office) {
        this.office = office;
        notifyPropertyChanged(BR.office);
    }

    public String getRoom() {
        return room;
    }

    @Bindable
    public void setRoom(String room) {
        this.room = room;
        notifyPropertyChanged(BR.room);
    }

    public Long getWhenJoined() {
        return whenJoined;
    }

    @Bindable
    public void setWhenJoined(Long whenJoined) {
        this.whenJoined = whenJoined;
        notifyPropertyChanged(BR.whenJoined);
    }

    public Long getDateUserCreated() {
        return dateUserCreated;
    }

    @Bindable
    public void setDateUserCreated(Long dateUserCreated) {
        this.dateUserCreated = dateUserCreated;
        notifyPropertyChanged(BR.dateUserCreated);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
