@file:Suppress("MagicNumber")

package com.andreolas.movierama.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.andreolas.movierama.R
import com.andreolas.movierama.base.communication.ApiConstants

@Composable
fun MovieImage(
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Crop,
  errorPlaceHolder: Painter = painterResource(R.drawable.ic_movie_placeholder),
  path: String?,
) {
  AsyncImage(
    modifier = modifier
      .aspectRatio((2f / 3f)),
    model = ImageRequest.Builder(LocalContext.current)
      .memoryCachePolicy(CachePolicy.ENABLED)
      .diskCachePolicy(CachePolicy.ENABLED)
      .data(ApiConstants.TMDB_IMAGE_URL + path)
      .crossfade(true)
      .build(),
    error = errorPlaceHolder,
    contentDescription = stringResource(id = R.string.movie_image_placeholder),
    contentScale = contentScale,
  )
}
