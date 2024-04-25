package com.example.contact_app.data.model

data class User(
    val name: String,
    val phoneNumber: String,
    val profileImage: Image,
    val email: String,
    val blogLink: String? = null,
    val githubLink: String? = null,
    val schedule: Schedule? = null,
    val isFavorite: Boolean = false,
)

