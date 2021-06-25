package forest.zykov.speech_directory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
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
    private var listFalseTranslation: ArrayList<String> = ArrayList()
    private var listKey: ArrayList<Boolean>? = ArrayList()
    var upFlag: Boolean = false
    var downFlag: Boolean = false
    var index: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.learning_activity)
        myDbManager.openDb()
        list = myDbManager.readDbData("")
        

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

    fun onClickBack(view: View) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun preparingArray() {
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
        upFlag = true
        updatePage()
    }

    fun onClickDown(view: View) {
        downFlag = true
        updatePage()
    }

    private fun updatePage() {

        if (index < list.size) {


            if (!upFlag && !downFlag) { // Первый старт
                mainText.text = list[index].word
                if (listKey?.get(index) == true) { // First btn is true
                    textVar1.text = list[index].translation
                    textVar2.text = listFalseTranslation[index]
                } else {                // Second btn is true
                    textVar1.text = listFalseTranslation[index]
                    textVar2.text = list[index].translation
                }
            } else {    // Проверяем правильность предыдущего хода, затем меняем вью под новое слово
                if (upFlag) {
                    //listKey!![index - 1]
                }

                if (downFlag) {

                }// Закончили проверку

                mainText.text = list[index].word

                if (listKey?.get(index) == true) { // First btn is true
                    textVar1.text = list[index].translation
                    textVar2.text = listFalseTranslation[index]
                } else {                // Second btn is true
                    textVar1.text = listFalseTranslation[index]
                    textVar2.text = list[index].translation
                }

                upFlag = false
                downFlag = false
            }


            index += 1
        } else {
            // END
            mainText.text = "END"
        }

    }


}