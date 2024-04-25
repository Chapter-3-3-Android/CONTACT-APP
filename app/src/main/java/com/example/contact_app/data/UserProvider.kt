package com.example.contact_app.data.model

import android.icu.text.Transliterator.Position
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

    fun addSchedule(userIndex: Int, newSchedule: Schedule) {
        _users[userIndex] = _users[userIndex].copy(
            schedule = newSchedule
        )
    }
    // MyPageFragment, ContactDetailFragment 수정 기능
//    fun modifyUser() {
//        _users
//    }

    // MyPageFragment, ContactDetailFragment 삭제 기능
//    fun deleteUser(position: Int) {
//        _users.removeAt(position)
//    }

    fun switchFavoriteByUser(index: Int) {
        _users[index] = _users[index].copy(
            isFavorite = !(_users[index].isFavorite)
        )
    }

    private fun createDummyUsers(): MutableList<User> {
        val users = mutableListOf<User>()
        val firstNames = listOf("James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda", "William", "Elizabeth")
        val lastNames = listOf("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor")
        val images = listOf(
            R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4, R.drawable.profile5,
            R.drawable.profile6, R.drawable.profile7,R.drawable.profile8, R.drawable.profile9, R.drawable.profile10
        )
        val blogLinks = listOf(
            "https://rlaxodud214.tistory.com/",
            "https://muk-clouds.tistory.com/",
            "https://nochfm0513.tistory.com/",
            "https://alwayszzurie.tistory.com/",
            "https://jihyung1997.tistory.com/"
        )
        val gitLinks = listOf(
            "https://github.com/rlaxodud214",
            "https://github.com/BanDalKang",
            "https://github.com/seoyoung19920921",
            "https://github.com/cococo35",
            "https://github.com/jihyung97"
        )

        for (i in 1..30) {
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val phoneNumber = "+233 ${(1000..9999).random()} ${(10..99).random()} ${(100..999).random()}"
            val email = "${firstName.lowercase()}${lastName.lowercase()}${i}@example.com"
            val profileImage = images.random()
            val blogLink = blogLinks.random()
            val gitLink = gitLinks.random()

            val user = User(
                name = "$firstName $lastName",
                phoneNumber = phoneNumber,
                profileImage = Image.ImageDrawable(profileImage),
                email = email
                //적용안되는 문제 해결해야됨
                //blogLink = blogLink
                //githubLink = gitLink
            )
            users.add(user)
        }
        return users
    }
}
