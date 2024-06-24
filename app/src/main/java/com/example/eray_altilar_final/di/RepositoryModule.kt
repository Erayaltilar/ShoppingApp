package com.example.eray_altilar_final.di

import com.example.eray_altilar_final.data.local.dao.CartDao
import com.example.eray_altilar_final.data.local.dao.FavoritesDao
import com.example.eray_altilar_final.data.remote.ProductService
import com.example.eray_altilar_final.data.remote.UserApi
import com.example.eray_altilar_final.data.repository.DatabaseRepositoryImpl
import com.example.eray_altilar_final.data.repository.FirebaseMessageRepositoryImpl
import com.example.eray_altilar_final.data.repository.ProductRepositoryImpl
import com.example.eray_altilar_final.data.repository.RemoteConfigRepositoryImpl
import com.example.eray_altilar_final.data.repository.UserRepositoryImpl
import com.example.eray_altilar_final.domain.repository.DatabaseRepository
import com.example.eray_altilar_final.domain.repository.FirebaseMessageRepository
import com.example.eray_altilar_final.domain.repository.ProductRepository
import com.example.eray_altilar_final.domain.repository.RemoteConfigRepository
import com.example.eray_altilar_final.domain.repository.UserRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepositoryImpl(userApi)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productService: ProductService): ProductRepository {
        return ProductRepositoryImpl(productService)
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(cartDao: CartDao, favoritesDao: FavoritesDao): DatabaseRepository {
        return DatabaseRepositoryImpl(cartDao, favoritesDao)
    }

    @Provides
    @Singleton
    fun provideRemoteConfigRepository(remoteConfig: FirebaseRemoteConfig): RemoteConfigRepository {
        return RemoteConfigRepositoryImpl(remoteConfig = remoteConfig)
    }

    @Provides
    @Singleton
    fun provideFirebaseMessageRepository(): FirebaseMessageRepository {
        return FirebaseMessageRepositoryImpl()
    }
}