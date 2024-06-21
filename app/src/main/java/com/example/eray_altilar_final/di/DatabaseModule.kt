package com.example.eray_altilar_final.di

import android.app.Application
import androidx.room.Room
import com.example.eray_altilar_final.data.local.dao.CartDao
import com.example.eray_altilar_final.data.local.dao.FavoritesDao
import com.example.eray_altilar_final.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "ErayAltilarDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCartDao(db: AppDatabase): CartDao {
        return db.cartDao()
    }

    @Provides
    fun provideFavoritesDao(db: AppDatabase): FavoritesDao {
        return db.favoritesDao()
    }
}