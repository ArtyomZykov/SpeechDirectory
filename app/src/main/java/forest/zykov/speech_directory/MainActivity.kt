package forest.zykov.speech_directory

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.fragment.app.DialogFragment
import forest.zykov.speech_directory.db.DbElem
import forest.zykov.speech_directory.db.MyDbManager

class MainActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this)
    private var list: ArrayList<DbElem> = ArrayList()
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDbManager.openDb()
        list = myDbManager.readDbData("")
        count = getCountStudyWords(list)
    }

    override fun onResume() {
        super.onResume()
        list = myDbManager.readDbData("")
        count = getCountStudyWords(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickInfo(view: View) {
        val myDialogFragment = MyDialogFragment_3()
        val manager = supportFragmentManager
        myDialogFragment.show(manager, "myDialog")
    }

    private fun getCountStudyWords(list: ArrayList<DbElem>) : Int {
        var count = 0
        for (i in 0 until list.size) {
            if (list[i].level < 3) {
                count++
            }
        }
        return count
    }

    fun onClickSettings(view: View) {}

    fun onClickLearning(view: View) {
        if (list.size <= 3) {
            val myDialogFragment = MyDialogFragment_1()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog1")
        } else if (count <= 3) {
            val myDialogFragment = MyDialogFragment_2()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog2")

        } else {
            val i = Intent(this, LearningActivity::class.java)
            startActivity(i)
        }
    }

    fun onClickShow(view: View) {
        val i = Intent(this, LibraryActivity::class.java)
        startActivity(i)
    }
    fun onClickNew(view: View) {
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)
    }

    class MyDialogFragment_1 : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle(getString(R.string.dialogTitle))
                    .setMessage(getString(R.string.dialogMessage))
                    .setPositiveButton(getString(R.string.good)) {
                            dialog, id ->  dialog.cancel()
                    }
                builder.create()
            } ?: throw IllegalStateException(getString(R.string.nullException))
        }
    }

    class MyDialogFragment_2 : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle(getString(R.string.dialogTitle))
                    .setMessage(getString(R.string.dialogMessage2))
                    .setPositiveButton(getString(R.string.good)) {
                            dialog, id ->  dialog.cancel()
                    }
                builder.create()
            } ?: throw IllegalStateException(getString(R.string.nullException))
        }
    }
    class MyDialogFragment_3 : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle(getString(R.string.dialogTitle))
                    .setMessage(getString(R.string.dialogMessage2))
                    .setPositiveButton(getString(R.string.good)) {
                            dialog, id ->  dialog.cancel()
                    }
                builder.create()
            } ?: throw IllegalStateException(getString(R.string.nullException))
        }
    }
}
