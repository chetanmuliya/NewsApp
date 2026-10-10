package com.example.newsapp.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.domain.helper.LatestNewsState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
    ) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->

        val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

        when(uiStates){
            is LatestNewsState.Error -> {


            }
            LatestNewsState.Loading -> {


            }
            is LatestNewsState.Success -> {

                val data = (uiStates as LatestNewsState.Success).data.results
                data?.let {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),

                        ) {
                        items(
                            data,
                            key = { it.article_id?:0 }
                        ) {
                            Text(
                                text = it.title?:""
                            )
                        }
                    }

                }
            }
        }

    }
}