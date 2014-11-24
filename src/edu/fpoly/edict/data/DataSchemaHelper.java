package edu.fpoly.edict.data;

import java.util.HashSet;
import java.util.Set;

import edu.fpoly.edict.data.table.WordTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataSchemaHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "dictionary.db";
	// TOGGLE THIS NUMBER FOR UPDATING TABLES AND DATABASE
	private static final int DATABASE_VERSION = 1;

	DataSchemaHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// CREATE STUDENTS TABLE
		db.execSQL("CREATE TABLE " + WordTable.TABLE_NAME + " ("
				+ WordTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ WordTable.WORD_KEY + " TEXT," + WordTable.WORD_MEANING + " TEXT);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("LOG_TAG", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		// KILL PREVIOUS TABLES IF UPGRADED
		db.execSQL("DROP TABLE IF EXISTS " + WordTable.TABLE_NAME);
		
		// CREATE NEW INSTANCE OF SCHEMA
		onCreate(db);
	}

	// WRAPPER METHOD FOR ADDING A STUDENT
	public long addStudent(String key, String meaning) {
		// CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(WordTable.WORD_KEY, key);
		cv.put(WordTable.WORD_MEANING, meaning);
		
		// RETRIEVE WRITEABLE DATABASE AND INSERT
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.insert(WordTable.TABLE_NAME, WordTable.WORD_KEY, cv);
		return result;
	}

//	// GET ALL STUDENTS IN A COURSE
//	public Cursor getStudentsForCourse(int courseId) {
//		SQLiteDatabase sd = getWritableDatabase();
//		// WE ONLY NEED TO RETURN STUDENT IDS
//		String[] cols = new String[] { ClassTable.STUDENT_ID };
//		String[] selectionArgs = new String[] { String.valueOf(courseId) };
//		// QUERY CLASS MAP FOR STUDENTS IN COURSE
//		Cursor c = sd.query(ClassTable.TABLE_NAME, cols, ClassTable.COURSE_ID
//				+ "= ?", selectionArgs, null, null, null);
//		return c;
//	}
//
//	// GET ALL COURSES FOR A GIVEN STUDENT
//	public Cursor getCoursesForStudent(int studentId) {
//		SQLiteDatabase sd = getWritableDatabase();
//		// WE ONLY NEED TO RETURN COURSE IDS
//		String[] cols = new String[] { ClassTable.COURSE_ID };
//		String[] selectionArgs = new String[] { String.valueOf(studentId) };
//		Cursor c = sd.query(ClassTable.TABLE_NAME, cols, ClassTable.STUDENT_ID
//				+ "= ?", selectionArgs, null, null, null);
//		return c;
//	}
//
//	public Set<Integer> getStudentsByGradeForCourse(int courseId, int grade) {
//		SQLiteDatabase sd = getWritableDatabase();
//		// WE ONLY NEED TO RETURN COURSE IDS
//		String[] cols = new String[] { ClassTable.STUDENT_ID };
//		String[] selectionArgs = new String[] { String.valueOf(courseId) };
//		// QUERY CLASS MAP FOR STUDENTS IN COURSE
//		Cursor c = sd.query(ClassTable.TABLE_NAME, cols, ClassTable.COURSE_ID
//				+ "= ?", selectionArgs, null, null, null);
//		Set<Integer> returnIds = new HashSet<Integer>();
//		while (c.moveToNext()) {
//			int id = c.getInt(c.getColumnIndex(ClassTable.STUDENT_ID));
//			returnIds.add(id);
//		}
//		// MAKE SECOND QUERY
//		cols = new String[] { StudentTable.ID };
//		selectionArgs = new String[] { String.valueOf(grade) };
//		c = sd.query(StudentTable.TABLE_NAME, cols, StudentTable.GRADE
//				+ "= ?", selectionArgs, null, null, null);
//		Set<Integer> gradeIds = new HashSet<Integer>();
//		while (c.moveToNext()) {
//			int id = c.getInt(c.getColumnIndex(StudentTable.ID));
//			gradeIds.add(id);
//		}
//		// RETURN INTERSECTION OF ID SETS
//		returnIds.retainAll(gradeIds);
//		return returnIds;
//	}
//
//	// METHOD FOR SAFELY REMOVING A STUDENT
//	public boolean removeStudent(int studentId) {
//		SQLiteDatabase sd = getWritableDatabase();
//		String[] whereArgs = new String[] { String.valueOf(studentId) };
//		// DELETE ALL CLASS MAPPINGS STUDENT IS SIGNED UP FOR
//		sd.delete(ClassTable.TABLE_NAME, ClassTable.STUDENT_ID + "= ? ",
//				whereArgs);
//		// THEN DELETE STUDENT
//		int result = sd.delete(StudentTable.TABLE_NAME, StudentTable.ID
//				+ "= ? ", whereArgs);
//		return (result > 0);
//	}
//
//	// METHOD FOR SAFELY REMOVING A STUDENT
//	public boolean removeCourse(int courseId) {
//		SQLiteDatabase sd = getWritableDatabase();
//		String[] whereArgs = new String[] { String.valueOf(courseId) };
//		// MAKE SURE YOU REMOVE COURSE FROM ALL STUDENTS ENROLLED
//		sd.delete(ClassTable.TABLE_NAME, ClassTable.COURSE_ID + "= ? ",
//				whereArgs);
//		// THEN DELETE COURSE
//		int result = sd.delete(CourseTable.TABLE_NAME, CourseTable.ID + "= ? ",
//				whereArgs);
//		return (result > 0);
//	}

}
