package dao;

import db.Conn;
import model.Contas;
import java.sql.*;
import java.util.ArrayList;

public class ContaDAO {
	
	public ArrayList<Contas> listAll() {
		ArrayList <Contas> list = new ArrayList<Contas>();
		
			    try (Connection conn = Conn.getConnection();
			         Statement select = conn.createStatement()) {
			        
				    ResultSet rs1 = select.executeQuery("SELECT id, nome, saldo FROM contas");
	
				    while (rs1.next()) {
				    	int id = rs1.getInt("id");
				    	String nome = rs1.getString("nome");
				    	double saldo = rs1.getDouble("saldo");
				    	
				        list.add(new Contas(id, nome, saldo));
				    }
	
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			    
		return list;
	}	
	
	public void insertConta(Contas conta) {
		String sql = "INSERT INTO contas (nome, saldo) VALUES (?, ?)";
		
		try (Connection conn = Conn.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
	            pstmt.setString(1, conta.getNome());
	            pstmt.setDouble(2, conta.getSaldo());
	            pstmt.executeUpdate();
	            System.out.println("Conta inserida com sucesso!");
		} catch (SQLException e) {
            System.out.println("Erro ao inserir conta: " + e.getMessage());
        }			
	}
//	public Contas selectConta(String nome) {
//        String sql = "SELECT * FROM contas WHERE nome = ?";
//        Contas conta = null;
//
//        try (Connection conn = Conn.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            
//            pstmt.setString(1, nome);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                
//                conta = new Contas(rs.getString("nome"), rs.getDouble("saldo"));
//            }
//        } catch (SQLException e) {
//            System.out.println("Erro ao buscar: " + e.getMessage());
//        }
//        return conta;
//    }
	
	public void updateSaldo(Contas conta) {
        String sql = "UPDATE contas SET saldo = ? WHERE id = ?";

        try (Connection conn = Conn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, conta.getSaldo());
            pstmt.setInt(2, conta.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }
}