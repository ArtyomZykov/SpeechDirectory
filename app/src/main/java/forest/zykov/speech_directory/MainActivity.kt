package forest.zykov.speech_directory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import forest.zykov.speech_directory.db.MyDbManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ThreadLocalRandom

class MainActivity : AppCompatActivity() {
    //val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickShow(view: View) {
        val i = Intent(this, LibraryActivity::class.java)
        startActivity(i)
    }

    fun initSearchView() {

    }
    fun onClickNew(view: View) {
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)
    }
}
