package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import model.Contas;
import service.Servicos;

public class MainPageController {

    @FXML private Label labelNome;
    @FXML private Label labelSaldo;
    @FXML private Label labelPercentual;
    @FXML private Button btnDeposito;
    @FXML private Button btnSaque;
    @FXML private Button btnTransacoes;
    @FXML private Button btnSair;

    private Contas conta;
    private Servicos servicos;

    public void setConta(Contas conta) {
        this.conta = conta;
        this.servicos = new Servicos(conta);
        atualizarTela();
    }

    private void atualizarTela() {
        labelNome.setText(conta.getNome());
        labelSaldo.setText("R$ " + String.format("%.2f", conta.getSaldo()));
        labelPercentual.setText(String.format("%.0f%%", servicos.verPercentual()));
    }
}