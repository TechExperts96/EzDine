package com.techexpert.ezdine.app.foundation.di.module

import com.techexpert.ezdine.app.foundation.network.repository.BaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(): BaseRepository = BaseRepository()
}
