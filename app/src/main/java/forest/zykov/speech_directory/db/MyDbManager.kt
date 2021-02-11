package forest.zykov.speech_directory.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log

class MyDbManager(context: Context) {
    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase

    }

    fun insertToDb(level: Int, word: String, translation: String) {
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_LEVEL, level)
            put(MyDbNameClass.COLUMN_NAME_WORD, word)
            put(MyDbNameClass.COLUMN_NAME_TRANSLATION, translation)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun updateItem(level: Int, word: String, translation: String, id: Int) {
        val selection = BaseColumns._ID + "=$id"
        //Log.d("MyLog", "id: " + id)
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_LEVEL, level)
            put(MyDbNameClass.COLUMN_NAME_WORD, word)
            put(MyDbNameClass.COLUMN_NAME_TRANSLATION, translation)
        }
        //Log.d("MyLog", "Values: " + level + " " + word + " " + translation + " " + id)
        db?.update(MyDbNameClass.TABLE_NAME, values, selection, null)
    }

    fun removeItemFromDb(id: String) {
        val selection = BaseColumns._ID + "=$id"
        db?.delete(MyDbNameClass.TABLE_NAME, selection, null)
    }

    fun readDbData(searchText: String) : ArrayList<DbElem> {
        val dataList = ArrayList<DbElem>()
        val selection = "${MyDbNameClass.COLUMN_NAME_WORD} like ?"
        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            arrayOf("%$searchText%"),          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        while (cursor?.moveToNext()!!) {
            val dataid = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val datalevel = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_LEVEL))
            val dataWord = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_WORD))
            val dataTransl = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TRANSLATION))
            val item = DbElem()
            item.id = dataid
            item.level = datalevel.toInt()
            item.word = dataWord
            item.translation = dataTransl
            dataList.add(item)
        }
        cursor.close()
        return dataList
    }

    fun closeDb() {
        myDbHelper.close()
    }
}


