package com.example.sqllite.persistent;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqllite.R;
import com.example.sqllite.entity.Comment;

import java.util.ArrayList;

public class OpenHelper extends SQLiteOpenHelper {

    private static String COMMENT_TABLE = String.valueOf(R.string.commet_table);
    private static String CREATE_COMMENT_TABLE = String.valueOf(R.string.create_commet_table);
    private static String SELECT_COMMENT_TABLE = String.valueOf(R.string.select_commet_table);
    private SQLiteDatabase db;

    public OpenHelper(@Nullable Context context, @Nullable String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        COMMENT_TABLE = context.getString(R.string.commet_table);
        CREATE_COMMENT_TABLE = context.getString(R.string.create_commet_table);
        SELECT_COMMENT_TABLE = context.getString(R.string.select_commet_table);
        db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveComment(Comment comment) {
        ContentValues cv = new ContentValues();
        cv.put("name", comment.name);
        cv.put("comment", comment.comment);
        db.insert(COMMENT_TABLE, null, cv);
    }

    public ArrayList<Comment> findAllComment() {
        ArrayList<Comment> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(SELECT_COMMENT_TABLE, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = getInt(cursor,"id");
                String name = getString(cursor,"name");
                String comment = getString(cursor,"comment");
                result.add(new Comment(id,name,comment));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public String getString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    @SuppressLint("Range")
    public int getInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public void deleteComment(Comment comment) {
        db.delete(COMMENT_TABLE, "id=?", new String[]{String.valueOf(comment.id)});
    }
}
