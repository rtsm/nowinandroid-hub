package com.google.samples.apps.nowinandroid.feature.topic

import androidx.navigation.NavDestination
import androidx.navigation.compose.ComposeNavigator
import com.google.samples.apps.nowinandroid.core.data.NavigationAction
import com.google.samples.apps.nowinandroid.core.data.NavigationAction.Type.DYNAMIC
import com.google.samples.apps.nowinandroid.core.data.HubMock
import com.google.samples.apps.nowinandroid.core.data.Plugin

object TopicPlugin: Plugin {
    init {
        HubMock.registry.add(this)
    }

    override val id = "topic"

    override fun onNavigationAction(action: NavigationAction) {
        when (action.type) {
            DYNAMIC -> {
               action.navController?.graph?.apply {
                val navigator =
                    action.navController!!.navigatorProvider.getNavigator(ComposeNavigator::class.java)
                NavDestination.Companion
                val destination =
                    ComposeNavigator.Destination(navigator) { TopicRoute({}, {}) }.apply {
                        this.route = this@TopicPlugin.id
                        action.arguments?.forEach { (argumentName, argument) ->
                            addArgument(argumentName, argument)
                        }
                    }
                addDestination(destination)
                action.navController!!.navigate(this@TopicPlugin.id)
                remove(destination)
               }
            }
            else -> Unit
        }
    }

}
