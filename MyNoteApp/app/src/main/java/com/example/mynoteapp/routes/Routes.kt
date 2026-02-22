package com.example.mynoteapp.routes


import com.example.mynoteapp.commons.DetailScreenType
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
data class DetailScreenRoute(val noteId: Int?, val screenType: DetailScreenType)