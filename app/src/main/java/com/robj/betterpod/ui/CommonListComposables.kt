package com.robj.betterpod.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.robj.betterpod.R
import com.robj.betterpod.networking.models.Category
import com.robj.betterpod.networking.models.Episode
import com.robj.betterpod.networking.models.Podcast
import com.robj.betterpod.ui.compose.screens.search.TextFieldState

@Composable
fun podcastList(
    podcasts: List<Podcast>,
    onNavigateToDetails: (podcast: Podcast) -> Unit,
    headerView: @Composable (() -> Unit)? = null,
    state: LazyListState
) {
    LazyColumn(state = state) {
        headerView?.let {
            item {
                headerView()
            }
        }
        items(podcasts) { podcast ->
            podcastRow(podcast = podcast, onNavigateToDetails)
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
        }
    }
}

@Composable
fun CategoryChips(
    categories: List<Category>,
    onCategorySelectionUpdated: (categories: List<Category>) -> Unit
) {
    val state = rememberLazyListState()
    val selectedCategories = hashSetOf<Category>()
    LazyRow(state = state) {
        items(categories) { category ->
            val isSelected = rememberSaveable { mutableStateOf(false) }
            Text(
                modifier = Modifier
                    .padding(2.dp)
                    .background(
                        if (isSelected.value) {
                            Color.Gray
                        } else {
                            Color.Yellow
                        }, RoundedCornerShape(4.dp)
                    )
                    .padding(4.dp)
                    .clickable {
                        isSelected.value = !isSelected.value
                        if (isSelected.value) {
                            selectedCategories.add(category)
                        } else {
                            selectedCategories.remove(category)
                        }
                        onCategorySelectionUpdated(selectedCategories.toList())
                    }, text = category.name
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun podcastPager(podcasts: List<Podcast>, onNavigateToDetails: (podcast: Podcast) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(4.dp))
    ) {
        val pagerState = rememberPagerState()
        HorizontalPager(count = podcasts.size, state = pagerState) { page ->
            val podcast = podcasts[page]
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(podcast.artwork)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(
                        onClick = {
                            onNavigateToDetails(podcast)
                        },
                    )
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(
    textState: TextFieldState,
    onValueChanged: (query: String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .apply {
                if (textState.hasFocus) {
                    focusTarget()
                } else {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            }
            .focusRequester(focusRequester)
            .onFocusChanged {
                textState.hasFocus = it.hasFocus
            }
            .padding(vertical = 2.dp),
        value = textState.text,
        onValueChange = {
            textState.text = it
            onValueChanged(it)
        },
        label = { Text(stringResource(R.string.search)) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        ),
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search") },
        trailingIcon = if (textState.text.isNotBlank()) {
            {
                IconButton(onClick = {
                    textState.text = ""
                    onValueChanged(textState.text)
                    keyboardController?.hide()
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
                }
            }
        } else {
            null
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
    )
}

@Composable
fun EpisodeList(episodes: List<Episode>) {
    LazyColumn(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = 0.dp
        )
    ) {
        items(episodes) { episode ->
            EpisodeRow(episode)
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}