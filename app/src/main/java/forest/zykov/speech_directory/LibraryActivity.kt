package forest.zykov.speech_directory

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        initSearchView()
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

    private fun init() {
        rcView.layoutManager = LinearLayoutManager(this)
        val swapHelper = getSwatMg()
        swapHelper.attachToRecyclerView(rcView)
        rcView.adapter = LibAdapter
    }

    private fun initSearchView() {
        val id = searchView.context.resources
            .getIdentifier("android:id/search_src_text", null, null)
        val textView = searchView.findViewById(id) as TextView
        textView.setTextColor(Color.rgb(74, 74, 74))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val list = myDbManager.readDbData(newText!!)
                LibAdapter.updateAdapter(list)
                return true
            }
        })
    }

    private fun fillAdapter() {
        val list = myDbManager.readDbData("")
        LibAdapter.updateAdapter(list)
        if (list.size > 0 ) tvNoElements.visibility = View.GONE
        else tvNoElements.visibility = View.VISIBLE
    }

    private fun getSwatMg() : ItemTouchHelper {
        return ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                LibAdapter.removeItem(viewHolder.adapterPosition, myDbManager)
            }
        })
    }
}