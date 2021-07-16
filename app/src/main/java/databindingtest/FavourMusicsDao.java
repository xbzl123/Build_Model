package databindingtest;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.root.build_model.data.FavourMusics;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FAVOUR_MUSICS".
*/
public class FavourMusicsDao extends AbstractDao<FavourMusics, Long> {

    public static final String TABLENAME = "FAVOUR_MUSICS";

    /**
     * Properties of entity FavourMusics.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property SongName = new Property(1, String.class, "SongName", false, "SONG_NAME");
        public final static Property SongPath = new Property(2, String.class, "SongPath", false, "SONG_PATH");
        public final static Property AddTime = new Property(3, long.class, "addTime", false, "ADD_TIME");
    }


    public FavourMusicsDao(DaoConfig config) {
        super(config);
    }
    
    public FavourMusicsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FAVOUR_MUSICS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"SONG_NAME\" TEXT," + // 1: SongName
                "\"SONG_PATH\" TEXT," + // 2: SongPath
                "\"ADD_TIME\" INTEGER NOT NULL );"); // 3: addTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FAVOUR_MUSICS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FavourMusics entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String SongName = entity.getSongName();
        if (SongName != null) {
            stmt.bindString(2, SongName);
        }
 
        String SongPath = entity.getSongPath();
        if (SongPath != null) {
            stmt.bindString(3, SongPath);
        }
        stmt.bindLong(4, entity.getAddTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FavourMusics entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String SongName = entity.getSongName();
        if (SongName != null) {
            stmt.bindString(2, SongName);
        }
 
        String SongPath = entity.getSongPath();
        if (SongPath != null) {
            stmt.bindString(3, SongPath);
        }
        stmt.bindLong(4, entity.getAddTime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public FavourMusics readEntity(Cursor cursor, int offset) {
        FavourMusics entity = new FavourMusics( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // SongName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // SongPath
            cursor.getLong(offset + 3) // addTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FavourMusics entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setSongName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSongPath(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAddTime(cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FavourMusics entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FavourMusics entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(FavourMusics entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
