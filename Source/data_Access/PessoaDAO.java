/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.dimap.dataAccess;

import br.ufrn.dimap.entidades.Pessoa;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author leobrizolara
 */
public class PessoaDAO extends SqlDAO{
    public PessoaDAO(){}
    public PessoaDAO(DatabaseController dataController) {
        super(dataController);
    }
    
    public Pessoa Insert(Pessoa p){
        StringBuilder sqlCmd = new StringBuilder();
        sqlCmd.append(createInsertCmd());
        sqlCmd.append("'(' '");
        sqlCmd.append(p.getCpf());
        sqlCmd.append(",'");
        sqlCmd.append(p.getNome());
        sqlCmd.append(",'");
        sqlCmd.append(p.getEndereco());
        sqlCmd.append(",'");
        sqlCmd.append(p.getCidade());
        sqlCmd.append(",'");
        sqlCmd.append(p.getUf());
        sqlCmd.append(",'");
        sqlCmd.append(p.getNaturalidade());
        sqlCmd.append(",'");
        sqlCmd.append(p.getNacionalidade());
        sqlCmd.append(",'");
        sqlCmd.append(p.getDataNascimento().toString());
        sqlCmd.append(",'");
        sqlCmd.append(p.getEmail());
        sqlCmd.append(",'");
        sqlCmd.append(p.getTelefone());
        sqlCmd.append(")");
        
        return p;
    }
    /*select Login, CPF, Nome, Endereco, Cidade, UF, Naturalidade, Nacionalidade, DTNasc, E_mail, Telefone from
	USUARIO natural join PESSOA where Login = ? and PasswordHash = ?;*/
    protected String createInsertCmd() {
        StringBuilder sqlCmd = new StringBuilder();
        sqlCmd.append("INSERT INTO PESSOA ("); 
        sqlCmd.append("Login, CPF, Nome, Endereco, Cidade, UF, Naturalidade, Nacionalidade, DTNasc, E_mail, Telefone) ");
        sqlCmd.append("VALUE ");
    
        return sqlCmd.toString();
    }
    
    @Override
    protected String createSelectCmd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Object read(ResultSet rs) throws SQLException {
        Pessoa p = new Pessoa();
        p.setCpf(rs.getString("CPF"));
        p.setNome(rs.getString("Nome"));
        p.setCidade(rs.getString("Cidade"));
        p.setDataNascimento(rs.getDate("DTNasc"));
        p.setEmail(rs.getString("E_mail"));
        p.setEndereco(rs.getString("Endereco"));
        p.setNacionalidade(rs.getString("Nacionalidade"));
        p.setNaturalidade(rs.getString("Naturalidade"));
        p.setTelefone(rs.getString("Telefone"));
        p.setUf(rs.getString("uf"));
        
        return p;
    }
}
