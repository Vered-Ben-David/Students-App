package com.example.myappstudent

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myappstudent.databinding.RowStudentBinding
import com.example.myappstudent.model.Student

class StudentsAdapter(
    private val studentsProvider: () -> List<Student>,
    private val onRowClick: (Student) -> Unit,
    private val onToggleCheck: (Student) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.StudentVH>() {

    inner class StudentVH(val binding: RowStudentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentVH {
        val binding = RowStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentVH(binding)
    }

    override fun getItemCount(): Int = studentsProvider().size

    override fun onBindViewHolder(holder: StudentVH, position: Int) {
        val student = studentsProvider()[position]

        holder.binding.txtName.text = student.name
        holder.binding.txtId.text = student.id

      val uriStr = student.imageUri
        if (!uriStr.isNullOrBlank()) {
            holder.binding.imgStudent.setImageURI(Uri.parse(uriStr))
        } else {
            holder.binding.imgStudent.setImageResource(student.defaultImageResId)
        }

        holder.binding.chkChecked.setOnCheckedChangeListener(null)
        holder.binding.chkChecked.isChecked = student.isChecked
        holder.binding.chkChecked.setOnCheckedChangeListener { _, _ ->
            onToggleCheck(student)
        }

        holder.binding.root.setOnClickListener {
            onRowClick(student)
        }
    }
}

