package com.armasconi.taskmaster.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.armasconi.taskmaster.database.TaskMasterDatabaseConverters;
import com.armasconi.taskmaster.models.MyTask;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TasksDao_Impl implements TasksDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MyTask> __insertionAdapterOfMyTask;

  private final EntityDeletionOrUpdateAdapter<MyTask> __deletionAdapterOfMyTask;

  private final EntityDeletionOrUpdateAdapter<MyTask> __updateAdapterOfMyTask;

  public TasksDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMyTask = new EntityInsertionAdapter<MyTask>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `MyTask` (`id`,`title`,`body`,`state`,`datePosted`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyTask value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getBody() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBody());
        }
        if (value.getState() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, __TaskStateEnum_enumToString(value.getState()));
        }
        final Long _tmp = TaskMasterDatabaseConverters.dateToTimeStamp(value.getDatePosted());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
      }
    };
    this.__deletionAdapterOfMyTask = new EntityDeletionOrUpdateAdapter<MyTask>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `MyTask` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyTask value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
      }
    };
    this.__updateAdapterOfMyTask = new EntityDeletionOrUpdateAdapter<MyTask>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `MyTask` SET `id` = ?,`title` = ?,`body` = ?,`state` = ?,`datePosted` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyTask value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getBody() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBody());
        }
        if (value.getState() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, __TaskStateEnum_enumToString(value.getState()));
        }
        final Long _tmp = TaskMasterDatabaseConverters.dateToTimeStamp(value.getDatePosted());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        if (value.id == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.id);
        }
      }
    };
  }

  @Override
  public long insertTask(final MyTask task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfMyTask.insertAndReturnId(task);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final MyTask task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMyTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final MyTask task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMyTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<MyTask> findAll() {
    final String _sql = "SELECT * FROM MyTask";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
      final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
      final int _cursorIndexOfDatePosted = CursorUtil.getColumnIndexOrThrow(_cursor, "datePosted");
      final List<MyTask> _result = new ArrayList<MyTask>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MyTask _item;
        _item = new MyTask();
        if (_cursor.isNull(_cursorIndexOfId)) {
          _item.id = null;
        } else {
          _item.id = _cursor.getLong(_cursorIndexOfId);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _item.setTitle(_tmpTitle);
        final String _tmpBody;
        if (_cursor.isNull(_cursorIndexOfBody)) {
          _tmpBody = null;
        } else {
          _tmpBody = _cursor.getString(_cursorIndexOfBody);
        }
        _item.setBody(_tmpBody);
        final MyTask.TaskStateEnum _tmpState;
        _tmpState = __TaskStateEnum_stringToEnum(_cursor.getString(_cursorIndexOfState));
        _item.setState(_tmpState);
        final Date _tmpDatePosted;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDatePosted)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDatePosted);
        }
        _tmpDatePosted = TaskMasterDatabaseConverters.fromTimeStamp(_tmp);
        _item.setDatePosted(_tmpDatePosted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public MyTask findById(final long id) {
    final String _sql = "SELECT * FROM MyTask WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
      final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
      final int _cursorIndexOfDatePosted = CursorUtil.getColumnIndexOrThrow(_cursor, "datePosted");
      final MyTask _result;
      if(_cursor.moveToFirst()) {
        _result = new MyTask();
        if (_cursor.isNull(_cursorIndexOfId)) {
          _result.id = null;
        } else {
          _result.id = _cursor.getLong(_cursorIndexOfId);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _result.setTitle(_tmpTitle);
        final String _tmpBody;
        if (_cursor.isNull(_cursorIndexOfBody)) {
          _tmpBody = null;
        } else {
          _tmpBody = _cursor.getString(_cursorIndexOfBody);
        }
        _result.setBody(_tmpBody);
        final MyTask.TaskStateEnum _tmpState;
        _tmpState = __TaskStateEnum_stringToEnum(_cursor.getString(_cursorIndexOfState));
        _result.setState(_tmpState);
        final Date _tmpDatePosted;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDatePosted)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDatePosted);
        }
        _tmpDatePosted = TaskMasterDatabaseConverters.fromTimeStamp(_tmp);
        _result.setDatePosted(_tmpDatePosted);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MyTask> findAllByState(final MyTask.TaskStateEnum state) {
    final String _sql = "SELECT * FROM MyTask WHERE state = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (state == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, __TaskStateEnum_enumToString(state));
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
      final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
      final int _cursorIndexOfDatePosted = CursorUtil.getColumnIndexOrThrow(_cursor, "datePosted");
      final List<MyTask> _result = new ArrayList<MyTask>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MyTask _item;
        _item = new MyTask();
        if (_cursor.isNull(_cursorIndexOfId)) {
          _item.id = null;
        } else {
          _item.id = _cursor.getLong(_cursorIndexOfId);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _item.setTitle(_tmpTitle);
        final String _tmpBody;
        if (_cursor.isNull(_cursorIndexOfBody)) {
          _tmpBody = null;
        } else {
          _tmpBody = _cursor.getString(_cursorIndexOfBody);
        }
        _item.setBody(_tmpBody);
        final MyTask.TaskStateEnum _tmpState;
        _tmpState = __TaskStateEnum_stringToEnum(_cursor.getString(_cursorIndexOfState));
        _item.setState(_tmpState);
        final Date _tmpDatePosted;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDatePosted)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDatePosted);
        }
        _tmpDatePosted = TaskMasterDatabaseConverters.fromTimeStamp(_tmp);
        _item.setDatePosted(_tmpDatePosted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __TaskStateEnum_enumToString(final MyTask.TaskStateEnum _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case NEW: return "NEW";
      case COMPLETED: return "COMPLETED";
      case PENDING: return "PENDING";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private MyTask.TaskStateEnum __TaskStateEnum_stringToEnum(final String _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case "NEW": return MyTask.TaskStateEnum.NEW;
      case "COMPLETED": return MyTask.TaskStateEnum.COMPLETED;
      case "PENDING": return MyTask.TaskStateEnum.PENDING;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
