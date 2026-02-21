package com.example.mynoteapp.routes


import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
data class DetailScreenRoute(val noteId: Int?, val appBarTitle:String)