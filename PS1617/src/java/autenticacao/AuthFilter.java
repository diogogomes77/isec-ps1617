/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autenticacao;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.Sessao;

/**
 *
 * @author diogo
 */
@WebFilter(filterName = "AuthFilter") //, urlPatterns = {"*.xhtml"})
public class AuthFilter implements Filter {

    @EJB
    Sessao sessao;

    public AuthFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("-----DO FILTER");

        try {

            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            
 
      // HttpSession ses = Util.getSession();
       // System.out.println("------Auth Filter SESSAO verifica -----"+ses.getAttribute("username"));
            //  allow user to proccede if url is login.xhtml or user logged in or user is accessing any page in //public folder

            String reqURI = req.getRequestURI();
            //System.out.println("-----TRY FILTER--");

            // chain.doFilter(request, response);
            if (!reqURI.contains("javax.faces.resource")) {
                if (ses != null) {
                    System.out.println("-----SES --");
                    if (ses.getAttribute("username") != null) {
                        System.out.println("-----SES USERNAME --");
                        System.out.println("----- --" + ses.toString());
                        // if (sessao.isAtivo(ses.getAttribute("username").toString())) {
                         String username = ses.getAttribute("username").toString();
                        if (!"".equals(username)) {
                           // if (sessao.isAtivo()) {
                                System.out.println("-----SESSAO isAtivo ");

                                //String username = ses.getAttribute("username").toString();
                                System.out.println("-----LOGED USERNAME= " + username);

                                if (!reqURI.contains("/area_privada")) {
                                    System.out.println("-----UIR no Utilizador --");
                                    res.sendRedirect(req.getContextPath() + "/faces/area_privada/gestaojogos.xhtml");
//                                    return;
                                }

                            } else {
                                System.out.println("-----IS NOT LOGGED --");
                                ses.invalidate();
                                res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
                                //return;
                            }
                        
                    }
                }
                if ((ses == null) || ses.getAttribute("username") == null) {
                    System.out.println("-----SES NULL or NO uSERNAME --");
                    if (!reqURI.contains("/faces/login.xhtml")) {
                        System.out.println("-----URI no VISITANTE --");
                        res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
                        //return;
                    }
//                    else {
//                        chain.doFilter(request, response);
//                        return;
//                    }
                }
            }
            //           System.out.println("-----CHAIN --");
            if (chain != null) {
                chain.doFilter(request, response);
            }
        } catch (IOException | ServletException t) {
            System.out.println(t.getMessage());
        }
    } //doFilter

    @Override
    public void destroy() {

    }
}
