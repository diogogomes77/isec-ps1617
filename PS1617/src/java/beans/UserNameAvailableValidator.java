/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entidades.Users;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author diogo
 */
@ManagedBean
@SessionScoped
public class UserNameAvailableValidator  implements Validator {
    
    @EJB
    private facades.UsersFacade ejbFacadeUsers;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
       String userName = (String) value;
       Users u = ejbFacadeUsers.find(userName);
       System.out.println("---UserNameAvailableValidator---"+userName);
       if (u!=null){
            throw new ValidatorException(new FacesMessage("Username ja existente! Escolha outro"));
        }
    }
}
