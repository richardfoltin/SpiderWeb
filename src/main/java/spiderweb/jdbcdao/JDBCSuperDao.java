/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.jdbcdao;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import spiderweb.SpiderWebApp;
import spiderweb.entity.Entity;
import spiderweb.jdbcdao.dbexception.*;

/**
 *
 * @author pokemonterkep
 */
public abstract class JDBCSuperDao<E extends Entity> {
    
    protected final String table;
    protected final String id;
    
    protected JDBCSuperDao(String table, String id) {
        this.table = table;
        this.id = id;
    }  
    
    protected void delete(Integer key) throws SpiderWriteException {
        String sql = "DELETE FROM \"ROOT\".\"" + table + "\" WHERE " + id + " = ?";

        try (PreparedStatement statement = SpiderWebApp.getConnection().prepareStatement(sql)) {
            statement.setInt(1, key);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SpiderWriteException(ex.getMessage());
        }
    }
    
    protected List<E> findAll(JDBCEntityCreator<ResultSet, E> creator) throws SpiderReadException  {
        String sql = "SELECT * FROM \"ROOT\".\"" + table + "\"";
        return findAll(sql, creator);
    }
    
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
    
    protected E findOneById(Integer key, JDBCEntityCreator<ResultSet, E> creator) throws SpiderReadException {
        String sql = "SELECT * FROM \"ROOT\".\"" + table + "\" WHERE " + id + " = ?";
        return findOne(sql, creator, key);
    }
    
    protected E findOneByColumn(String columnName, String value, JDBCEntityCreator<ResultSet, E> creator) throws SpiderReadException  {
       String sql = "SELECT * FROM \"ROOT\".\"" + table + "\" WHERE " + columnName + " = ?";
       return findOne(sql, creator, value);
    }
    
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
    
    protected E save(E entity, String[] columns, Object[] values) throws SpiderWriteException  {
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
                entity.setId(generatedKeys.getInt(1));

                return entity;
            } else {
                throw new SQLException("Failed entity creation");
            }
        } catch (SQLException ex) {
            throw new SpiderWriteException(ex.getMessage());
        }
    }
    
    protected void update(E entity, String[] columns, Object[] values) throws SpiderWriteException {
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
        builder.append("\n WHERE ").append(id).append(" = ").append(entity.getId());

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
    
    private void StatementValueSetter(PreparedStatement st, int id, Object value) throws SQLException {
        if (value == null) st.setNull(id, java.sql.Types.INTEGER);
        else if (value instanceof String) st.setString(id, (String)value);
        else if (value instanceof Integer) st.setInt(id, (Integer)value);
        else if (value instanceof Boolean) st.setBoolean(id, (Boolean)value);
        else if (value instanceof LocalDate) st.setDate(id, java.sql.Date.valueOf((LocalDate)value));
        else if (value instanceof Blob) st.setBlob(id, (Blob)value);
    }
    

}
