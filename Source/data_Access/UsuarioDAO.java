/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.dimap.dataAccess;

import br.ufrn.dimap.entidades.Pessoa;
import br.ufrn.dimap.entidades.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 *
 * @author leobrizolara
 */
public class UsuarioDAO extends SqlDAO{

    public UsuarioDAO(DatabaseController dataController) {
        this.dataController = dataController;
    }
    
    /*select Login, CPF, Nome, Endereco, Cidade, UF, Naturalidade, Nacionalidade, DTNasc, E_mail, Telefone from
	USUARIO natural join PESSOA where Login = ? and PasswordHash = ?;*/
    public Usuario login(String login, String password){
        StringBuilder sqlCmd = new StringBuilder();
        sqlCmd.append(createSelectCmd());
        sqlCmd.append("where Login = '");
        sqlCmd.append(login);
        sqlCmd.append("' ");
        sqlCmd.append("and PasswordHash = '");
        sqlCmd.append(this.hash(password));
        sqlCmd.append("' ");
        Collection<? extends Object> user = this.listAll(sqlCmd.toString());
        if(user.size() > 0){
            return (Usuario) user.toArray()[0];
        }
        //else
        return null;
    }

    @Override
    /*select Login, CPF, Nome, Endereco, Cidade, UF, Naturalidade, Nacionalidade, DTNasc, E_mail, Telefone from
	USUARIO natural join PESSOA where Login = ? and PasswordHash = ?;*/
    protected String createSelectCmd() {
        StringBuilder sqlCmd = new StringBuilder();
        sqlCmd.append("select Login, CPF, Nome, Endereco, Cidade, UF, Naturalidade, Nacionalidade, DTNasc, E_mail, Telefone ");
        sqlCmd.append("from USUARIO natural join PESSOA ");
    
        return sqlCmd.toString();
    }

    @Override
    protected Object read(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        
        Pessoa p = (Pessoa)new PessoaDAO().read(rs);
        usuario.setPessoa(p);
        usuario.setLogin(rs.getString("Login"));
        
        return usuario;
    }
    
    protected String hash(String password){
        //Função para criar hash da senha informada 
      MessageDigest md = null;  
      try {  
         md = MessageDigest.getInstance("MD5");  
      } catch (NoSuchAlgorithmException e) {  
         e.printStackTrace();  
      }
      BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));  
      return hash.toString(16);  
    }
}
