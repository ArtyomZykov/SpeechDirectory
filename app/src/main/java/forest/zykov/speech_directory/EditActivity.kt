package forest.zykov.speech_directory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import forest.zykov.speech_directory.db.MyDbManager
import kotlinx.android.synthetic.main.edit_activity.*

class EditActivity : AppCompatActivity() {

    val myDbManager = MyDbManager(this)
    val ifNewWord = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()

    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickSave(view: View) {
        val myWord = edWord.text.toString()
        val myTranslation = edTranslation.text.toString()
        if (myWord != "" && myTranslation != "") {
            myDbManager.insertToDb(ifNewWord, myWord, myTranslation)
        }

    }
}
