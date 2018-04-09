$(document).ready(function () {
    $(":radio").change(atualizarTipoProduto);
    $("#btSalvar").click(salvarProduto);
    $.getJSON("http://localhost:8080/lojavirtual-javaee7-web/resources/categorias/", function (categorias) {
        lerCategorias(categorias);
        var produtoSelecionado = sessionStorage.getItem("produtoSelecionado");
        if (produtoSelecionado) {
            atualizarFormulario(JSON.parse(produtoSelecionado));
            sessionStorage.removeItem("produtoSelecionado");
        }
    });
});

function salvarProduto() {
    var produto = criarProduto();
    var produtoJson = JSON.stringify(produto);

    if (produto.id === 0) {
        $.ajax({
            url: "http://localhost:8080/lojavirtual-javaee7-web/resources/produtos/",
            data: produtoJson,
            type: "POST",
            contentType: "application/json",
            complete: function (jqXHR, status) {
                exibirMensagem("Resultado da inserção do produto = " + status);
                if (status !== "error") {
                    window.location = "listaProdutos.html";
                }
            }
        });
    } else {
        $.ajax({
            url: "http://localhost:8080/lojavirtual-javaee7-web/resources/produtos/" + produto.id,
            data: produtoJson,
            type: "PUT",
            contentType: "application/json",
            complete: function (jqXHR, status) {
                exibirMensagem("Resultado da inserção do produto = " + status);
                if (status !== "error") {
                    window.location = "listaProdutos.html";
                }
            }
        });
    }
}

//cria um objeto produto a partir dos dados do formulario
function criarProduto() {
    var categoria = Object();
    categoria.id = parseInt($(":selected").val());
    categoria.descricao = $(":selected").text();
    var produto = Object();
    produto.id = parseInt($("#id").val());
    produto.nome = $("#nome").val();
    produto.preco = parseFloat($("#preco").val());
    produto.marca = $("#marca").val();
    produto.categoria = categoria;
    produto.ativo = $("#status").is(":checked");
    var produtoMaterial = $("#tipoMaterial").is(":checked");
    if (produtoMaterial) {
        produto.type = "material";
        produto.taxaEntrega = parseFloat($("#taxaEntrega").val());
    } else {
        produto.type = "digital";
        produto.nomeArquivo = $("#nomeArquivo").val();
        produto.tamanho = parseInt($("#tamanho").val());
    }
    return produto;
}

// atualiza os dados do formulario a partir de um objeto produto
function atualizarFormulario(produto) {
    $("#id").val(produto.id);
    if (produto.id) {
        $("#nome").val(produto.nome);
        $("#marca").val(produto.marca);
        $("#categoria").val(produto.categoria.id);
        $("#preco").val(produto.preco);
        if (produto.ativo === true) {
            $("#status").prop("checked", true);
        }
        if (produto.type === "material") {
            $("#tipoMaterial").prop("checked", true);
            $("#taxaEntrega").val(produto.taxaEntrega);
        } else {
            $("#tipoDigital").prop("checked", true);
            $("#nomeArquivo").val(produto.nomeArquivo);
            $("#tamanho").val(produto.tamanho);
        }
    }
    atualizarTipoProduto();
}

function lerCategorias(categorias) {
    var select = $("#categoria");
    for (var i = 0; i < categorias.length; i++) {
        var option = criarOption(categorias[i]);
        select.append(option);
    }
}

function criarOption(categoria) {
    var id = categoria.id;
    var descricao = categoria.descricao;
    return $("<option value='" + id + "'>" + descricao + "</option>");

}

function atualizarTipoProduto() {
    var tipo = $("input").filter(":radio").filter(":checked").val();
    if (tipo === "material") {
        $("#dadosProdutoMaterial").show();
        $("#dadosProdutoDigital").hide();
    } else {
        $("#dadosProdutoMaterial").hide();
        $("#dadosProdutoDigital").show();
    }
}

function exibirMensagem(mensagem) {
    $("#mensagem").text(mensagem);
    $("#confirmacao").modal();
}

