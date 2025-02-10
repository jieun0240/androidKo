package com.example.starbuzzproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "community.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "community_data"
        private const val COLUMN_IMAGE = "image"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_IMAGE BLOB,
                $COLUMN_CONTENT TEXT
            )
        """
        db?.execSQL(createTableQuery)
    }

    fun insertData(image: String, title: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_IMAGE, image)
        values.put(COLUMN_CONTENT, title) // COLUMN_TITLE -> COLUMN_CONTENT 수정
        db.insert(TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun getAllData(): List<CommunityItem> {
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        val communityItems = mutableListOf<CommunityItem>()
        while (cursor.moveToNext()) {
            val image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)) // COLUMN_TITLE -> COLUMN_CONTENT 수정
            communityItems.add(CommunityItem(image, title))
        }
        cursor.close()
        return communityItems
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // 데이터 추가
    fun addData(image: ByteArray, content: String) {
        val db = writableDatabase
        val query = "INSERT INTO $TABLE_NAME ($COLUMN_IMAGE, $COLUMN_CONTENT) VALUES (?, ?)"
        val statement = db.compileStatement(query)
        statement.bindBlob(1, image)
        statement.bindString(2, content)
        statement.executeInsert()
    }
}