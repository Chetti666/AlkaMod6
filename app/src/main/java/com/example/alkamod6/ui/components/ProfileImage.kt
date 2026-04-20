package com.example.alkamod6.ui.components

import android.widget.ImageView
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.squareup.picasso.Picasso

@Composable
fun ProfileImage(url: String, modifier: Modifier = Modifier) {
    // Usamos AndroidView para integrar Picasso (que es una librería de Views) en Compose
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape),
        update = { view ->
            val finalUrl = if (url.isEmpty()) "https://ui-avatars.com/api/?name=User&background=random" else url
            Picasso.get()
                .load(finalUrl)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(view)
        }
    )
}
