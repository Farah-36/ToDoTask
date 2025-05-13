package eu.tutorial.todolist

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eu.tutorial.todolist.R.id.taskContainer

class MainActivity : AppCompatActivity() {

    private val taskList = mutableListOf<Task>()
    private lateinit var taskContainer: LinearLayout
    private lateinit var fabAddTask: FloatingActionButton

    data class Task(
        val title: String,
        val description: String,
        val dueDate: String,
        val priority: String,
        var isCompleted: Boolean = false
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        taskContainer = findViewById(R.id.taskContainer)
        fabAddTask = findViewById(R.id.fabAddTask)

        fabAddTask.setOnClickListener { showAddTaskDialog() }
    }

    private fun refreshTaskList() {
        taskContainer.removeAllViews()

        for (task in taskList) {
            val checkBox = CheckBox(this).apply {
                text = buildString {
                    append(task.title)
                    append("\nDue: ${task.dueDate}")
                    append(" • Priority: ${task.priority}")
                    if (task.description.isNotEmpty()) append("\n${task.description}")
                }
                isChecked = task.isCompleted
                setTextColor(getPriorityColor(task.priority))
                textSize = 16f
                setPadding(0, 16, 0, 16)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8)
                }

                setOnCheckedChangeListener { _, isChecked ->
                    task.isCompleted = isChecked
                }
            }
            taskContainer.addView(checkBox)
        }
    }

    private fun getPriorityColor(priority: String): Int {
        return when (priority.lowercase()) {
            "high" -> Color.RED
            "medium" -> Color.parseColor("#FFA500")
            "low" -> Color.GREEN
            else -> Color.BLACK
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val editTitle = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val editDescription = dialogView.findViewById<EditText>(R.id.editTextDescription)
        val editDueDate = dialogView.findViewById<EditText>(R.id.editTextDueDate)
        val spinnerPriority = dialogView.findViewById<Spinner>(R.id.spinnerPriority)
        val checkBoxCompleted = dialogView.findViewById<CheckBox>(R.id.checkBoxCompleted)

        // Set up spinner
        val priorities = arrayOf("Low", "Medium", "High")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priorities)
        spinnerPriority.adapter = adapter

        AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                if (validateInput(editTitle, editDueDate)) {
                    val newTask = Task(
                        title = editTitle.text.toString(),
                        description = editDescription.text.toString(),
                        dueDate = editDueDate.text.toString(),
                        priority = spinnerPriority.selectedItem.toString(),
                        isCompleted = checkBoxCompleted.isChecked
                    )
                    taskList.add(newTask)
                    refreshTaskList()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun validateInput(title: EditText, dueDate: EditText): Boolean {
        var isValid = true

        if (title.text.toString().trim().isEmpty()) {
            title.error = "Title is required"
            isValid = false
        }

        if (!dueDate.text.toString().matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
            dueDate.error = "Invalid date format (YYYY-MM-DD)"
            isValid = false
        }

        return isValid
        }
}