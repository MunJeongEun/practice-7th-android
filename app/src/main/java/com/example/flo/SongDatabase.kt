package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class, User::class, Like::class, Album::class], version = 3)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun userDao(): UserDao
    abstract fun albumDao(): AlbumDao

    // (수정) instance 반환 타입 non-null 변경 및 @Synchronized 제거
    companion object {
        @Volatile
        private var instance: SongDatabase? = null

        fun getInstance(context: Context): SongDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    SongDatabase::class.java,
                    "song-database"
                )
                    .fallbackToDestructiveMigration() // 기존 데이터를 삭제하고 새로운 DB를 생성
                    .allowMainThreadQueries()
                    .build().also { instance = it }
            }
        }
    }
}