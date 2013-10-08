/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.dimap.dataAccess;

import br.ufrn.dimap.utils.Parameter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leobrizolara
 */
public abstract class SqlDAO implements DatabaseAccessObject{
    
    protected DatabaseController dataController;
    
    public SqlDAO(){
    }
    public SqlDAO(DatabaseController dbControl){
        dataController = dbControl;
    }
    
    public void update(Object obj) {
        //this.connection.
    }

    public void insert(Object obj) {
    }

    public void remove(Object obj) {
    }

    public Collection<? extends Object> listAll() {
        return this.listAll(this.createSelectCmd());
    }
    
    /**Permite as classes na hierarquia executar comandos de busca especificos
     *  -- utiliza transações por padrão, pois garante que consultas encadeadas 
     *      (a varios objetos DAO serão executadas de forma consistente).
     */
    protected final Collection<? extends Object> listAll(String selectCmd){  
        //Cria conexão
        Connection connection = this.dataController.CreateConnection();
        
        try {
            //Inicializa transação
            dataController.beginTransaction(connection);
            //Realiza busca
            Collection<Object> objects = (Collection<Object>) this.listAll(selectCmd, connection);
            //Commit
            dataController.commit(connection);
            //Se conexão ainda estiver aberta, fecha
            if(connection.isClosed() == false){
                connection.close();
            }
            return objects;
            
        } catch (SQLException ex) {
            Logger.getLogger(SqlDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SqlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**Permite as classes na hierarquia utilizar transações
     *  -- Se um objeto DAO está em uma transação e deseja realizar uma consulta através de
     *      outro objeto, deve utilizar este método, passando sua conexão.
     *  -- Caso necessário, esse método deve ser sobrescrito ao invés dos anteriores
     */
    protected Collection<? extends Object> listAll(String selectCmd, Connection connection){
        System.out.println(selectCmd);//DEBUG
        
        try {
            Statement sqlStatement = connection.createStatement();
            ResultSet rs = sqlStatement.executeQuery(selectCmd);
            Collection<Object> objects = new ArrayList<Object>();
            
            while (rs.next()){
                objects.add(read(rs));
            }
            
            return objects;
        } catch(SQLException e){
            e.printStackTrace();
        } 
        return null;
        
    }
    
    protected abstract String createSelectCmd();

    protected abstract Object read(ResultSet rs) throws SQLException;

    /**Para Override utilize search(Object obj, Connection conn) e não este*/
    public final Collection<? extends Object> search(Object obj) {
        Connection conn = dataController.CreateConnection();
        try {
            dataController.beginTransaction(conn);
        
            Collection<? extends Object> objs = this.search(obj, conn);

            dataController.commit(conn);
            if(conn.isClosed() == false){
                conn.close();
            }
            return objs;
        
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(SqlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     *  @return por padrao retorna uma colecao vazia.
     */
    public Collection<? extends Object> search(Object obj, Connection conn) {
        return new ArrayList<Object>();
    }
    
    
    public Collection<? extends Object> search(Parameter param) {
        return this.listAll(createFilteredSearch(param));
    }
    public Collection<? extends Object> search(Parameter param, Connection conn) {
        return this.listAll(createFilteredSearch(param), conn);
    }
    
    /*  select ...
        where param.key = param.value*/
    private String createFilteredSearch(Parameter param) {
        StringBuilder builder = new StringBuilder(this.createSelectCmd());
        builder.append(" where ");
        builder.append(param.getKey());
        builder.append(" = ");
        if(!(param.getValue() instanceof String) && ! (param.getValue() instanceof Date)){
            builder.append(param.getValue().toString());
        }
        else{
            builder.append(" '");
            builder.append(param.getValue().toString());
            builder.append("' ");
        }
        return builder.toString();
    }
    
}
