/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.feature.interests

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavController
import androidx.navigation.NavType
import com.google.samples.apps.nowinandroid.core.data.NavigationAction
import com.google.samples.apps.nowinandroid.core.data.HubMock
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaBackground
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.google.samples.apps.nowinandroid.core.domain.model.FollowableTopic
import com.google.samples.apps.nowinandroid.core.ui.DevicePreviews
import com.google.samples.apps.nowinandroid.core.ui.FollowableTopicPreviewParameterProvider
import com.google.samples.apps.nowinandroid.core.ui.TrackScreenViewEvent

@Composable
internal fun InterestsRoute(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: InterestsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var topicId by remember { mutableStateOf<String?>(null) }
    var launched by remember { mutableStateOf(false) }

    val onTopicClick: (String) -> Unit = { id ->
       topicId = id
    }

    if(topicId != null && !launched) {
        val args = mapOf("topicId" to NavArgumentBuilder().apply {
            type = NavType.StringType
            defaultValue = topicId!!
        }.build())
        HubMock.navigationEvent(
            "topic",
            NavigationAction.Builder()
                .toDynamic()
                .withNavController(navController)
                .withArguments(args)
                .build()
        )
        launched = true
    }
    InterestsScreen(
        uiState = uiState,
        followTopic = viewModel::followTopic,
        onTopicClick = onTopicClick,
        modifier = modifier,
    )
}

@Composable
internal fun InterestsScreen(
    uiState: InterestsUiState,
    followTopic: (String, Boolean) -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            InterestsUiState.Loading ->
                NiaLoadingWheel(
                    modifier = modifier,
                    contentDesc = stringResource(id = R.string.loading),
                )
            is InterestsUiState.Interests ->
                TopicsTabContent(
                    topics = uiState.topics,
                    onTopicClick = onTopicClick,
                    onFollowButtonClick = followTopic,
                    modifier = modifier,
                )
            is InterestsUiState.Empty -> InterestsEmptyScreen()
        }
    }
    TrackScreenViewEvent(screenName = "Interests")
}

@Composable
private fun InterestsEmptyScreen() {
    Text(text = stringResource(id = R.string.empty_header))
}

@DevicePreviews
@Composable
fun InterestsScreenPopulated(
    @PreviewParameter(FollowableTopicPreviewParameterProvider::class)
    followableTopics: List<FollowableTopic>,
) {
    NiaTheme {
        NiaBackground {
            InterestsScreen(
                uiState = InterestsUiState.Interests(
                    topics = followableTopics,
                ),
                followTopic = { _, _ -> },
                onTopicClick = {},
            )
        }
    }
}

@DevicePreviews
@Composable
fun InterestsScreenLoading() {
    NiaTheme {
        NiaBackground {
            InterestsScreen(
                uiState = InterestsUiState.Loading,
                followTopic = { _, _ -> },
                onTopicClick = {},
            )
        }
    }
}

@DevicePreviews
@Composable
fun InterestsScreenEmpty() {
    NiaTheme {
        NiaBackground {
            InterestsScreen(
                uiState = InterestsUiState.Empty,
                followTopic = { _, _ -> },
                onTopicClick = {},
            )
        }
    }
}
