package com.divinelink.core.ui

object TestTags {

  const val LOADING_CONTENT_TAG = "LOADING_CONTENT_TAG"
  const val MOVIES_LIST_TAG = "MOVIES_LIST_TAG"
  const val SCROLL_TO_TOP_BUTTON = "SCROLL_TO_TOP_BUTTON_TAG"
  const val LOADING_PROGRESS = "Loading Progress Bar"

  object Details {
    const val YOUR_RATING = "Details Your Rating"
    const val RATE_DIALOG = "Details Rate Dialog"
    const val RATE_SLIDER = "Details Rate Slider"
  }

  object Dialogs {
    const val ALERT_DIALOG = "Dialogs Alert Dialog"
    const val CONFIRM_BUTTON = "Dialog Confirm Button"
    const val DISMISS_BUTTON = "Dialog Dismiss Button"
  }

  object Settings {
    const val TOP_APP_BAR = "Settings Top App Bar"
    const val NAVIGATION_ICON = "Settings Navigation Icon"

    object Account {
      const val LOGIN_BUTTON = "Account Login Button"
      const val LOGOUT_BUTTON = "Account Logout Button"
    }

    object Jellyseerr {
      const val INITIAL_BOTTOM_SHEET = "Jellyseerr Initial Bottom Sheet"
      const val LOGGED_IN_BOTTOM_SHEET = "Jellyseerr Logged In Bottom Sheet"
      const val ADDRESS_TEXT_FIELD = "Jellyseerr Address Text Field"

      const val JELLYFIN_EXPANDABLE_CARD_BUTTON = "Jellyfin Expandable Card"
      const val JELLYFIN_USERNAME_TEXT_FIELD = "Jellyfin Username"
      const val JELLYFIN_PASSWORD_TEXT_FIELD = "Jellyfin Password"

      const val JELLYSEERR_EXPANDABLE_CARD_BUTTON = "Jellyseerr Expandable Card"
      const val JELLYSEERR_USERNAME_TEXT_FIELD = "Jellyseerr Username"
      const val JELLYSEERR_PASSWORD_TEXT_FIELD = "Jellyseerr Password"

      const val JELLYSEERR_LOGIN_BUTTON = "Jellyseerr Login Button"
      const val JELLYSEERR_LOGOUT_BUTTON = "Jellyseerr Logout Button"
    }
  }

  object Menu {
    const val MENU_BUTTON_VERTICAL = "Menu Button Vertical"
    const val DROPDOWN_MENU = "Dropdown Menu"
    const val MENU_ITEM = "Menu Item %s"
  }

  object Watchlist {
    const val TAB_BAR = "Watchlist Tab Bar $%s"
    const val WATCHLIST_CONTENT = "Watchlist Content with data"
    const val WATCHLIST_SCREEN = "Watchlist Screen"
    const val WATCHLIST_ERROR_CONTENT = "Watchlist Error Content"
  }
}
