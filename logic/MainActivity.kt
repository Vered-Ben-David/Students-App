package com.example.myappstudent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappstudent.databinding.ActivityMainBinding
import com.example.myappstudent.model.Student
import com.example.myappstudent.model.StudentsRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Students List"

        seedIfNeeded()

        adapter = StudentsAdapter(
            studentsProvider = { StudentsRepository.getAll() },
            onRowClick = { student ->
                val i = Intent(this, StudentDetailsActivity::class.java)
                i.putExtra(StudentDetailsActivity.EXTRA_STUDENT_ID, student.id)
                startActivity(i)
            },
            onToggleCheck = { student ->
                StudentsRepository.toggleChecked(student.id)
                adapter.notifyDataSetChanged()
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btnAddStudent.setOnClickListener {
            startActivity(Intent(this, NewStudentActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun seedIfNeeded() {
        if (StudentsRepository.getAll().isNotEmpty()) return

        StudentsRepository.add(
            Student(
                id = "209344678",
                name = "Noa Aviv",
                phone = "052-6786585",
                address = "Tel Aviv",
                isChecked = false,
                imageUri = null,
                defaultImageResId = R.drawable.ic_student
            )
        )

        StudentsRepository.add(
            Student(
                id = "209344675",
                name = "Amit Yagel",
                phone = "052-6786584",
                address = "Jerusalem",
                isChecked = true,
                imageUri = null,
                defaultImageResId = R.drawable.ic_student
            )
        )
    }
}

