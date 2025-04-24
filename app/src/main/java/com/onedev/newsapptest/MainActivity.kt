package com.onedev.newsapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.onedev.newsapptest.presentation.home.HomeScreen
import com.onedev.newsapptest.ui.theme.NewsAppTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsAppTestTheme {
                HomeScreen(
                    userName = "One",
                )
            }
        }
    }
}
