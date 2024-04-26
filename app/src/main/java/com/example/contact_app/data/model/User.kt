package com.example.contact_app.data.model

data class User(
    val name: String,
    val phoneNumber: String,
    val profileImage: Image,
    val email: String,
    val blogLink: String? = "https://muk-clouds.tistory.com/",
    val githubLink: String? = "https://github.com/BanDalKang",
    val schedule: Schedule? = null,
    val isFavorite: Boolean = false,
)

