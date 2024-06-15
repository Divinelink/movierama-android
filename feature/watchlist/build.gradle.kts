plugins {
  alias(libs.plugins.divinelink.android.feature)
  alias(libs.plugins.divinelink.android.library.compose)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.divinelink.watchlist"
}

ksp {
  arg("compose-destinations.moduleName", "watchlist")

  arg("compose-destinations.htmlMermaidGraph", "$rootDir/docs")
  arg("compose-destinations.mermaidGraph", "$rootDir/docs")

  arg("compose-destinations.codeGenPackageName", "com.divinelink.ui.screens")
}

dependencies {
  implementation(projects.core.commons)
  implementation(projects.core.data)
  implementation(projects.core.datastore)
  implementation(projects.core.domain)
  implementation(projects.core.model)

  implementation(projects.feature.details)

  implementation(libs.timber)
}
