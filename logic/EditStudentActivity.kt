package com.example.myappstudent

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myappstudent.databinding.ActivityEditStudentBinding
import com.example.myappstudent.model.Student
import com.example.myappstudent.model.StudentsRepository

class EditStudentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_OLD_ID = "extra_old_id"
    }

    private lateinit var binding: ActivityEditStudentBinding
    private var oldId: String? = null
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
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Edit Student"

        oldId = intent.getStringExtra(EXTRA_OLD_ID)
        val id = oldId
        if (id == null) {
            Toast.makeText(this, "Missing student id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val s = StudentsRepository.findById(id)
        if (s == null) {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.etName.setText(s.name)
        binding.etId.setText(s.id)
        binding.etPhone.setText(s.phone)
        binding.etAddress.setText(s.address)
        binding.chkChecked.isChecked = s.isChecked

        // תמונה קיימת
        if (s.imageUri != null) {
            binding.imgPreview.setImageURI(Uri.parse(s.imageUri))
        } else {
            binding.imgPreview.setImageResource(s.defaultImageResId)
        }

        binding.btnChooseImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            val newName = binding.etName.text.toString().trim()
            val newId = binding.etId.text.toString().trim()
            val newPhone = binding.etPhone.text.toString().trim()
            val newAddress = binding.etAddress.text.toString().trim()
            val newChecked = binding.chkChecked.isChecked

            if (newName.isEmpty() || newId.isEmpty()) {
                Toast.makeText(this, "Please enter name and id", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newId != id && StudentsRepository.findById(newId) != null) {
                Toast.makeText(this, "ID already exists", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updated = Student(
                id = newId,
                name = newName,
                phone = newPhone,
                address = newAddress,
                isChecked = newChecked,
                imageUri = (selectedImageUri?.toString() ?: s.imageUri),
                defaultImageResId = s.defaultImageResId   // <-- תיקון כאן
            )

            val ok = StudentsRepository.update(id, updated)
            if (!ok) {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            finish()
        }

        binding.btnDelete.setOnClickListener {
            StudentsRepository.deleteById(id)
            finish()
        }
    }
}

