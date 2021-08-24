package forest.zykov.speech_directory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import forest.zykov.speech_directory.db.IntentConstants
import forest.zykov.speech_directory.db.MyDbManager
import kotlinx.android.synthetic.main.edit_activity.*

class EditActivity : AppCompatActivity() {
    var id = 0
    var isEditState = false
    private val myDbManager = MyDbManager(this)
    private val ifNewWord = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        getIntents()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickBack(view: View) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    fun onClickSave(view: View) {

        val myWord = edWord.text.toString()
        val myTranslation = edTranslation.text.toString()
        if (myWord != "" && myTranslation != "") {
            if (isEditState) {
                Log.d("MyLog", "Values: " + ifNewWord + " " + myWord + " " + myTranslation + " " + id)
                myDbManager.updateItem(ifNewWord, myWord, myTranslation, id)
            }
            else {
                myDbManager.insertToDb(ifNewWord, myWord, myTranslation)
            }
            finish()
            val toast = Toast.makeText(this, "Запись успешно сохранена", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun getIntents() {
        val i = intent
        if (i != null) {
            Log.d("MyLog","I_WORD_KEY : " + i.getStringExtra(IntentConstants.I_WORD_KEY))
            if (i.getStringExtra(IntentConstants.I_WORD_KEY) != null) {
                Log.d("MyLog", "+++++++++")
                edWord.setText(i.getStringExtra(IntentConstants.I_WORD_KEY))
                Log.d("MyLog", "isEditState : " + isEditState)
                isEditState = true
                edTranslation.setText(i.getStringExtra(IntentConstants.I_TRANSLATION_KEY))
                id = i.getIntExtra(IntentConstants.I_ID_KEY, 0)
                Log.d("MyLog", "id: " + id)
            }
        }
    }
}
