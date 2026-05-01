package application;

import model.Contas;
import dao.ContaDAO;
import db.Conn; 
import service.Servicos;

import java.util.ArrayList;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		boolean isEmpty = true;
		
		//conexao com banco
		try (Connection conn = Conn.getConnection()) {
		    System.out.println("Conexão com SQLite estabelecida!\n");
		    
		    //criação(se não existente) da tabela contas
		    Statement stmt = conn.createStatement();
		    stmt.execute("CREATE TABLE IF NOT EXISTS contas (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, saldo REAL)");
		    
		    //verificaçao se tabela contas esta vazia
		    Statement stmt0 = conn.createStatement();
		    ResultSet rs = stmt0.executeQuery("SELECT EXISTS(SELECT 1 FROM contas LIMIT 1)");
		    
		    if(rs.next()) {
		    	 isEmpty = rs.getInt(1) == 0;
		    }
		   
		} catch (SQLException e) {
		    System.out.println("Erro ao conectar: " + e.getMessage()); 
		}
		
		//apagar daddos
		boolean apagarTudo = false;	
		if (apagarTudo) {
		    try (Connection conn = Conn.getConnection();
		         Statement stmt = conn.createStatement()) {

		        stmt.executeUpdate("DROP TABLE contas");

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		//isEmpty = true;
		
		//se tabela contas vazia, da opção de criação
		if (isEmpty) {
			System.out.println("Digite o nome da conta: ");
			String nome = input.nextLine();
			System.out.println("Digite o saldo inicial: ");
			double saldo = input.nextDouble();
			
			Contas novaConta = new Contas(nome, saldo);
			
			ContaDAO dao = new ContaDAO();
			dao.insertConta(novaConta);
		}
		
		//exibir dados
		boolean exibirTudo = false;	
		if (exibirTudo) {
		    try (Connection conn = Conn.getConnection();
		         Statement select = conn.createStatement()) {
		        
			    ResultSet rs1 = select.executeQuery("SELECT * FROM contas");

			    while (rs1.next()) {
			        System.out.println(rs1.getString("nome"));
			        System.out.println(rs1.getDouble("saldo"));
			        System.out.println("");
			    }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		boolean running = true;
		while (running) {
			
			System.out.println("0 - Sair");
			
			//pega nomes do banco
			ContaDAO dao = new ContaDAO();
			ArrayList<Contas> list = dao.listAll();		
			
			for (int i = 0; i < list.size(); i++) {
				Contas conta = list.get(i);
				System.out.println((i+1) + " - " + conta.getNome());
			}
			
			System.out.println("\nSelecione uma conta: \n");
			int opcaoConta = input.nextInt();
			
			//opcao invalida
			if (opcaoConta > list.size()) {
				System.out.println("\nOpção inválida, tente novamente. \n");
				continue;
			}
			//escolha sair
			if (opcaoConta == 0 || opcaoConta < 1) {
				System.out.println("\nSaindo");
				running = false;
				break;
			} 
			
			Contas contaEsc = list.get(opcaoConta - 1);
			Servicos servicos = new Servicos(contaEsc);
			boolean operating = true;
			
			while (operating) {
				System.out.println("\nConta: " + (contaEsc.getNome()));
				System.out.println("\n1- Deposito");
				System.out.println("2- Saque");
				System.out.println("3- Saldo");
				System.out.println("4- Voltar");
				
				int opcaoOp = input.nextInt();
				
				switch (opcaoOp) {

	            case 1:
	            	System.out.println("Valor: ");
					double valorDeposito = input.nextDouble();
					servicos.deposito(valorDeposito);
					dao.updateSaldo(contaEsc);
					break;
	            case 2:
	            	System.out.println("Valor: ");
					double valorSaque = input.nextDouble();
					servicos.saque(valorSaque);
					dao.updateSaldo(contaEsc);
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