plugins {
  alias(libs.plugins.divinelink.android.library)
  alias(libs.plugins.divinelink.android.library.compose)
}

android {
  namespace = "com.andreolas.core.designsystem"
}

dependencies {
  api(libs.compose.material)
  api(libs.compose.material3)
  api(libs.compose.runtime)

  implementation(libs.androidx.core.ktx)
}
