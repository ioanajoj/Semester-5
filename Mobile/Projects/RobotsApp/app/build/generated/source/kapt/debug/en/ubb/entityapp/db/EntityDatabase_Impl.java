package en.ubb.entityapp.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EntityDatabase_Impl extends EntityDatabase {
  private volatile TypesDao _typesDao;

  private volatile RobotDao _robotDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `types` (`name` TEXT NOT NULL, PRIMARY KEY(`name`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `robots` (`id` INTEGER NOT NULL, `name` TEXT, `specs` TEXT, `height` INTEGER, `type` TEXT, `age` INTEGER, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a4ef820899829dc9e29cd59dcc58a348')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `types`");
        _db.execSQL("DROP TABLE IF EXISTS `robots`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTypes = new HashMap<String, TableInfo.Column>(1);
        _columnsTypes.put("name", new TableInfo.Column("name", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTypes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTypes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTypes = new TableInfo("types", _columnsTypes, _foreignKeysTypes, _indicesTypes);
        final TableInfo _existingTypes = TableInfo.read(_db, "types");
        if (! _infoTypes.equals(_existingTypes)) {
          return new RoomOpenHelper.ValidationResult(false, "types(en.ubb.entityapp.domain.Type).\n"
                  + " Expected:\n" + _infoTypes + "\n"
                  + " Found:\n" + _existingTypes);
        }
        final HashMap<String, TableInfo.Column> _columnsRobots = new HashMap<String, TableInfo.Column>(6);
        _columnsRobots.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobots.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobots.put("specs", new TableInfo.Column("specs", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobots.put("height", new TableInfo.Column("height", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobots.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobots.put("age", new TableInfo.Column("age", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRobots = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRobots = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRobots = new TableInfo("robots", _columnsRobots, _foreignKeysRobots, _indicesRobots);
        final TableInfo _existingRobots = TableInfo.read(_db, "robots");
        if (! _infoRobots.equals(_existingRobots)) {
          return new RoomOpenHelper.ValidationResult(false, "robots(en.ubb.entityapp.domain.Robot).\n"
                  + " Expected:\n" + _infoRobots + "\n"
                  + " Found:\n" + _existingRobots);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a4ef820899829dc9e29cd59dcc58a348", "ee905d68d8ac4ed38be565af8c969aa8");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "types","robots");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `types`");
      _db.execSQL("DELETE FROM `robots`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public TypesDao getTypesDao() {
    if (_typesDao != null) {
      return _typesDao;
    } else {
      synchronized(this) {
        if(_typesDao == null) {
          _typesDao = new TypesDao_Impl(this);
        }
        return _typesDao;
      }
    }
  }

  @Override
  public RobotDao getRobotsDao() {
    if (_robotDao != null) {
      return _robotDao;
    } else {
      synchronized(this) {
        if(_robotDao == null) {
          _robotDao = new RobotDao_Impl(this);
        }
        return _robotDao;
      }
    }
  }
}
