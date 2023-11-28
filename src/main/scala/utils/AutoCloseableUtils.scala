package com.volodya262
package utils

object AutoCloseableUtils {
  def using[T <: AutoCloseable, R](closeableResource: T, func: T => R): R = {
    try {
      func(closeableResource)
    } finally {
      closeableResource.close()
    }
  }
}
