package dao;

import db.Conn;
import model.Contas;
import java.sql.*;

public class ContaDAO {
	
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
	public Contas selectConta(String nome) {
        String sql = "SELECT * FROM contas WHERE nome = ?";
        Contas conta = null;

        try (Connection conn = Conn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                
                conta = new Contas(rs.getString("nome"), rs.getDouble("saldo"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
        return conta;
    }
	
	public void updateSaldo(Contas conta) {
        String sql = "UPDATE contas SET saldo = ? WHERE nome = ?";

        try (Connection conn = Conn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, conta.getSaldo());
            pstmt.setString(2, conta.getNome());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }
}