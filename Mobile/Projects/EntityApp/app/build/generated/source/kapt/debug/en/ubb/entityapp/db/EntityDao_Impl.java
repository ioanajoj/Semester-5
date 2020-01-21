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
import en.ubb.entityapp.domain.Entity;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EntityDao_Impl implements EntityDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Entity> __insertionAdapterOfEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Entity> __deletionAdapterOfEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteEntities;

  public EntityDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEntity = new EntityInsertionAdapter<Entity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `entities` (`id`,`title`,`date`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Entity value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        final Long _tmp;
        _tmp = __converters.dateToTimestamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
      }
    };
    this.__deletionAdapterOfEntity = new EntityDeletionOrUpdateAdapter<Entity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `entities` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Entity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__preparedStmtOfDeleteEntities = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from entities";
        return _query;
      }
    };
  }

  @Override
  public void addEntity(final Entity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEntity.insert(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addEntities(final List<Entity> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEntity.insert(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteEntity(final Entity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteEntities() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteEntities.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteEntities.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Entity>> getEntities() {
    final String _sql = "select * from entities";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"entities"}, false, new Callable<List<Entity>>() {
      @Override
      public List<Entity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final List<Entity> _result = new ArrayList<Entity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Entity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __converters.fromTimestamp(_tmp);
            _item = new Entity(_tmpId,_tmpTitle,_tmpDate);
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
    final String _sql = "select count(*) from entities";
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
