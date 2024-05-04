package com.andreolas.factories.session.model

import com.andreolas.movierama.session.model.RequestToken

object RequestTokenFactory {

  fun full(): RequestToken = RequestToken(
    token = "968f4b1a-593f-4fee-98e8-09a051c5c522",
    expiresAt = "2021-09-30 00:00:00"
  )
}
