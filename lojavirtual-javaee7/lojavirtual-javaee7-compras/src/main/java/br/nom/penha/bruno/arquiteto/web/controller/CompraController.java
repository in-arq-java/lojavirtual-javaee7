/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.penha.bruno.arquiteto.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.nom.penha.bruno.ae.lojavirtual.session.PedidoSessionLocal;
import br.nom.penha.bruno.arquiteto.dao.BancoDAO;
import br.nom.penha.bruno.arquiteto.dao.BandeiraCartaoCreditoDAO;
import br.nom.penha.bruno.arquiteto.dao.PedidoDAO;
import br.nom.penha.bruno.arquiteto.dao.ProdutoDAO;
import br.nom.penha.bruno.arquiteto.dao.collection.BancoCollection;
import br.nom.penha.bruno.arquiteto.dao.collection.BandeiraCartaoCreditoCollection;
import br.nom.penha.bruno.arquiteto.model.Banco;
import br.nom.penha.bruno.arquiteto.model.BandeiraCartaoCredito;
import br.nom.penha.bruno.arquiteto.model.CarrinhoCompras;
import br.nom.penha.bruno.arquiteto.model.Cliente;
import br.nom.penha.bruno.arquiteto.model.ItemCompra;
import br.nom.penha.bruno.arquiteto.model.Pagamento;
import br.nom.penha.bruno.arquiteto.model.PagamentoBoleto;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;
import br.nom.penha.bruno.arquiteto.model.Pedido;
import br.nom.penha.bruno.arquiteto.model.Produto;
import br.nom.penha.bruno.arquiteto.web.util.JSFHelper;

@Named("compraController")
@ConversationScoped
public class CompraController implements Serializable{

    private final static Logger logger = Logger.getLogger(CompraController.class);
    private static final String CARTAO_CREDITO = "cartao";
    private static final String BOLETO = "boleto";
    private Collection<Produto> produtos;
    private CarrinhoCompras carrinho;
    private String formaPagamentoSelecionada = "cartao";
    private Cliente cliente;
    private boolean boleto = false;
    private List<String> formasPagamento;
    private Pagamento pagamento = new PagamentoCartaoCredito();
    @EJB
    PedidoDAO pedidoDAO;
    @EJB
    ProdutoDAO produtoDAO;
    @EJB
    PedidoSessionLocal pedidoEJB;
            
    @Inject
    private Conversation conversation;

    public PedidoDAO getPedidoDAO() {
        return pedidoDAO;
    }

    public void setPedidoDAO(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public ProdutoDAO getProdutoDAO() {
        return produtoDAO;
    }

    public void setProdutoDAO(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    
    
    public CompraController() {
        pagamento = new PagamentoCartaoCredito();
        HttpSession session =
                (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        cliente = (Cliente) session.getAttribute("usuario");
        if (cliente == null) {
            cliente = new Cliente();
        }
    }

    public void setProdutos(Collection<Produto> produtos) {
        this.produtos = produtos;
    }

    public Collection<Produto> getProdutos() {
        try {
            if (produtos == null) {
                produtos = produtoDAO.findAll();
            }
        } catch (Exception e) {
            String msg = "Erro ao carregar produtos";
            logger.error(msg, e);
            JSFHelper.addErrorMessage(msg);
            return null;
        }
        return produtos;
    }

    public void setCarrinho(CarrinhoCompras carrinho) {
        this.carrinho = carrinho;
    }

    public CarrinhoCompras getCarrinho() {
        return carrinho;
    }

    public void setFormaPagamentoSelecionada(String formaPagamento) {
        this.formaPagamentoSelecionada = formaPagamento;
        boleto = BOLETO.equals(formaPagamento);
    }

    public List getBancos() {
        BancoDAO dao = BancoCollection.getInstance();
        List bancos = dao.getAll();
        return bancos;
    }

    public void setBoleto(boolean boleto) {
        this.boleto = boleto;
    }

    public boolean isBoleto() {
        return boleto;
    }

    public void setFormasPagamento(List formasPagamento) {
        this.formasPagamento = formasPagamento;
    }

    public List getFormasPagamento() {
        formasPagamento = new ArrayList();
        formasPagamento.add(BOLETO);
        formasPagamento.add(CARTAO_CREDITO);
        return formasPagamento;
    }

    public String getFormaPagamentoSelecionada() {
        return formaPagamentoSelecionada;
    }

    public List getBandeiras() {
        BandeiraCartaoCreditoDAO dao = BandeiraCartaoCreditoCollection.getInstance();
        List<BandeiraCartaoCredito> bandeiras = dao.getAll();
        return bandeiras;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void atualizarFormaPagamento(ValueChangeEvent valueChangeEvent) {
        logger.info("[ValueChangeListener] - pagamento=" + pagamento);
        if (valueChangeEvent.getNewValue().equals("boleto")) {
            this.setBoleto(true);
            pagamento = new PagamentoBoleto(pagamento);
        } else {
            this.setBoleto(false);
            pagamento = new PagamentoCartaoCredito(pagamento);
        }
        FacesContext.getCurrentInstance().renderResponse();
    }

    public String adicionarCarrinho(Produto produto) {
        if(conversation.isTransient()) {
            conversation.begin();
        }
        logger.info("Adicionar produto no carrinho");
        logger.info("Produto selecionado pelo cliente " + produto);
        ItemCompra item = new ItemCompra(produto, 1, 0);
        //caso não exista um carrinho, cria-se um e adiciona-se o item desejado
        if (carrinho == null) {
            carrinho = new CarrinhoCompras(item);
        } else {
            carrinho.addItem(item);
        }
        return "/compra/carrinhoCompras";
    }

    public String enviarPedido() {
        logger.info("[CompraController - enviarPedido]" + pagamento);

        try {

            pagamento.setDataPagamento(new Date());
            pagamento.setValor(carrinho.getPrecoTotal());
            if (pagamento instanceof PagamentoCartaoCredito) {
                ((PagamentoCartaoCredito) pagamento).setBandeiraCartaoCredito(new BandeiraCartaoCredito(1, "Visa"));
            } else if (pagamento instanceof PagamentoBoleto) {
                ((PagamentoBoleto) pagamento).setBanco(new Banco(1, "Banco do Brasil"));
            }

            //Criacao do pedido
            String clienteBrowser = "";
            String clienteIP = "";
            String status = "";

            Pedido pedido =
                    new Pedido(cliente, clienteBrowser, clienteIP, new Date(),
                    carrinho.getItens(), pagamento, status);
            logger.info("[CompraController] Enviando pedido para o banco de dados " + pedido);
            pedidoEJB.enviarPedido(pedido);
            if(!conversation.isTransient()) {
                conversation.end();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JSFHelper.addErrorMessage("Erro no processamento de envio de pedido");
        }
        return "/compra/pedidoEnviado";
    }
}
