/*
 * PedidoSessionBean.java
 *
 */

package br.nom.penha.bruno.ae.lojavirtual.session;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.nom.penha.bruno.arquiteto.dao.DAOException;
import br.nom.penha.bruno.arquiteto.dao.PedidoDAO;
import br.nom.penha.bruno.arquiteto.exception.LojavirtualException;
import br.nom.penha.bruno.arquiteto.jms.ClienteJMS;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;
import br.nom.penha.bruno.arquiteto.model.Pedido;

@Stateless(mappedName="ejb/PedidoSession")
public class PedidoSessionBean implements PedidoSessionLocal {
  
    @EJB
    PedidoDAO pedidoDAO;
    
    @Override
    public Pedido enviarPedido(Pedido pedido) throws LojavirtualException {
       try {
           pedido.setStatus("Recebido");
           pedidoDAO.save(pedido);
           if(pedido.getPagamento() instanceof PagamentoCartaoCredito) {
               ClienteJMS.getInstance().enviarMensagem(pedido,"java:app/queue/ProcessarPedido");
           }
        } catch(DAOException  e) {
            throw new LojavirtualException("Erro ao enviar pedido",e);
        }
        return pedido;
    }
  
}
