package com.onedev.newsapptest.data.di

import com.onedev.newsapptest.data.remote.api.NewsApi
import com.onedev.newsapptest.data.repository.NewsRepositoryImpl
import com.onedev.newsapptest.domain.repository.NewsRepository
import com.onedev.newsapptest.domain.usecase.GetArticleUseCase
import com.onedev.newsapptest.domain.usecase.GetBlogUseCase
import com.onedev.newsapptest.domain.usecase.GetNewsSiteUseCase
import com.onedev.newsapptest.domain.usecase.GetReportUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val hostname = "api.spaceflightnewsapi.net"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/67oicpGf9++WuMMvVEdq/Q9HbNb7m0AhEnPlU54tQb8=")
            .add(hostname, "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=")
            .add(hostname, "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=")
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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

    @Provides
    @Singleton
    fun provideGetReportsUseCase(repository: NewsRepository): GetReportUseCase {
        return GetReportUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetNewsSiteUseCase(repository: NewsRepository): GetNewsSiteUseCase {
        return GetNewsSiteUseCase(repository)
    }
}
