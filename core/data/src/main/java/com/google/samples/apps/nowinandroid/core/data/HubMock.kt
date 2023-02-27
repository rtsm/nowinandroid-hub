
package com.google.samples.apps.nowinandroid.core.data

import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

object HubMock {

    val registry = mutableSetOf<Plugin>()

    fun navigationEvent(id: String, action: NavigationAction) {
        println(registry)
        registry.firstOrNull { it.id == id }?.onNavigationAction(action)
    }

}

interface Plugin {
    val id: String
    fun onNavigationAction(action: NavigationAction)
}

data class NavigationAction(
    val type: Type,
    val graphBuilder: NavGraphBuilder?,
    val navController: NavController?,
    val arguments: Map<String, NavArgument>?
) {
    class Builder {
        private var arguments: Map<String, NavArgument>? = null
        private var graphBuilder: NavGraphBuilder? = null
        private var navController: NavController? = null
        private lateinit var type: Type

        fun toGraphTree(): Builder = this.apply {
            this.type = Type.GRAPH
        }

        fun toDynamic(): Builder = this.apply {
            this.type = Type.DYNAMIC
        }

        fun withArguments(args: Map<String, NavArgument>): Builder = this.apply {
            this.arguments = args
        }

        fun withNavController(navController: NavController): Builder = this.apply {
            this.navController = navController
        }

        fun withNavGraph(navGraphBuilder: NavGraphBuilder): Builder = this.apply {
            this.graphBuilder = navGraphBuilder
        }

        fun build(): NavigationAction {
            return NavigationAction(type, graphBuilder, navController, arguments)
        }
    }

    enum class Type {
        GRAPH, LEGACY, DYNAMIC
    }
}