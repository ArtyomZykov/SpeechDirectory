package forest.zykov.speech_directory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import forest.zykov.speech_directory.db.Adapter
import forest.zykov.speech_directory.db.MyDbManager
import kotlinx.android.synthetic.main.library_activity.*

class LibraryActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    val LibAdapter = Adapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_activity)
        init()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        fillAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickBack(view: View) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    fun init() {
        rcView.layoutManager = LinearLayoutManager(this)
        rcView.adapter = LibAdapter
    }

    fun fillAdapter() {
        val list = myDbManager.readDbData()
        LibAdapter.updateAdapter(list)
        if (list.size > 0 ) tvNoElements.visibility = View.GONE
        else tvNoElements.visibility = View.VISIBLE
    }

}