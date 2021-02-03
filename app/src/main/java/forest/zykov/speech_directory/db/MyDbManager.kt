package forest.zykov.speech_directory.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

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

    fun readDbData() : ArrayList<DbElem> {
        val dataList = ArrayList<DbElem>()

        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        while (cursor?.moveToNext()!!) {
            val datalevel = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_LEVEL))
            val dataWord = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_WORD))
            val dataTransl = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TRANSLATION))
            val item = DbElem()
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


