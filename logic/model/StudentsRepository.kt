package com.example.myappstudent.model

object StudentsRepository {

    private val students = mutableListOf<Student>()

    fun getAll(): List<Student> = students

    fun add(student: Student) {
        students.add(student)
    }

    fun findById(id: String): Student? {
        return students.firstOrNull { it.id == id }
    }

    fun deleteById(id: String): Boolean {
        return students.removeIf { it.id == id }
    }

    fun update(oldId: String, updated: Student): Boolean {
        val index = students.indexOfFirst { it.id == oldId }
        if (index == -1) return false
        students[index] = updated
        return true
    }

    fun toggleChecked(id: String): Boolean {
        val s = findById(id) ?: return false
        s.isChecked = !s.isChecked
        return true
    }
}

