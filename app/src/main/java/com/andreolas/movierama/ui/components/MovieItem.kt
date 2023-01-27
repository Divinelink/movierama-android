@file:Suppress("LongMethod")

package com.andreolas.movierama.ui.components

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andreolas.movierama.ExcludeFromJacocoGeneratedReport
import com.andreolas.movierama.R
import com.andreolas.movierama.home.domain.model.Movie
import com.andreolas.movierama.ui.theme.AppTheme
import com.andreolas.movierama.ui.theme.PopularMovieItemShape

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieItemClick: () -> Unit,
    onLikeMovieClick: () -> Unit,
) {
    Card(
        shape = PopularMovieItemShape,
        modifier = Modifier
            .widthIn(max = 140.dp)
            .clip(PopularMovieItemShape)
            .clipToBounds()
            .clickable {
                onMovieItemClick()
            },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            MovieImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(180.dp),
                path = movie.posterPath,
            )
            movie.isFavorite?.let {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(bottomEnd = 8.dp))
                        .clickable { onLikeMovieClick() }
                ) {
                    LikeButton(
                        isFavorite = movie.isFavorite,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Rating(
            modifier = Modifier.padding(start = 8.dp),
            rating = movie.rating,
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, bottom = 4.dp, end = 8.dp)
                .height(40.dp),
            text = movie.title,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            text = movie.releaseDate,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f),
        )
    }
}

@Composable
fun LikeButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
) {
    Box {
        val color by animateColorAsState(
            targetValue = when (isFavorite) {
                true -> colorResource(id = R.color.core_red_highlight)
                false -> colorResource(id = R.color.core_grey_55)
            }
        )
        Box(
            modifier = modifier
                .size(44.dp)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
        ) {
            Crossfade(
                modifier = Modifier.align(Alignment.Center),
                targetState = isFavorite
            ) { favorite ->
                val image = when (favorite) {
                    true -> Icons.Default.Favorite
                    false -> Icons.Default.FavoriteBorder
                }
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = image,
                    tint = color,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun Rating(
    modifier: Modifier,
    rating: String,
    leadingContent: (@Composable () -> Unit) = {},
    trailingContent: (@Composable () -> Unit) = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingContent()
        Icon(
            imageVector = Icons.Default.Star,
            tint = Color.Yellow,
            contentDescription = stringResource(id = R.string.popular_movie__rating),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = rating,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        trailingContent()
    }
}

@Composable
@ExcludeFromJacocoGeneratedReport
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PopularMovieItemPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .width(160.dp)
                .height(340.dp)
        ) {
            MovieItem(
                modifier = Modifier,
                movie = Movie(
                    id = 0,
                    posterPath = "original/A81kDB6a1K86YLlcOtZB27jriJh.jpg",
                    releaseDate = "2023",
                    title = "Fight Club",
                    rating = "72",
                    isFavorite = true,
                    overview = "",
                ),
                onMovieItemClick = {},
                onLikeMovieClick = {},
            )
        }
    }
}

@Composable
@ExcludeFromJacocoGeneratedReport
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun MovieItemPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
        ) {
            MovieItem(
                modifier = Modifier,
                movie = Movie(
                    id = 0,
                    posterPath = "/w200/A81kDB6a1K86YLlcOtZB27jriJh.jpg",
                    releaseDate = "2023",
                    title = "Night of the Day of the Dawn of the Son of the Bride of the " +
                        "Return of the Revenge of the Terror of the Attack of the Evil," +
                        " Mutant, Alien, Flesh Eating, Hellbound, Zombified Living Dead",
                    rating = "4.2",
                    isFavorite = null,
                    overview = "",
                ),
                onMovieItemClick = {},
                onLikeMovieClick = {},
            )
        }
    }
}