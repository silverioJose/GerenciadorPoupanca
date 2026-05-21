package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import model.Contas;
import model.Transacao;
import service.Servicos;
import dao.ContaDAO;
import dao.TransacaoDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    @FXML private Button btnFecharHist;
    @FXML private AnchorPane paneCriar;
    @FXML private AnchorPane paneValor;
    @FXML private AnchorPane paneExclusao;
    @FXML private AnchorPane paneHistorico;
    @FXML private TextField fieldName;
    @FXML private TextField fieldMeta;
    @FXML private TextField fieldValor;
    @FXML private CheckBox checkVisao;
    @FXML private TableView<Transacao> tableHistorico;
    @FXML private TableColumn<Transacao, String> collumData;
    @FXML private TableColumn<Transacao, String> collumTipo;
    @FXML private TableColumn<Transacao, Double> collumValor;

    private ArrayList<Contas> listaContas;
    private int indiceAtual = 0;
    private Contas conta;
    private Servicos servicos;
    private ContaDAO dao = new ContaDAO();
    private String operacaoAtual;
    private TransacaoDAO transacaoDAO = new TransacaoDAO();

    // esse metodo roda automatico quando a tela abre
    @FXML
    public void initialize() {
        listaContas = dao.listAll();
        
        
        if (!listaContas.isEmpty()) {
            conta = listaContas.get(indiceAtual);
            servicos = new Servicos(conta);
            atualizarTela();
        } else {
        	paneCriar.setVisible(true);
        }
        labelSaldo.setVisible(false);
        checkVisao.setSelected(false);
        labelMes.setVisible(false);
        
        collumData.setCellValueFactory(new PropertyValueFactory<>("data"));
        
        //formataco para exibicao na tabela
        collumTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        collumTipo.setCellFactory(col -> new TableCell<Transacao, String>() {
        	@Override
        	protected void updateItem(String tipo, boolean empty) {
        		super.updateItem(tipo, empty);
        		if(empty || tipo == null) setText(null);
        		else setText(tipo.substring(0, 1).toUpperCase() + tipo.substring(1).toLowerCase());
        	}
        });
        
        collumValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        collumValor.setCellFactory(col -> new TableCell<Transacao, Double>() {
        	@Override
        	protected void updateItem(Double valor, boolean empty) {
        		super.updateItem(valor, empty);
        		if(empty || valor == null) setText(null);
        		else setText(String.format("R$ %.2f", valor));
        	}
        });
    }

    // atualiza os labels com os dados da conta atual
    private void atualizarTela() {
        conta = listaContas.get(indiceAtual);
        servicos = new Servicos(conta);
        labelName.setText(conta.getNome());
        labelSaldo.setText("R$ " + String.format("%.2f", conta.getSaldo()));
        labelPercentual.setText(String.format("%.0f%%", servicos.verPercentual()));
        //labelMes.setVisible(false);
        
        double movMes = transacaoDAO.saldoMes(conta.getId());
        labelMes.setText(String.format("R$ %.2f", movMes));
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
        double meta = Double.parseDouble(fieldMeta.getText().replace("," , "."));
        
        String data = LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        Contas novaConta = new Contas(nome, 0.0, meta, data);
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
        boolean sucesso = true;
        
        if (operacaoAtual.equals("deposito")) {
            servicos.deposito(valor);
            transacaoDAO.inserir(conta.getId(), "deposito", valor);
        } else {
            sucesso = servicos.saque(valor);
            
            if(sucesso) {
            	transacaoDAO.inserir(conta.getId(), "saque", valor);
            } else { 
            	System.out.println("Saldo insuficiente para o saque!");
            }
        }
        
        if(sucesso) {
	        dao.updateSaldo(conta);
	        atualizarTela();
	        paneValor.setVisible(false);
	        fieldValor.clear();
        }
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
    public void carregarHistorico() {
    	paneHistorico.setVisible(true);
    	ArrayList<Transacao> historico = transacaoDAO.listarPorConta(conta.getId());
    	
    	if (tableHistorico.getColumns().isEmpty()) {
            System.out.println("Atenção: Você precisa adicionar colunas na TableView via SceneBuilder ou código!");
        }
    	
    	ObservableList<Transacao> dadosTabela = FXCollections.observableArrayList(historico);
    	
    	tableHistorico.setItems(dadosTabela);
    	
    	System.out.println("Transações encontradas: " + historico.size());
    }
    
    @FXML
    public void fecharHistorico() {
    	paneHistorico.setVisible(false);
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