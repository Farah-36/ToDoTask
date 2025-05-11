package eu.tutorial.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var checkBoxOne: CheckBox
    lateinit var checkBoxTwo: CheckBox
    lateinit var checkBoxThree: CheckBox
    lateinit var checkBoxFour: CheckBox
    lateinit var checkBoxFive: CheckBox
    lateinit var checkBoxSix: CheckBox
    lateinit var checkBoxSeven: CheckBox
    lateinit var myButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBoxOne = findViewById(R.id.checkBox1)
        checkBoxTwo = findViewById(R.id.checkBox2)
        checkBoxThree = findViewById(R.id.checkBox3)
        checkBoxFour = findViewById(R.id.checkBox4)
        checkBoxFive = findViewById(R.id.checkBox5)
        checkBoxSix = findViewById(R.id.checkBox6)
        checkBoxSeven = findViewById(R.id.checkBox7)


        myButton = findViewById(R.id.myButton)
        myButton.setOnClickListener {
            checkBoxOne.isChecked = true
        }

        checkBoxOne.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

            } else {

            }
        }

        checkBoxTwo.setOnCheckedChangeListener { _, isChecked ->
        }

    }
}
