package com.example.myappstudent.model

data class Student(
    var id: String,
    var name: String,
    var phone: String,
    var address: String,
    var isChecked: Boolean = false,
    var imageUri: String? = null,
    val defaultImageResId: Int
)

