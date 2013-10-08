/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.dimap.dataAccess;

import br.ufrn.dimap.entidades.Aluno;
import br.ufrn.dimap.entidades.Vinculo;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author leobrizolara
 */
public class AlunoDAO extends SqlDAO{

    public AlunoDAO(DatabaseController dataManager) {
        super(dataManager);
    }

   /*select * from 
	PESSOA 
        natural join VINCULO 
        join ALUNO on Matricula = MatriculaAluno 
        natural left join LINHADEPESQUISA;*/
    protected String createSelectCmd(){
        StringBuilder builder = new StringBuilder();
        builder.append("select * from ");
        builder.append("PESSOA ");
        builder.append("natural join VINCULO ");
        builder.append("join ALUNO on MatriculaAluno = Matricula ");
        builder.append("natural left join LINHADEPESQUISA");
        
        String cmd = builder.toString();
        
        return cmd;
    }

    @Override
    protected Object read(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno((Vinculo)(new VinculoDAO()).read(rs));
        
        aluno.setGrau(rs.getString("Grau"));
        //TODO: ler orientador
        //aluno.setOrientador(new DocenteDAO(this.dataController).read(rs));
        
        return aluno;
    }
    
}
