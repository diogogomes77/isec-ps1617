/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entidades.Torneios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author diogo
 */
@Stateless
public class TorneiosFacade extends AbstractFacade<Torneios> {

    @PersistenceContext(unitName = "PS1617PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public TorneiosFacade() {
        super(Torneios.class);
    }
    
}
