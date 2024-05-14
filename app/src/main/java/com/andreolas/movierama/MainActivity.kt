package com.andreolas.movierama

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.andreolas.core.designsystem.theme.AppTheme
import com.andreolas.core.designsystem.theme.Theme
import com.andreolas.movierama.destinations.HomeScreenDestination
import com.andreolas.movierama.home.ui.HomeScreen
import com.andreolas.movierama.home.ui.LoadingContent
import com.andreolas.movierama.ui.components.snackbar.controller.ProvideSnackbarController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.spec.Route
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      val snackbarHostState = remember { SnackbarHostState() }
      val coroutineScope = rememberCoroutineScope()

      AppTheme(
        useDarkTheme = viewModel.theme.collectAsState().value == Theme.DARK,
        dynamicColor = viewModel.materialYou.collectAsState().value,
        blackBackground = viewModel.blackBackgrounds.collectAsState().value,
      ) {
        ProvideSnackbarController(
          snackbarHostState = snackbarHostState,
          coroutineScope = coroutineScope
        ) {
          Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
            val mainState = viewModel.viewState.collectAsState()

            when (mainState.value) {
              MainViewState.Completed -> {
                AppNavHost(
                  startRoute = HomeScreenDestination,
                )
              }
              MainViewState.Loading -> {
                LoadingContent()
              }
            }
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
private fun AppNavHost(
  startRoute: Route,
) {
  DestinationsNavHost(
    startRoute = startRoute,
    navGraph = NavGraphs.root,
    engine = rememberAnimatedNavHostEngine(
      rootDefaultAnimations = RootNavGraphDefaultAnimations(
        enterTransition = {
          slideInHorizontally()
        },
        exitTransition = {
          fadeOut()
        },
      ),
    ),
    manualComposableCallsBuilder = {
      composable(HomeScreenDestination) {
        HomeScreen(
          navigator = destinationsNavigator,
        )
      }
    }
  )
}
