package forest.zykov.speech_directory

import android.content.Intent
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

    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }


    fun onClickNew(view: View) {
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)


    }


}
