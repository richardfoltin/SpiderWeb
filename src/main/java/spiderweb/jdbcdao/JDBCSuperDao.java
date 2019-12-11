/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.jdbcdao;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;

import spiderweb.SpiderWebApp;
import spiderweb.entity.Entity;
import spiderweb.jdbcdao.dbexception.*;

/**
 * JDBC adatábiziskapcsolatot használó, mentést és betöltést vezérlő osztályok absztrakt szülőosztálya
 * 
 * @author Foltin Csaba Richárd
 * @param <E> 
 */
public abstract class JDBCSuperDao<E extends Entity> {
    
    protected final String table;
    protected final String idColumn;
    
    protected JDBCSuperDao(String table, String id) {
        this.table = table;
        this.idColumn = id;
    }  
    
    /**
     * Törlés kulcs alapján
     * 
     * @param key
     * @throws SpiderWriteException 
     */
    protected void delete(Integer key) throws SpiderWriteException {
        String sql = "DELETE FROM \"ROOT\".\"" + table + "\" WHERE " + idColumn + " = ?";

        try (PreparedStatement statement = SpiderWebApp.getConnection().prepareStatement(sql)) {
            statement.setInt(1, key);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SpiderWriteException(ex.getMessage());
        }
    }
    
    /**
     * Entitások lekérdezése az adatbázisból.
     * Az entitások létrehozása a paraméterként megadott függvénnyel
     * 
     * @param creator az entitások listáját egy ResultSet alapján létrehozó függvény
     * @return az entitások listája
     * @throws SpiderReadException 
     */
    protected List<E> findAll(JDBCEntityCreator<ResultSet, E> creator) throws SpiderReadException  {
        String sql = "SELECT * FROM \"ROOT\".\"" + table + "\"";
        return findAll(sql, creator);
    }
    
    /**
     * Entitások lekérdezése az adatbázisból a megadott SQL utasítás alapján.
     * Az entitások létrehozása a paraméterként megadott függvénnyel.
     * 
     * @param sql
     * @param creator az entitások listáját egy ResultSet alapján létrehozó függvény
     * @return az entitások listája
     * @throws SpiderReadException 
     */
    protected List<E> findAll(String sql, JDBCEntityCreator<ResultSet, E> creator) throws SpiderReadException  {
        try (PreparedStatement statement = SpiderWebApp.getConnection().prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();) {
            List<E> result = new LinkedList<>();
            while (resultSet.next()) {
                result.add(creator.apply(resultSet));
            }

            return result;
        } catch (SQLException ex) {
            throw new SpiderReadException(ex.getMessage());
        }
    }
    
    /**
     * Egy entitás lekérdezése az adatbázisból kulcs alapján.
     * Az entitás létrehozása a paraméterként megadott függvénnyel
     * 
     * @param key a kulcs
     * @param creator az entitást egy ResultSet alapján létrehozó függvény
     * @return a keresett entitás
     * @throws SpiderReadException 
     */
    protected E findOneById(Integer key, JDBCEntityCreator<ResultSet, E> creator) throws SpiderReadException {
        String sql = "SELECT * FROM \"ROOT\".\"" + table + "\" WHERE " + idColumn + " = ?";
        return findOne(sql, creator, key);
    }
    
    /**
     * Egy entitás lekérdezése az adatbázisból megadott oszlopnév és érték alapján.
     * Az entitás létrehozása a paraméterként megadott függvénnyel.
     * 
     * @param columnName a keresett érték osztlopának neve
     * @param value a keresett érték
     * @param creator az entitást egy ResultSet alapján létrehozó függvény
     * @return a keresett entitás
     * @throws SpiderReadException 
     */
    protected E findOneByColumn(String columnName, String value, JDBCEntityCreator<ResultSet, E> creator) throws SpiderReadException  {
       String sql = "SELECT * FROM \"ROOT\".\"" + table + "\" WHERE " + columnName + " = ?";
       return findOne(sql, creator, value);
    }
    
