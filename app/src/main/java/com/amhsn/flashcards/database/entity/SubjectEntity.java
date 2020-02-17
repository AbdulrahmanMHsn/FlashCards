package com.amhsn.flashcards.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class SubjectEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subject_id")
    private int id;
    @ColumnInfo(name = "subject_title")
    private String title;


    // Room Construction
    public SubjectEntity(int id, String title) {
        this.id = id;
        this.title = title;
    }

    // Our Constructions
    @Ignore
    public SubjectEntity(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
