/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.dimap.dataAccess;

import java.util.Collection;

/**
 *
 * @author leobrizolara
 */
public interface DatabaseAccessObject {
	 public void update(Object obj);
	 public void insert(Object obj);
	 public void remove(Object obj);
         /** 
          * @param obj um objeto contendo a partir do qual podem ser obtidos os parâmetros
          * de busca da tabela relacionada
          * @return retorna uma colecao de objetos da tabela referenciada obtidos na busca
          */
	 public Collection<? extends Object> search(Object obj);
	 public Collection<? extends Object> listAll();
}
