/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.dimap.dataAccess;

import br.ufrn.dimap.entidades.LinhaDePesquisa;
import br.ufrn.dimap.entidades.Disciplina;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author leobrizolara
 */
public class DisciplinaDAO extends SqlDAO{
    public DisciplinaDAO(){}
    public DisciplinaDAO(DatabaseController dbController){
        super(dbController);
    }
    
    /*select * from 
	DISCIPLINA
	natural join LINHADEPESQUISA;*/
    @Override
    protected String createSelectCmd() {
        StringBuilder builder = new StringBuilder();
        builder.append("select * from ");
        builder.append(" DISCIPLINA d ");
        builder.append(" natural join LINHADEPESQUISA ");
        
        String cmd = builder.toString();
        
        return cmd;
    }

    @Override
    protected Object read(ResultSet rs) throws SQLException{
        Disciplina disciplina = new Disciplina();
        
        disciplina.setNome(rs.getString("Nome"));
        disciplina.setCodigoDisciplina(rs.getString("CodigoDisciplina"));
        disciplina.setCargaHoraria(rs.getString("CargaHoraria"));
        disciplina.setEmenta(rs.getString("Ementa"));
        disciplina.setStatus(rs.getString("Status"));
        //le objeto linha de pesquisa
        disciplina.setLinhaDePesquisa((LinhaDePesquisa) (new LinhaDePesquisaDAO().read(rs)));
        
        return disciplina;
    }
    
}
