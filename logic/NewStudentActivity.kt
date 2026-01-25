package com.example.myappstudent

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myappstudent.databinding.ActivityNewStudentBinding
import com.example.myappstudent.model.Student
import com.example.myappstudent.model.StudentsRepository

class NewStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewStudentBinding
    private var selectedImageUri: Uri? = null

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedImageUri = uri
                binding.imgPreview.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "New Student"

        binding.btnChooseImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val id = binding.etId.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()

            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(this, "Please enter name and id", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (StudentsRepository.findById(id) != null) {
                Toast.makeText(this, "ID already exists", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            StudentsRepository.add(
                Student(
                    id = id,
                    name = name,
                    phone = phone,
                    address = address,
                    isChecked = false,
                    imageUri = selectedImageUri?.toString(),
                    defaultImageResId = R.drawable.ic_student
                )
            )

            finish()
        }
    }
}

