$(document).ready(function () {
    $("#btInit").click(iniciarMonitoracao);
    $("#btFim").click(finalizarMonitoracao);
    $("#btFim").prop("disabled", true);
});

var websocket;
function iniciarMonitoracao() {
    websocket = new WebSocket("ws://localhost:8080/lojavirtual-javaee7-web/monitor");
    websocket.onmessage = atualizaDashboard;
    $("#btFim").prop("disabled", false);
    $("#btInit").prop("disabled", true);
}
function finalizarMonitoracao() {
    websocket.close();
    $("#btFim").prop("disabled", true);
    $("#btInit").prop("disabled", false);
}
function atualizaDashboard(evento) {
    var tabela = $("#listaEventos");
    var linha = criarLinhaEvento(JSON.parse(evento.data));
    tabela.append(linha);
}

function criarLinhaEvento(eventoRecebido) {
    var produto = eventoRecebido.produto;
    var id = produto.id;
    var nome = produto.nome;
    var categoria = produto.categoria.descricao;
    var preco = produto.preco;
    var marca = produto.marca;
    var tipo = eventoRecebido.evento;
    return $("<tr id='prod" + id + "'><td>" + id + "</td><td>" + nome + "</td><td>" + categoria + "</td><td>" + preco + "</td><td>" + marca + "</td><td>" + tipo + "</td></tr>");
}


