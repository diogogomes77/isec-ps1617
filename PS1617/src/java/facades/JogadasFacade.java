/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entidades.Jogadas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author diogo
 */
@Stateless
public class JogadasFacade extends AbstractFacade<Jogadas> {

    @PersistenceContext(unitName = "PS1617PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public JogadasFacade() {
        super(Jogadas.class);
    }
    
    public void  createJogada(Jogadas entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
}
