package forest.zykov.speech_directory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import forest.zykov.speech_directory.db.MyDbManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ThreadLocalRandom

class MainActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        val dataList = myDbManager.readDbData()
        for (item in dataList) {
            tvTest.append(item)
            tvTest.append("\n")
        }
    }


    fun onClickSave(view: View) {
        tvTest.text = ""
        val i = ThreadLocalRandom.current().nextInt() % 10 * -1
        myDbManager.insertToDb(i.toString(), edWord.text.toString(), edTranslation.text.toString())
        val dataList = myDbManager.readDbData()
        for (item in dataList) {
            tvTest.append(item)
            tvTest.append("\n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
}
