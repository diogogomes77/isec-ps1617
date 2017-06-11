/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entidades.Jogos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import logica.InterfaceJogo;

/**
 *
 * @author diogo
 */
@Stateless
public class JogosFacade extends AbstractFacade<Jogos> {

    @PersistenceContext(unitName = "PS1617PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public JogosFacade() {
        super(Jogos.class);
    }
    
    public Jogos  createJogo(Jogos entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
       // return entity.getJogoId();
        return entity;
    }
    
}
