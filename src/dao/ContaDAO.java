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
			        
				    ResultSet rs1 = select.executeQuery("SELECT id, nome, saldo, meta FROM contas");
	
				    while (rs1.next()) {
				    	int id = rs1.getInt("id");
				    	String nome = rs1.getString("nome");
				    	double saldo = rs1.getDouble("saldo");
				    	double meta = rs1.getDouble("meta");
				    	
				        list.add(new Contas(id, nome, saldo, meta));
				    }
	
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			    
		return list;
	}	
	
	public void insertConta(Contas conta) {
		String sql = "INSERT INTO contas (nome, meta) VALUES (?, ?)";
		
		try (Connection conn = Conn.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
	            pstmt.setString(1, conta.getNome());
	            pstmt.setDouble(2, conta.getMeta());
	            pstmt.executeUpdate();
	            System.out.println("Cofre inserido com sucesso!");
		} catch (SQLException e) {
            System.out.println("Erro ao inserir cofre: " + e.getMessage());
        }			
	}
	
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