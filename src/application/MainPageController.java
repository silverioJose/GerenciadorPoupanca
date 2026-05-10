package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import model.Contas;
import service.Servicos;
import dao.ContaDAO;
import java.util.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.application.Platform;

public class MainPageController {

    @FXML private Label labelName;
    @FXML private Label labelSaldo;
    @FXML private Label labelOculto;
    @FXML private Label labelPercentual;
    @FXML private Label labelMes;
    @FXML private Label labelOcultoMes;
    @FXML private Button btnDeposito;
    @FXML private Button btnSaque;
    @FXML private Button btnTransacoes;
    @FXML private Button btnSair;
    @FXML private Button btnProx;
    @FXML private Button btnAnt;
    @FXML private Button btnAdicionar;
    @FXML private Button btnConfig;
    @FXML private Button btnDelete;
    @FXML private AnchorPane paneCriar;
    @FXML private AnchorPane paneValor;
    @FXML private AnchorPane paneExclusao;
    @FXML private TextField fieldName;
    @FXML private TextField fieldMeta;
    @FXML private TextField fieldValor;
    @FXML private CheckBox checkVisao;

    private ArrayList<Contas> listaContas;
    private int indiceAtual = 0;
    private Contas conta;
    private Servicos servicos;
    private ContaDAO dao = new ContaDAO();
    private String operacaoAtual;

    // esse metodo roda automatico quando a tela abre
    @FXML
    public void initialize() {
        listaContas = dao.listAll();
        conta = listaContas.get(indiceAtual);
        servicos = new Servicos(conta);
        atualizarTela();
        
        labelSaldo.setVisible(false);
        checkVisao.setSelected(false);
    }

    // atualiza os labels com os dados da conta atual
    private void atualizarTela() {
        conta = listaContas.get(indiceAtual);
        servicos = new Servicos(conta);
        labelName.setText(conta.getNome());
        labelSaldo.setText("R$ " + String.format("%.2f", conta.getSaldo()));
        labelPercentual.setText(String.format("%.0f%%", servicos.verPercentual()));
    }

    //direita
    @FXML
    public void proximaConta() {
        indiceAtual++;
        if (indiceAtual >= listaContas.size()) indiceAtual = 0;
        atualizarTela();
    }

    //esquerda
    @FXML
    public void contaAnterior() {
        indiceAtual--;
        if (indiceAtual < 0) indiceAtual = listaContas.size() - 1;
        atualizarTela();
    }
    //criar conta
    @FXML
    public void abrirCriar() {
        paneCriar.setVisible(true);
    }
    
    @FXML
    public void confirmar() {
        String nome = fieldName.getText();
        double meta = Double.parseDouble(fieldMeta.getText());

        Contas novaConta = new Contas(nome, 0, meta);
        dao.insertConta(novaConta);

        listaContas = dao.listAll();
        indiceAtual = listaContas.size() - 1;
        atualizarTela();
        
        if (listaContas.size() >= 2) {
            btnProx.setVisible(true);
            btnAnt.setVisible(true);
        }

        paneCriar.setVisible(false);
        fieldName.clear();
        fieldMeta.clear();
    }
    
    @FXML
    public void cancelar() {
        paneCriar.setVisible(false);
        fieldName.clear();
        fieldMeta.clear();
    }
    @FXML
    public void deposito() {
    	operacaoAtual = "deposito";
        paneValor.setVisible(true);
    }
    @FXML
    public void saque() {
    	operacaoAtual = "saque";
    	paneValor.setVisible(true);
    }
    
    @FXML
    public void confirmarOperacao() {
        double valor = Double.parseDouble(fieldValor.getText());
        
        if (operacaoAtual.equals("deposito")) {
            servicos.deposito(valor);
        } else {
            servicos.saque(valor);
        }
        
        dao.updateSaldo(conta);
        atualizarTela();
        paneValor.setVisible(false);
        fieldValor.clear();
    }

    @FXML
    public void cancelarOperacao() {
        paneValor.setVisible(false);
        fieldValor.clear();
    }
    
    @FXML
    public void sair() {
    	Platform.exit();
    }
    
    @FXML
    public void visao() {
        boolean ativo = checkVisao.isSelected();
        labelSaldo.setVisible(ativo);
        labelSaldo.setManaged(ativo);
        
        labelOculto.setVisible(!ativo);
        labelOculto.setManaged(!ativo);
        
        labelMes.setVisible(ativo);
        labelMes.setManaged(ativo);
        
        labelOcultoMes.setVisible(!ativo);
        labelOcultoMes.setManaged(!ativo);
    }
    
    @FXML
    public void delete() {
    	paneExclusao.setVisible(true);
    }
    
    @FXML
    private void confirmarDeletar() {
    	dao.deletar(conta.getId());
    	
    	listaContas.remove(indiceAtual);
        
    	if (indiceAtual >= listaContas.size()) {
            indiceAtual = listaContas.size() - 1;
        }
    	
        atualizarTela();
        paneExclusao.setVisible(false);
    }

    @FXML
    private void cancelarDeletar() {
        paneExclusao.setVisible(false);
    }
}