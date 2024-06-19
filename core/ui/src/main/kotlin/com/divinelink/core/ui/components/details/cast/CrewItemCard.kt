package com.divinelink.core.ui.components.details.cast

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.divinelink.core.designsystem.theme.PopularMovieItemShape
import com.divinelink.core.designsystem.theme.dimensions
import com.divinelink.core.model.details.crew.Actor
import com.divinelink.core.ui.MovieImage
import com.divinelink.core.ui.R

@Composable
fun CrewItemCard(
  modifier: Modifier = Modifier,
  actor: Actor,
) {
  Card(
    shape = PopularMovieItemShape,
    modifier = modifier
      .clip(PopularMovieItemShape)
      .clipToBounds()
      .widthIn(max = 120.dp)
      .clickable {
        // todo
      },
    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
  ) {
    MovieImage(
      modifier = Modifier,
      path = actor.profilePath,
      errorPlaceHolder = painterResource(id = R.drawable.ic_cast_placeholder)
    )

    Spacer(modifier = Modifier.height(4.dp))
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp, bottom = 4.dp, end = 8.dp)
        .height(40.dp),
      text = actor.name,
      overflow = TextOverflow.Ellipsis,
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onSurface,
    )

    Text(
      modifier = Modifier
        .padding(horizontal = MaterialTheme.dimensions.keyline_8)
        .padding(bottom = MaterialTheme.dimensions.keyline_4),
      text = actor.character,
      maxLines = 1,
      style = MaterialTheme.typography.bodySmall,
      overflow = TextOverflow.Ellipsis,
      color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.60f),
    )
  }
}