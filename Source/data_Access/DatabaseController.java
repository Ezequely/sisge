/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.dimap.dataAccess;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author leobrizolara
 */
public interface DatabaseController {

    public Connection CreateConnection(); 
    public void beginTransaction(Connection conn) throws SQLException;
    public void commit(Connection conn) throws Exception;
    public void cancelTransaction(Connection conn)throws Exception;
}
