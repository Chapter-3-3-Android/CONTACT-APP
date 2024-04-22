package com.example.contact_app.data.model

import java.time.LocalTime

data class Schedule(
    val name: String,
    val time: LocalTime,
    val remindTime: LocalTime?,
)
