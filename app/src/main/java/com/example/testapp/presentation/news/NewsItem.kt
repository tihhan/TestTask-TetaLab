package com.example.testapp.presentation.news

import BookmarkedViewModel
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil.compose.rememberImagePainter
import com.example.testapp.R
import com.example.testapp.data.Network.Article
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsItem(
    title: String,
    description: String?,
    url: String,
    imageUrl: String?,
    author: String?,
    publishedAt: String?,
    viewModel: BookmarkedViewModel
)
{
    val context = LocalContext.current
    var isBookmarked by remember { mutableStateOf(false) }


    LaunchedEffect(url) {
        viewModel.bookmarkedArticles.collect { articles ->
            isBookmarked = articles.any { it.url == url }
        }
    }

    val formatter = remember {
        DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm")
    }


    val formattedDate = remember(publishedAt) {
        publishedAt?.let {
            val zdt = ZonedDateTime.parse(it)
            val localDateTime = zdt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
            localDateTime.format(formatter)
        } ?: "Date not available"
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }


            }

            imageUrl?.let {
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = rememberImagePainter(it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier.padding(20.dp)
                    ) {
                author?.let {
                    Text(
                        text =  "Author: ${it.take(25)}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                publishedAt?.let {
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Row {
                IconButton(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.toggleBookmark(
                                Article(
                                    title,
                                    description,
                                    url,
                                    imageUrl,
                                    author,
                                    publishedAt
                                )
                            )
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isBookmarked) R.drawable.bookmark else R.drawable.bookmark_white
                        ),
                        contentDescription = "Bookmark",
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Open in browser")
                }
            }
        }
    }
}