    /**
     * Egy entitás lekérdezése az adatbázisból megadott SQL lekérdezés és egy behelyettesítési éréték alapján
     * Az entitás létrehozása a paraméterként megadott függvénnyel.
     * 
     * @param sql
     * @param creator az entitást egy ResultSet alapján létrehozó függvény
     * @param value az egy behelyettesítési érétk (általában WHERE klauzulába)
     * @return a keresett entitás
     * @throws SpiderReadException 
     */
    protected E findOne(String sql, JDBCEntityCreator<ResultSet, E> creator, Object value) throws SpiderReadException {
        try (PreparedStatement statement = SpiderWebApp.getConnection().prepareStatement(sql)) {
            
            StatementValueSetter(statement, 1, value);
            
            ResultSet resultSet = statement.executeQuery();

            List<E> results = new LinkedList<>();
            while (resultSet.next()) {
                results.add(creator.apply(resultSet));
            }
            E result = results.get(0);
            resultSet.close();
            
            return result;
        } catch (SQLException | IndexOutOfBoundsException ex) {
            throw new SpiderReadException(ex.getMessage());
        }
    }
    
    /**
     * A megadott értékek elmentése az adatbázis megadott oszlopaiba.
     * 
     * @param columns
     * @param values
     * @return az elementett entitás visszakapott adatbázis-kulcsa
     * @throws SpiderWriteException 
     */
    protected int save(String[] columns, Object[] values) throws SpiderWriteException  {
        if (columns.length != values.length) {
            throw new SpiderWriteException("Columns and values count don't match.");
        }
                
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO \"ROOT\".\"").append(table).append("\"\n");
        builder.append("(");
        
        for (String column : columns) {
            builder.append(column).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")\n");
        builder.append("VALUES (");
        
        for (int i = 0; i < columns.length; i++) {
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
                
        try {
            PreparedStatement statement = SpiderWebApp.getConnection().prepareStatement(builder.toString(), Statement.RETURN_GENERATED_KEYS);
            
            for (int i = 0; i < values.length; i++) {
                StatementValueSetter(statement, i + 1, values[i]);
            }
            
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                Integer id = generatedKeys.getInt(1);
                return id;
            } else {
                throw new SQLException("Failed entity creation in database.");
            }
        } catch (SQLException ex) {
            throw new SpiderWriteException(ex.getMessage());
        }
    }
    
    /**
     * Az adatbázis kulcs alapján talált rekordja értékeinek frissítése
     * 
     * @param id az entitás adatbáziskulcsa
     * @param columns a firssítendő oszlopok
     * @param values a frissítendő értékek
     * @throws SpiderWriteException 
     */
    protected void update(int id, String[] columns, Object[] values) throws SpiderWriteException {
        if (columns.length != values.length) {
            throw new SpiderWriteException("Columns and values count don't match.");
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE \"ROOT\".\"").append(table).append("\"\n");
        builder.append("SET ");
        
        for (String column : columns) {
            builder.append(column).append(" = ?,");
        }
        
        builder.deleteCharAt(builder.length() - 1);
        builder.append("\n WHERE ").append(idColumn).append(" = ").append(id);

        try {
            PreparedStatement statement = SpiderWebApp.getConnection().prepareStatement(builder.toString());
            
            for (int i = 0; i < values.length; i++) {
                StatementValueSetter(statement, i + 1, values[i]);
            }
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SpiderWriteException(ex.getMessage());
        }
    }
    
    /**
     * Az SQL statement megadott indexű helyén az objektum típusa alapján feltölti értékkel
     * 
     * @param st a statement
     * @param id a hiányzó változó indexe
     * @param value
     * @throws SQLException 
     */
    private void StatementValueSetter(PreparedStatement st, int id, Object value) throws SQLException {
        if (value == null) st.setNull(id, java.sql.Types.INTEGER);
        else if (value instanceof String) st.setString(id, (String)value);
        else if (value instanceof Integer) st.setInt(id, (Integer)value);
        else if (value instanceof Boolean) st.setBoolean(id, (Boolean)value);
        else if (value instanceof LocalDate) st.setDate(id, java.sql.Date.valueOf((LocalDate)value));
        else if (value instanceof Blob) st.setBlob(id, (Blob)value);
    }
    
    /**
     * byte[] -> Blob konverzió
     * 
     * @param bytes
     * @return
     * @throws SpiderImageException 
     */
    protected static Blob bytesToBlob(byte[] bytes) throws SpiderImageException {
        try {
           return new SerialBlob(bytes); 
        } catch (SQLException ex) {
            throw new SpiderImageException("Can't create Blob.");
        }
    }
}
