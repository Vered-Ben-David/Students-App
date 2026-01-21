package com.example.myappstudent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myappstudent.databinding.ActivityStudentDetailsBinding
import com.example.myappstudent.model.StudentsRepository

class StudentDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STUDENT_ID = "extra_student_id"
    }

    private lateinit var binding: ActivityStudentDetailsBinding
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Student Details"

        studentId = intent.getStringExtra(EXTRA_STUDENT_ID)
        if (studentId == null) {
            Toast.makeText(this, "Missing student id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.btnEdit.setOnClickListener {
            val i = Intent(this, EditStudentActivity::class.java)
            i.putExtra(EditStudentActivity.EXTRA_OLD_ID, studentId)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        renderStudent()
    }

    private fun renderStudent() {
        val id = studentId ?: return
        val s = StudentsRepository.findById(id)
        if (s == null) {
            Toast.makeText(this, "Student not found (maybe deleted)", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val uriStr = s.imageUri
        if (!uriStr.isNullOrBlank()) {
            binding.imgStudent.setImageURI(Uri.parse(uriStr))
        } else {
            binding.imgStudent.setImageResource(s.defaultImageResId)
        }

        binding.txtNameValue.text = s.name
        binding.txtIdValue.text = s.id
        binding.txtPhoneValue.text = if (s.phone.isBlank()) "-" else s.phone
        binding.txtAddressValue.text = if (s.address.isBlank()) "-" else s.address
        binding.txtCheckedValue.text = if (s.isChecked) "Yes" else "No"
      
        studentId = s.id
    }
}

