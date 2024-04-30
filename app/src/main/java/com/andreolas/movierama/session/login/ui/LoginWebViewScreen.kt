package com.andreolas.movierama.session.login.ui

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.andreolas.movierama.R
import com.andreolas.movierama.settings.components.SettingsScaffold
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(navArgsDelegate = LoginScreenArgs::class)
@SuppressLint("SetJavaScriptEnabled")
fun LoginWebViewScreen(
  navigator: DestinationsNavigator,
  viewModel: LoginWebViewViewModel = hiltViewModel()
) {
  val context = LocalContext.current

//  val scope = rememberCoroutineScope()
//  val snackbarHostState = remember { SnackbarHostState() }

  val viewState = viewModel.viewState.collectAsState()

  LaunchedEffect(viewState.value.navigateBack) {
    if (viewState.value.navigateBack) {
      navigator.navigateUp()
    }
  }

//  LaunchedEffect(key1 = viewState.value.toastText) {
//    if (viewState.value.toastText != null) {
//      scope.launch {
//        snackbarHostState.showSnackbar(
//          message = viewState.value.toastText.toString()
//        )
//      }
//    }
//  }

  val webView = remember {
    WebView(context).apply {
      settings.javaScriptEnabled = true
      settings.domStorageEnabled = true
      webViewClient = AutoRedirectWebView(
        redirectUrl = viewState.value.redirectUrl,
        onCloseWebView = { successUrl ->
          viewModel.handleSession(successUrl)
        }
      )
    }
  }

  SettingsScaffold(
    title = stringResource(R.string.login__title),
    onNavigationClick = navigator::navigateUp
  ) { paddingValues ->
    AndroidView(
      modifier = Modifier
        .windowInsetsPadding(WindowInsets.statusBars)
        .padding(paddingValues)
        .fillMaxSize(),
      factory = { webView },
      update = { it.loadUrl(viewState.value.url) }
    )
  }

  BackHandler(enabled = webView.canGoBack()) {
    webView.goBack()
  }
}
