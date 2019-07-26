package com.example.roomwordssample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    //  If you use a return value of type LiveData in your method description, Room generates all necessary code         to update the LiveData when the database is updated.
    @Query("SELECT * FROM word_table ORDER BY word")
    LiveData<List<Word>> getAllWords();

}
