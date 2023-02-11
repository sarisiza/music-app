package com.example.musicapp.utils

/**
 * Creating custom exceptions
 */

/**
 * Throws when Response is null
 */
class NullSongsResponse(message: String = "Songs response is null"): Exception(message)

/**
 * Throws when the network connection failed
 */
class FailureResponse(message: String?): Exception(message)

/**
 * Throws when there is an Incorrect Query
 */
class IncorrectQuery(message: String = "Query not found"): Exception(message)