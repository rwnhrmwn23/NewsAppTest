package com.onedev.newsapptest.data.di

import com.onedev.newsapptest.data.remote.api.NewsApi
import com.onedev.newsapptest.data.repository.NewsRepositoryImpl
import com.onedev.newsapptest.domain.repository.NewsRepository
import com.onedev.newsapptest.domain.usecase.GetArticleUseCase
import com.onedev.newsapptest.domain.usecase.GetBlogUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideSpaceApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl("https://api.spaceflightnewsapi.net/v4/")
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(repository: NewsRepository): GetArticleUseCase {
        return GetArticleUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBlogsUseCase(repository: NewsRepository): GetBlogUseCase {
        return GetBlogUseCase(repository)
    }
}
