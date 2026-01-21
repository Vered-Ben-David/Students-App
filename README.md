Students App – Android - Assignment 2
**Overview**
This project is a simple Android application for managing a list of students.
The app allows adding new students, viewing student details, editing existing students, and displaying all students in a list using a RecyclerView.
The data is stored in memory only (no real database), as required by the assignment.
All data is erased when the app is closed.
**Features**
1. Display a list of students
2. Add a new student
3. View student details
4. Edit or delete an existing student
5. Mark students as checked / unchecked
6. Static student image for all students
**Screens**
1. Students List
2. New Student
3. Student Details
4. Edit Student
Each screen is implemented as a separate Activity.
**Architecture**
Language: Java / Kotlin
UI: XML layouts
Data storage: In-memory repository (no persistent DB)
List implementation: RecyclerView
Project Structure
Activities – All screens logic
Adapter – RecyclerView adapter and ViewHolder
Model – Student entity
Repository – In-memory data management
Resources – Layouts, images, values (strings, colors, themes)
**Team Work**
This project is developed collaboratively.
Responsibilities are divided as follows:
**Logic & Code**
1. Activities
2. RecyclerView Adapter
3. Model & Repository
4. App logic
**UI & Resources**
1. XML layouts
2. Drawables & images
3. Values (strings, colors, themes)
4. Visual design

**How to Run**
Clone the repository
Open the project in Android Studio
Let Gradle sync finish
Run the app on an emulator or physical device

**Notes**
No real database is used – all data is stored in memory
All screens are implemented using Activities
All students use the same static image
