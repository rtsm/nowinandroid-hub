/*
 * Copyright 2022 The Android Open Source Project
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

package com.google.samples.apps.nowinandroid

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.google.samples.apps.nowinandroid.feature.foryou.ForYouPlugin
import com.google.samples.apps.nowinandroid.feature.topic.TopicPlugin
import com.google.samples.apps.nowinandroid.sync.initializers.Sync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

/**
 * [Application] class for NiA
 */
@HiltAndroidApp
class NiaApplication : Application(), ImageLoaderFactory {
    @Inject
    lateinit var imageLoader: Provider<ImageLoader>

    override fun onCreate() {
        super.onCreate()
        setOf(ForYouPlugin, TopicPlugin).forEach { println(it.id) } // had to make ForYouPlugin init before actual navgraph, PH does that on its own

        // Initialize Sync; the system responsible for keeping data in the app up to date.
        Sync.initialize(context = this)
    }

    override fun newImageLoader(): ImageLoader = imageLoader.get()
}
