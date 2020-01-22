package en.ubb.entityapp.db;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import en.ubb.entityapp.domain.Robot;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RobotDao_Impl implements RobotDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Robot> __insertionAdapterOfRobot;

  private final EntityDeletionOrUpdateAdapter<Robot> __deletionAdapterOfRobot;

  private final EntityDeletionOrUpdateAdapter<Robot> __updateAdapterOfRobot;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRobots;

  public RobotDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRobot = new EntityInsertionAdapter<Robot>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `robots` (`id`,`name`,`specs`,`height`,`type`,`age`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Robot value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getSpecs() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSpecs());
        }
        if (value.getHeight() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getHeight());
        }
        if (value.getType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getType());
        }
        if (value.getAge() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getAge());
        }
      }
    };
    this.__deletionAdapterOfRobot = new EntityDeletionOrUpdateAdapter<Robot>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `robots` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Robot value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfRobot = new EntityDeletionOrUpdateAdapter<Robot>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `robots` SET `id` = ?,`name` = ?,`specs` = ?,`height` = ?,`type` = ?,`age` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Robot value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getSpecs() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSpecs());
        }
        if (value.getHeight() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getHeight());
        }
        if (value.getType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getType());
        }
        if (value.getAge() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getAge());
        }
        stmt.bindLong(7, value.getId());
      }
    };
    this.__preparedStmtOfDeleteRobots = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from robots where type = ?";
        return _query;
      }
    };
  }

  @Override
  public void addRobot(final Robot robot) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRobot.insert(robot);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addRobots(final List<Robot> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRobot.insert(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteRobot(final Robot entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfRobot.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateRobot(final Robot robot) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfRobot.handle(robot);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteRobots(final String type) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRobots.acquire();
    int _argIndex = 1;
    if (type == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, type);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteRobots.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Robot>> getRobots() {
    final String _sql = "select * from robots";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"robots"}, false, new Callable<List<Robot>>() {
      @Override
      public List<Robot> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSpecs = CursorUtil.getColumnIndexOrThrow(_cursor, "specs");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final List<Robot> _result = new ArrayList<Robot>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Robot _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpSpecs;
            _tmpSpecs = _cursor.getString(_cursorIndexOfSpecs);
            final Integer _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getInt(_cursorIndexOfHeight);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            _item = new Robot(_tmpId,_tmpName,_tmpSpecs,_tmpHeight,_tmpType,_tmpAge);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getNumberOfEntities() {
    final String _sql = "select count(*) from robots";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Robot>> getRobotsByType(final String type) {
    final String _sql = "select * from robots where type = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"robots"}, false, new Callable<List<Robot>>() {
      @Override
      public List<Robot> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSpecs = CursorUtil.getColumnIndexOrThrow(_cursor, "specs");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final List<Robot> _result = new ArrayList<Robot>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Robot _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpSpecs;
            _tmpSpecs = _cursor.getString(_cursorIndexOfSpecs);
            final Integer _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getInt(_cursorIndexOfHeight);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            _item = new Robot(_tmpId,_tmpName,_tmpSpecs,_tmpHeight,_tmpType,_tmpAge);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
