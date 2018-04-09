$(document).ready(function () {
    $.getJSON("http://localhost:8080/lojavirtual-javaee7-web/resources/produtos/", lerProdutos);
    $("#btNovo").click(criarNovoProduto);
});

function lerProdutos(produtos) {
    var tabela = $("#corpoProdutos");
    for (var i = 0; i < produtos.length; i++) {
        var linha = criarLinha(produtos[i]);
        tabela.append(linha);
    }
    //adiciona classes CSS do Bootstrap aos links
    $("a").addClass("btn btn-primary btn-xs");
}

function criarLinha(produto) {
    var id = produto.id;
    var nome = produto.nome;
    var categoria = produto.categoria.descricao;
    var preco = produto.preco;
    var marca = produto.marca;
    var linkEditar = "<a onclick='verProduto(" + id + ");' title='Ver'><span class='glyphicon glyphicon-search'></span></a>";
    var linkRemover = "<a onclick='removerProduto(" + id + ");' title='Remover'><span class='glyphicon glyphicon-trash'></span></a>";
    return $("<tr id='prod" + id + "'><td>" + id + "</td><td>" + nome + "</td><td>" + categoria + "</td><td>" + preco + "</td><td>" + marca + "</td><td>" + linkEditar + " " + linkRemover + "</td></tr>");
}

function verProduto(id) {
    $.getJSON("http://localhost:8080/lojavirtual-javaee7-web/resources/produtos/" + id, function (produto) {
        sessionStorage.setItem("produtoSelecionado", JSON.stringify(produto));
        window.location = "formProdutos.html";
    });
}

function removerProduto(id) {
    $.ajax({
        url: "http://localhost:8080/lojavirtual-javaee7-web/resources/produtos/"+id,
        type: "DELETE",
        complete: function (jqXHR, status) {
            exibirMensagem("Resultado da remoção do produto = " + status);
            if (status !== "error") {
                $("#prod" + id).remove();
            }
        }
    });
}

function criarNovoProduto() {
    var novoProduto = {id: 0};
    sessionStorage.setItem("produtoSelecionado", JSON.stringify(novoProduto));
    window.location = "formProdutos.html";
}

function exibirMensagem(mensagem) {
    $("#mensagem").text(mensagem);
    $("#confirmacao").modal();
}



