/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author diogo
 */
@Stateless
public class JogosFacade extends AbstractFacade<Jogos> {

    @PersistenceContext(unitName = "G3PS1617PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JogosFacade() {
        super(Jogos.class);
    }
    
}
