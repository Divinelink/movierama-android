package com.divinelink.core.commons.extensions

@Suppress("MagicNumber")
fun Double.round(decimals: Int): Double {
  var multiplier = 1.0
  repeat(decimals) { multiplier *= 10 }
  return kotlin.math.round(this * multiplier) / multiplier
}
