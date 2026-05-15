package dao;

import db.Conn;
import model.Transacao;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TransacaoDAO {
	
	public void inserir(int contaId, String tipo, double valor) {
		String data = LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		String sql = "INSERT INTO transacoes (conta_id, tipo, valor, data) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, contaId);
            pstmt.setString(2, tipo);
            pstmt.setDouble(3, valor);
            pstmt.setString(4, data);
            pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Transacao> listarPorConta (int contaId) {
		ArrayList<Transacao> lista = new ArrayList<>();
		String sql = "SELECT * FROM transacao WHERE contaId = ? ORDER BY id DESC";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, contaId);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Transacao t = new Transacao(
						rs.getInt("conta_id"),
						rs.getString("tipo"),
						rs.getDouble("valor"),
						rs.getString("data")
						);
				lista.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
}