/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author diogo
 */
@Stateless
public class JogosFacade extends AbstractFacade<Jogos> {

 //   @PersistenceContext(unitName = "PS1617PU")
 //   private EntityManager em;



    public JogosFacade() {
        super(Jogos.class);
    }
    
}
