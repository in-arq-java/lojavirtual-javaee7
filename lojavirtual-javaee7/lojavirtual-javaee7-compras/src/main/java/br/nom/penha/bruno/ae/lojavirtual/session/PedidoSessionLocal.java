package br.nom.penha.bruno.ae.lojavirtual.session;

import javax.ejb.Local;

import br.nom.penha.bruno.arquiteto.exception.LojavirtualException;
import br.nom.penha.bruno.arquiteto.model.Pedido;

@Local
public interface PedidoSessionLocal {
    public Pedido enviarPedido(Pedido pedido) throws LojavirtualException;
}
