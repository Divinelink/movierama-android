package com.divinelink.core.commons.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

  /** Executes the use case asynchronously and returns a [Result].
   *
   * @return a [Result].
   *
   * @param parameters the input parameters to run the use case with
   */
  suspend operator fun invoke(parameters: P): Result<R> = try {
    // Moving all use case's executions to the injected dispatcher
    // In production code, this is usually the Default dispatcher (background thread)
    // In tests, this becomes a TestCoroutineDispatcher
    withContext(coroutineDispatcher) {
      execute(parameters).let {
        Result.success(it)
      }
    }
  } catch (e: Exception) {
    Timber.d(e)
    Result.failure(e)
  }

  /**
   * Override this to set the code to be executed.
   */
  @Throws(RuntimeException::class)
  protected abstract suspend fun execute(parameters: P): R
}
