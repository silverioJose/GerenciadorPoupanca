package application;

import model.Contas;
import service.Servicos;
import java.util.Scanner;
import db.Conn; 
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) {
		
		try (Connection conn = Conn.getConnection()) {
		    System.out.println("Conexão com SQLite estabelecida!");
		    
		    Statement stmt = conn.createStatement();
		    
		    stmt.execute("CREATE TABLE IF NOT EXISTS contas (nome TEXT, saldo REAL)");
		    
		} catch (SQLException e) {
		    System.out.println("Erro ao conectar: " + e.getMessage()); 
		}
		
		Scanner input = new Scanner(System.in);
		
		boolean running = true;
		while (running) {
			System.out.println("Escolha a conta");
			System.out.println("1- Inter");
			System.out.println("2- Nubank");
			System.out.println("0- Sair");
			
			int opcaoConta = input.nextInt();
			
			if (opcaoConta == 0) {
				System.out.println("\nSaindo");
				running = false;
				break;
			} 
			
			Contas contaEscolhida = null;
			String nomeConta = "";
			
			if (opcaoConta == 1) {
				contaEscolhida = new Contas("Inter", 53000.23);
				nomeConta = "Inter";
			} else if (opcaoConta == 2) {
				contaEscolhida = new Contas("Nubank", 192.45);
				nomeConta = "Nubank";
			}
			
			Servicos servicos = new Servicos(contaEscolhida);
			boolean operating = true;
			
			while (operating) {
				System.out.println("\nConta: " + nomeConta);
				System.out.println("\n1- Deposito");
				System.out.println("2- Saque");
				System.out.println("3- Saldo");
				System.out.println("4- Voltar");
				
				int opcao = input.nextInt();
				
				switch (opcao) {

	            case 1:
	            	System.out.println("Valor: ");
					double valorDeposito = input.nextDouble();
					servicos.deposito(valorDeposito);
					break;
	            case 2:
	            	System.out.println("Valor: ");
					double valorSaque = input.nextDouble();
					servicos.saque(valorSaque);
	                break;
	            case 3:
	            	System.out.printf("Saldo: %.2f", servicos.verSaldo());
	            	break;
	            case 4:
	                operating = false;
	                break;
				}
			}
		}
		input.close();
	}
}