package com.example.newsapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun NewsTabs(
    modifier: Modifier = Modifier,
    tabList : List<String>,
    onTabClicked:(Int) -> Unit
) {

    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        PrimaryTabRow(
            selectedTabIndex
        ) {
            tabList.forEachIndexed { index, name ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        onTabClicked(selectedTabIndex)
                    },
                    text = {
                        Text(name)
                    }
                )
            }
        }
    }
    
}