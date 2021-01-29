package forest.zykov.speech_directory.db

import android.provider.BaseColumns

object MyDbNameClass {

    const val TABLE_NAME = "my_directory_table"
    const val  COLUMN_NAME_LEVEL = "level"
    const val COLUMN_NAME_WORD = "word"
    const val COLUMN_NAME_TRANSLATION = "translation"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "Directory2.db"

    const val CREAT_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            " $COLUMN_NAME_LEVEL INTEGER," +
            "$COLUMN_NAME_WORD TEXT,$COLUMN_NAME_TRANSLATION TEXT)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME}"
}