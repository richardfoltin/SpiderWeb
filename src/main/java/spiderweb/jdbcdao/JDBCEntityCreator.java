/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.jdbcdao;

import java.sql.SQLException;

/**
 *
 * @author pokemonterkep
 */

@FunctionalInterface
public interface JDBCEntityCreator<T, R> {
    R apply(T t) throws SQLException;    
}
