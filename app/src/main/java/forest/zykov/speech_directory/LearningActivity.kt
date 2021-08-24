package forest.zykov.speech_directory

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import forest.zykov.speech_directory.db.Adapter
import forest.zykov.speech_directory.db.DbElem
import forest.zykov.speech_directory.db.MyDbManager
import kotlinx.android.synthetic.main.learning_activity.*
import kotlinx.android.synthetic.main.library_activity.*
import kotlin.random.Random

class LearningActivity : AppCompatActivity() {

    private val myDbManager = MyDbManager(this)
    private var list: ArrayList<DbElem> = ArrayList()
    private var parsList: ArrayList<DbElem> = ArrayList()
    private var listFalseTranslation: ArrayList<String> = ArrayList()
    private var listKey: ArrayList<Boolean>? = ArrayList()
    var upFlag: Boolean = false
    var downFlag: Boolean = false
    var buttonBlock = false
    var index: Int = 0

    private var mainText: TextView? = null
    private var textVar1: TextView? = null
    private var textVar2: TextView? = null
    private var buttonNext: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.learning_activity)


        mainText = findViewById(R.id.mainText)
        textVar1 = findViewById(R.id.textVar1)
        textVar2 = findViewById(R.id.textVar2)

        buttonNext = findViewById(R.id.next_button)
        buttonNext?.visibility = View.GONE

        myDbManager.openDb()
        parsList = myDbManager.readDbData("")


        preparingArray()
        updatePage()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    private fun preparingArray() {
        for (i in 0 until parsList.size) {
            if (parsList[i].level < 3) {
                list.add(parsList[i])
            }
        }
        list.sortBy { it.level }
        for (i in 0 until list.size) {
            listKey?.add(Random.nextBoolean())
            var randNum = Random.nextInt(0, list.size)
            while (randNum == i) {
                randNum = Random.nextInt(0, list.size)
            }
            listFalseTranslation.add(list[randNum].translation)

        }
    }

    fun onClickUp(view: View) {
        if (!buttonBlock) {
            upFlag = true
            paintButton()
            buttonBlock = true
            buttonNext?.visibility = View.VISIBLE
        }
    }

    fun onClickDown(view: View) {
        if (!buttonBlock) {
            downFlag = true
            paintButton()
            buttonBlock = true
            buttonNext?.visibility = View.VISIBLE
        }
    }

    fun onClickNext(view: View) {
        textVar1?.setBackgroundResource(R.drawable.button_var_1)
        textVar2?.setBackgroundResource(R.drawable.button_var_1)
        updatePage()
        buttonBlock = false
        buttonNext?.visibility = View.GONE
    }

    fun onClickBack(view: View) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun updatePage() {
        if (index < list.size) {
            if (!upFlag && !downFlag) { // Первый старт
                if (list[index].word.length >= 10) {
                    mainText?.textSize = 40F
                } else {
                    mainText?.textSize = 60F
                }
                mainText!!.text = list[index].word
                if (listKey?.get(index) == true) { // First btn is true
                    textVar1!!.text = list[index].translation
                    textVar2!!.text = listFalseTranslation[index]
                } else {                // Second btn is true
                    textVar1!!.text = listFalseTranslation[index]
                    textVar2!!.text = list[index].translation
                }
            } else {    // Проверяем правильность предыдущего хода, затем меняем вью под новое слово
                checkingTrue()
                // Закончили проверку
                if (list[index].word.length >= 10) {
                    mainText?.textSize = 40F
                } else {
                    mainText?.textSize = 60F
                }
                mainText!!.text = list[index].word
                if (listKey?.get(index) == true) { // First btn is true
                    textVar1!!.text = list[index].translation
                    textVar2!!.text = listFalseTranslation[index]
                } else {                // Second btn is true
                    textVar1!!.text = listFalseTranslation[index]
                    textVar2!!.text = list[index].translation
                }
                upFlag = false
                downFlag = false
            }
            index += 1
        } else {
            // END
            if (index == list.size) {
                checkingTrue()
            }
            val i = Intent(this, LibraryActivity::class.java)
            startActivity(i)
            //mainText!!.text = "END"
        }
    }


    private fun paintButton() {
        if (upFlag) {
            if (listKey!![index - 1]) { // true
                textVar1?.setBackgroundResource(R.drawable.button_true_var)
            } else { // false
                textVar1?.setBackgroundResource(R.drawable.button_false_var)
            }
        }
        if (downFlag) {
            if (!listKey!![index - 1]) { // true
                textVar2?.setBackgroundResource(R.drawable.button_true_var)
            } else { // false
                textVar2?.setBackgroundResource(R.drawable.button_false_var)
            }
        }
    }

    private fun checkingTrue() {
        if (upFlag) {
            if (listKey!![index - 1]) { // true
                if (list[index - 1].level < 3) {
                    myDbManager.updateItem(
                        list[index - 1].level + 1,
                        list[index - 1].word,
                        list[index - 1].translation,
                        list[index - 1].id
                    )
                }
            } else { // false
            }
        }
        if (downFlag) {
            if (!listKey!![index - 1]) { // true
                if (list[index - 1].level < 3) {
                    myDbManager.updateItem(
                        list[index - 1].level + 1,
                        list[index - 1].word,
                        list[index - 1].translation,
                        list[index - 1].id
                    )
                }
            } else { // false
            }
        } // Закончили проверку
    }

}