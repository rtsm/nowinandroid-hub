/*
 * Copyright 2023 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.google.samples.apps.nowinandroid.feature.foryou

import com.google.samples.apps.nowinandroid.core.data.NavigationAction
import com.google.samples.apps.nowinandroid.core.data.NavigationAction.Type.GRAPH
import com.google.samples.apps.nowinandroid.core.data.HubMock
import com.google.samples.apps.nowinandroid.core.data.Plugin
import com.google.samples.apps.nowinandroid.feature.foryou.navigation.forYouNavigationRoute
import com.google.samples.apps.nowinandroid.feature.foryou.navigation.forYouScreen

object ForYouPlugin: Plugin {
    init {
        HubMock.registry.add(this)
    }

    override val id = forYouNavigationRoute

    override fun onNavigationAction(action: NavigationAction) {
        when (action.type) {
            GRAPH -> {
                action.graphBuilder?.forYouScreen {}
            }
            else -> Unit
        }
    }
}