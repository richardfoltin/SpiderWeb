/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.jdbcdao.dbexception;

import java.sql.SQLException;

/**
 *
 * @author pokemonterkep
 */
public class SpiderReadException extends SQLException {
    
    public SpiderReadException(String msg) {
        super(msg);
    }
}
