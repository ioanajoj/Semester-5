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
import en.ubb.entityapp.domain.Type;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TypesDao_Impl implements TypesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Type> __insertionAdapterOfType;

  private final EntityDeletionOrUpdateAdapter<Type> __deletionAdapterOfType;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTypes;

  public TypesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfType = new EntityInsertionAdapter<Type>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `types` (`name`) VALUES (?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Type value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
      }
    };
    this.__deletionAdapterOfType = new EntityDeletionOrUpdateAdapter<Type>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `types` WHERE `name` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Type value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
      }
    };
    this.__preparedStmtOfDeleteTypes = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from types";
        return _query;
      }
    };
  }

  @Override
  public void addType(final Type type) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfType.insert(type);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addTypes(final List<Type> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfType.insert(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteType(final Type entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfType.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTypes() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTypes.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteTypes.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Type>> getEntities() {
    final String _sql = "select * from types";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"types"}, false, new Callable<List<Type>>() {
      @Override
      public List<Type> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final List<Type> _result = new ArrayList<Type>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Type _item;
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item = new Type(_tmpName);
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
    final String _sql = "select count(*) from types";
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
}
