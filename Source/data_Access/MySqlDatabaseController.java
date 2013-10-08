/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.dimap.dataAccess;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author leobrizolara
 */
public class MySqlDatabaseController implements DatabaseController{
    private static final String MySQLDriver = "com.mysql.jdbc.Driver";  
    private String url;
    private String login;
    private String password;
    
    //para cada conexão determina quantos objetos chamaram beginTransaction
    private Map<Connection, Integer> connOnTransactionCount; 
    private int onTransaction; //determina se esta em transacao
    
    public MySqlDatabaseController(String Url, String Login, String Password) 
            throws ClassNotFoundException{
        url = Url;
        login = Login;
        password = Password;
        
        connOnTransactionCount = new HashMap<Connection, Integer>();
        onTransaction = 0;
        Class.forName(MySQLDriver);  
        
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public Connection CreateConnection(){
        try{
            return DriverManager.getConnection(url, this.login, this.password); 
        }
        catch(SQLException sqlEx){
            sqlEx.printStackTrace();
            return null;
        }
    }

    public void beginTransaction(Connection conn) throws SQLException {
        //if(onTransaction == 0){//nao esta em transacao
        int count = 0;
        if(connOnTransactionCount.containsKey(conn) == false 
                || connOnTransactionCount.get(conn) == 0){//nao esta em transacao
            conn.setAutoCommit(false);
        }
        else{
            count = connOnTransactionCount.get(conn);
        }
        connOnTransactionCount.put(conn, count + 1);
        //++onTransaction; //incrementa o numero de objetos em transacao
    }

    public void commit(Connection conn) throws Exception{
        
        if(connOnTransactionCount.containsKey(conn)){
            int count = connOnTransactionCount.get(conn) - 1;            
            if(count == 0){//todos os que solicitaram beginTransaction deram commit, enviar commit
                conn.commit();
                conn.close();
                connOnTransactionCount.remove(conn);
            }
            else{
                connOnTransactionCount.put(conn, count);
            }
        }
        else{
            //algo de errado ocorreu!
            throw new Exception("Commit fora de transação!");
        }
    }

    public void cancelTransaction(Connection conn)throws Exception{
        if(connOnTransactionCount.containsKey(conn)){
            //cancelar transacao
            conn.rollback();
            conn.close();
            connOnTransactionCount.remove(conn);
        }
        else{
            //algo de errado ocorreu!
            throw new Exception("'Cancel transaction' fora de transação!");
        }
    }
    
}
