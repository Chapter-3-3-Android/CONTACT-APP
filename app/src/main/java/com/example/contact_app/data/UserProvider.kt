package com.example.contact_app.data.model

import com.example.contact_app.R

object UserProvider {
    private var _users: MutableList<User>
    val users: List<User> get() = _users

    init {
        _users = createDummyUsers()
    }

    fun addUser(user: User) {
        _users.add(user)
    }

    fun switchFavoriteByUser(index: Int) {
        _users[index] = _users[index].copy(
            isFavorite = !(_users[index].isFavorite)
        )
    }

    private fun createDummyUsers(): MutableList<User> {
        val users = mutableListOf<User>()
        val firstNames = listOf("James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda", "William", "Elizabeth")
        val lastNames = listOf("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor")

        for (i in 1..30) {
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val phoneNumber = "+233 ${(1000..9999).random()} ${(10..99).random()} ${(100..999).random()}"
            val email = "${firstName.lowercase()}${lastName.lowercase()}${i}@example.com"

            val user = User(
                name = "$firstName $lastName",
                phoneNumber = phoneNumber,
                profileImage = Image.ImageDrawable(R.drawable.ic_user),
                email = email
            )
            users.add(user)
        }
        return users
    }
}