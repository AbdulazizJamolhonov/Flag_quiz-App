package com.example.flag_quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var flagArrayList: ArrayList<Flag>
    private lateinit var buttonArrayList: ArrayList<Button>
    private var count = 0
    private var countryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonArrayList = ArrayList()

        obyektYaratish()
        btnJoylaCount()
    }

    private fun obyektYaratish() {
        flagArrayList = ArrayList()
        flagArrayList.add(Flag("usa", R.drawable.usa))
        flagArrayList.add(Flag("brazil", R.drawable.brazil))
        flagArrayList.add(Flag("turkey", R.drawable.turkey))
        flagArrayList.add(Flag("russian", R.drawable.russia))
        flagArrayList.add(Flag("england", R.drawable.england))
        flagArrayList.add(Flag("germany", R.drawable.germany))
        flagArrayList.add(Flag("uzbekistan", R.drawable.uzb))
    }

    private fun btnJoylaCount() {
        image.setImageResource(flagArrayList[count].image!!)
        lin_1_matn.removeAllViews()
        lin_2_btn_1.removeAllViews()
        lin_3_btn_2.removeAllViews()
        countryName = ""
        btnJoyla(flagArrayList[count].name!!)
    }

    private fun btnJoyla(countryName: String) {
        val btnArray: ArrayList<Button> = randomBtn(countryName)
        for (i in 0..5) {
            lin_2_btn_1.addView(btnArray[i])
        }
        for (i in 6..11) {
            lin_3_btn_2.addView(btnArray[i])
        }
    }

    private fun randomBtn(countryName: String?): ArrayList<Button> {
        val array = ArrayList<Button>()
        val arrayText = ArrayList<String>()
        for (c in countryName!!) {
            arrayText.add(c.toString())
        }
        if (arrayText.size != 12) {
            val str = "ABCDEFGHIJKLMNOPQRSTUVXYZ"
            for (i in arrayText.size until 12) {
                val random = Random().nextInt(str.length)
                arrayText.add(str[random].toString())
            }
        }
        arrayText.shuffle()
        for (i in 0 until arrayText.size) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            button.text = arrayText[i]
            button.setOnClickListener(this)
            array.add(button)
        }
        return array
    }

    override fun onClick(v: View?) {
        val button1 = v as Button
        if (buttonArrayList.contains(button1)) {
            lin_1_matn.removeView(button1)
            var hasC = false
            lin_2_btn_1.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    countryName = countryName.substring(0, countryName.length - 1)
                    hasC = true
                }
            }
            lin_3_btn_2.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    if (!hasC) {
                        countryName = countryName.substring(0, countryName.length - 1)
                    }
                }
            }
        } else {
            button1.visibility = View.INVISIBLE
            countryName += button1.text.toString().uppercase(Locale.getDefault())
            val button2 = Button(this)
            button2.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            button2.text = button1.text
            button2.setOnClickListener(this)
            buttonArrayList.add(button2)
            lin_1_matn.addView(button2)
            matnTogri()
        }
    }

    private fun matnTogri() {
        if (countryName == flagArrayList[count].name?.uppercase(Locale.getDefault())) {
            Toast.makeText(this, "True", Toast.LENGTH_SHORT).show()
            if (count == flagArrayList.size - 1) {
                count = 0
            } else {
                count++
            }
            btnJoylaCount()
        } else {
            if (countryName.length == flagArrayList[count].name?.length) {
                Toast.makeText(this, "False", Toast.LENGTH_SHORT).show()
                if (health.text != "1") {
                    val a = health.text.toString().toInt() - 1
                    health.text = "$a"
                } else {
                    finish()
                }
                lin_1_matn.removeAllViews()
                lin_2_btn_1.removeAllViews()
                lin_3_btn_2.removeAllViews()
                btnJoyla(flagArrayList[count].name!!)
                countryName = ""
            }
        }
    }

}