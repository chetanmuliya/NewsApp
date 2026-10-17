package com.example.newsapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.domain.helper.LatestNewsState
import com.example.newsapp.domain.model.Result

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
    ) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->

        //for direct api call use this
        // val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

        //for room
        val uiStates by viewModel.newsCache.collectAsStateWithLifecycle()

        //search
        val uiSearchStates by viewModel.searchState.collectAsStateWithLifecycle()
        var query by remember { mutableStateOf("") }


        val displaystate = if (query.isBlank()) uiStates else uiSearchStates

        val data = when (displaystate) {
            is LatestNewsState.Success -> displaystate.data
            is LatestNewsState.Loading -> emptyList()
            is LatestNewsState.Error -> emptyList()
        }

        when(displaystate){
            is LatestNewsState.Error -> {

                val message = (uiStates as LatestNewsState.Error).error
                Box(
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = message,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )

                }

            }
            LatestNewsState.Loading -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator()

                }

            }
            is LatestNewsState.Success -> {

                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(paddingValues)
                ) {

                    NewsSearchTextField(
                        value = query,
                        onValueChange = { newQuery ->
                            query = newQuery
                            viewModel.searchNews(newQuery)
                        },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(Modifier.height(5.dp))

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                        ) {
                        items(
                            data,
                            key = { it.article_id?:0 }
                        ) {

                            NewsCard(it)

                        }
                    }

                }

            }
        }

    }
}


@Composable
fun NewsCard(
    data: Result,
    onBookmarkClick: () -> Unit = {}
) {
    var isBookmarked by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = data.image_url,
                contentDescription = "News thumbnail",
                modifier = Modifier
                    .size(width = 112.dp, height = 120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_image_placeholder)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = data.title.orEmpty(),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        onClick = {
                            isBookmarked = !isBookmarked
                            onBookmarkClick()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = if (isBookmarked) {
                                painterResource(R.drawable.ic_bookmark_filled)
                            } else {
                                painterResource(R.drawable.ic_bookmark)
                            },
                            contentDescription = if (isBookmarked) {
                                "Remove bookmark"
                            } else {
                                "Bookmark article"
                            },
                            tint = if (isBookmarked) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = data.description.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun NewsSearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        placeholder = {
            Text("Search news...")
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(
                    onClick = { onValueChange("") }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF1F3F8),
            unfocusedContainerColor = Color(0xFFF1F3F8),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFF2563EB)
        )
    )
}