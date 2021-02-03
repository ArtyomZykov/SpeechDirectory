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

    val myDbManager = MyDbManager(this)
    val ifNewWord = 0

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
            myDbManager.insertToDb(ifNewWord, myWord, myTranslation)
        }
        val toast = Toast.makeText(this, "Запись успешно сохранена", Toast.LENGTH_SHORT);
        toast.show();
    }

    fun getIntents() {
        val i = intent
        if (i != null) {

            if(i.getStringExtra(IntentConstants.I_WORD_KEY) != null)
                //Log.d("MyLog", "Data received : " + i.getStringExtra(IntentConstants.I_LEVEL_KEY))
                edWord.setText(i.getStringExtra(IntentConstants.I_WORD_KEY))
                edTranslation.setText(i.getStringExtra(IntentConstants.I_TRANSLATION_KEY))
        }
    }

}
