package databindingtest;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.root.build_model.data.MusicFile;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MUSIC_FILE".
*/
public class MusicFileDao extends AbstractDao<MusicFile, Long> {

    public static final String TABLENAME = "MUSIC_FILE";

    /**
     * Properties of entity MusicFile.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Dir = new Property(1, String.class, "dir", false, "DIR");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property MusicDuration = new Property(3, int.class, "musicDuration", false, "MUSIC_DURATION");
        public final static Property IsPlayTime = new Property(4, long.class, "isPlayTime", false, "IS_PLAY_TIME");
        public final static Property IsFavTime = new Property(5, long.class, "isFavTime", false, "IS_FAV_TIME");
    }


    public MusicFileDao(DaoConfig config) {
        super(config);
    }
    
    public MusicFileDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MUSIC_FILE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"DIR\" TEXT," + // 1: dir
                "\"NAME\" TEXT," + // 2: name
                "\"MUSIC_DURATION\" INTEGER NOT NULL ," + // 3: musicDuration
                "\"IS_PLAY_TIME\" INTEGER NOT NULL ," + // 4: isPlayTime
                "\"IS_FAV_TIME\" INTEGER NOT NULL );"); // 5: isFavTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MUSIC_FILE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MusicFile entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String dir = entity.getDir();
        if (dir != null) {
            stmt.bindString(2, dir);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getMusicDuration());
        stmt.bindLong(5, entity.getIsPlayTime());
        stmt.bindLong(6, entity.getIsFavTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MusicFile entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String dir = entity.getDir();
        if (dir != null) {
            stmt.bindString(2, dir);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getMusicDuration());
        stmt.bindLong(5, entity.getIsPlayTime());
        stmt.bindLong(6, entity.getIsFavTime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public MusicFile readEntity(Cursor cursor, int offset) {
        MusicFile entity = new MusicFile( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dir
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.getInt(offset + 3), // musicDuration
            cursor.getLong(offset + 4), // isPlayTime
            cursor.getLong(offset + 5) // isFavTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MusicFile entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setDir(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMusicDuration(cursor.getInt(offset + 3));
        entity.setIsPlayTime(cursor.getLong(offset + 4));
        entity.setIsFavTime(cursor.getLong(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MusicFile entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MusicFile entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MusicFile entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
