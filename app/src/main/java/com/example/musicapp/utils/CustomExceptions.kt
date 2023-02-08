package com.example.musicapp.utils

class NullSongsResponse(message: String = "Songs response is null"): Exception(message)
class FailureResponse(message: String?): Exception(message)

class IncorrectQuery(message: String = "Query not found"): Exception(message)