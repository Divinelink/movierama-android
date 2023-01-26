package com.andreolas.movierama.details.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.andreolas.movierama.ExcludeFromJacocoGeneratedReport
import com.andreolas.movierama.R
import com.andreolas.movierama.details.domain.model.Actor
import com.andreolas.movierama.details.domain.model.Director
import com.andreolas.movierama.details.domain.model.MovieDetails
import com.andreolas.movierama.home.domain.model.PopularMovie
import com.andreolas.movierama.home.ui.LoadingContent
import com.andreolas.movierama.ui.UIText
import com.andreolas.movierama.ui.components.LikeButton
import com.andreolas.movierama.ui.components.MovieImage
import com.andreolas.movierama.ui.theme.AppTheme
import com.andreolas.movierama.ui.theme.PopularMovieItemShape

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(
    viewState: DetailsViewState,
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onMarkAsFavoriteClicked: (PopularMovie) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed,
        )
    )

    BottomSheetScaffold(
        sheetElevation = 32.dp,
        sheetPeekHeight = 1.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            // to do
        },
        modifier = Modifier
            .navigationBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = viewState.movie.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp,
                    ) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    LikeButton(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .clickable {
                                onMarkAsFavoriteClicked(viewState.movie)
                            },
                        isFavorite = viewState.movie.isFavorite,
                    )
                }
            )
        }
    ) { paddingValues ->
        when (viewState) {
            is DetailsViewState.Completed -> {
                DetailsMovieContent(
                    modifier = Modifier,
                    movieDetails = viewState.movieDetails,
                )
            }
            is DetailsViewState.Error -> {
                // todo
            }
            is DetailsViewState.Initial -> LoadingContent()
        }
    }
}

@Composable
fun DetailsMovieContent(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails,
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TitleDetails(movieDetails)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {
                MovieImage(
                    path = movieDetails.posterPath,
                )

                OverviewDetails(movieDetails, movieDetails.genres)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Divider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Cast(
                cast = movieDetails.cast,
                director = movieDetails.director,
            )
        }
    }
}

@Composable
private fun Cast(
    cast: List<Actor>,
    director: Director?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .padding(start = 4.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.details__cast_title),
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 4.dp,
                bottom = 8.dp,
            ),
        ) {
            items(
                items = cast,
                key = {
                    it.id
                }
            ) {
                CrewItemCard(
                    actor = it,
                )
            }
        }

        if (director != null) {
            DirectorItem(
                director = director,
            )
        }
    }
}

@Composable
private fun DirectorItem(
    modifier: Modifier = Modifier,
    director: Director,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(
            start = 8.dp, top = 16.dp,
        )
    ) {

        Text(
            text = stringResource(id = R.string.details__director_title),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = director.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f),
        )
    }
}

@Composable
private fun CrewItemCard(
    modifier: Modifier = Modifier,
    actor: Actor,
) {
    Card(
        shape = PopularMovieItemShape,
        modifier = Modifier
            .clip(PopularMovieItemShape)
            .clipToBounds()
            .clickable {
                //                onMovieItemClick()
            },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            MovieImage(path = actor.profilePath)
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, bottom = 4.dp, end = 8.dp)
                .height(40.dp),
            text = actor.name,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            text = actor.character,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f),
        )
    }
}

@Composable
private fun TitleDetails(movieDetails: MovieDetails) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            style = MaterialTheme.typography.displaySmall,
            text = movieDetails.title,
        )
    }

    Row(
        modifier = Modifier.padding(start = 4.dp),
    ) {
        Text(
            style = MaterialTheme.typography.bodySmall,
            text = movieDetails.releaseDate,
        )
        Spacer(modifier = Modifier.width(12.dp))
        movieDetails.runtime?.let {
            Text(
                style = MaterialTheme.typography.bodySmall,
                text = movieDetails.runtime,
            )
        }
    }
}

@Composable
private fun OverviewDetails(
    movieDetails: MovieDetails,
    genres: List<String>?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        if (movieDetails.genres?.isNotEmpty() == true) {
            Row {
                genres?.forEach { genre ->
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
        if (movieDetails.overview?.isNotEmpty() == true) {
            Text(
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 8.dp,
                ),
                text = movieDetails.overview,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember")
@ExcludeFromJacocoGeneratedReport
private fun DetailsContentPreview(
    @PreviewParameter(DetailsViewStateProvider::class)
    viewState: DetailsViewState,
) {
    AppTheme {
        Surface {
            DetailsContent(
                modifier = Modifier,
                viewState = viewState,
                onNavigateUp = {},
                onMarkAsFavoriteClicked = {},
            )
        }
    }
}

class DetailsViewStateProvider : PreviewParameterProvider<DetailsViewState> {

    override val values: Sequence<DetailsViewState>
        get() {
            val popularMovie = PopularMovie(
                id = 0,
                posterPath = "",
                releaseDate = "",
                title = "Flight Club",
                rating = "",
                overview = "This movie is good.",
                isFavorite = false,
            )
            val movieDetails = MovieDetails(
                id = 1123,
                posterPath = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
                releaseDate = "2022",
                title = "Flight Club",
                rating = "9.5",
                isFavorite = false,
                overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a " +
                    "shocking new form of therapy. Their concept catches on, with underground fight clubs forming in every town," +
                    " until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
                director = Director(id = 123443321, name = "Forest Gump", profilePath = "BoxOfChocolates.jpg"),
                cast = listOf(
                    Actor(
                        id = 1,
                        name = "Jack",
                        profilePath = "AllWorkAndNoPlay.jpg",
                        character = "HelloJohnny",
                        order = 0
                    ),
                    Actor(
                        id = 2,
                        name = "Nicholson",
                        profilePath = "Cuckoo.jpg",
                        character = "McMurphy",
                        order = 1
                    ),
                    Actor(
                        id = 3,
                        name = "Jack",
                        profilePath = "AllWorkAndNoPlay.jpg",
                        character = "HelloJohnny",
                        order = 0
                    ),
                    Actor(
                        id = 4,
                        name = "Nicholson",
                        profilePath = "Cuckoo.jpg",
                        character = "McMurphy",
                        order = 1
                    ),
                    Actor(
                        id = 5,
                        name = "Jack",
                        profilePath = "AllWorkAndNoPlay.jpg",
                        character = "HelloJohnny",
                        order = 0
                    ),
                    Actor(
                        id = 6,
                        name = "Nicholson",
                        profilePath = "Cuckoo.jpg",
                        character = "McMurphy",
                        order = 1
                    ),
                    Actor(
                        id = 7,
                        name = "Jack",
                        profilePath = "AllWorkAndNoPlay.jpg",
                        character = "HelloJohnny",
                        order = 0
                    ),
                    Actor(
                        id = 8,
                        name = "Nicholson",
                        profilePath = "Cuckoo.jpg",
                        character = "McMurphy",
                        order = 1
                    ),
                ),
                genres = listOf("Thriller", "Drama", "Comedy"),
                runtime = "2h 10m"
            )

            return sequenceOf(
                DetailsViewState.Initial(
                    movie = popularMovie,
                ),

                DetailsViewState.Completed(
                    movie = popularMovie,
                    movieDetails = movieDetails,
                ),

                DetailsViewState.Error(
                    movie = popularMovie,
                    error = UIText.StringText("Something went wrong.")
                ),
            )
        }
}
