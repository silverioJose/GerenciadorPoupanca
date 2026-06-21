package dao;
import db.Conn;
import model.Transacao;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
public class TransacaoDAO {
	
	public void inserir(int contaId, String tipo, double valor) {
		String data = LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		
		String sql = "INSERT INTO transacoes (contaId, tipo, valor, data) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, contaId);
            pstmt.setString(2, tipo);
            pstmt.setDouble(3, valor);
            pstmt.setString(4, data);
            pstmt.executeUpdate();
		} catch (SQLException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Transacao> listarPorConta (int contaId) {
		ArrayList<Transacao> lista = new ArrayList<>();
		String sql = "SELECT * FROM transacoes WHERE contaId = ? ORDER BY id DESC";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, contaId);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Transacao t = new Transacao(
						rs.getInt("contaId"),
						rs.getString("tipo"),
						rs.getDouble("valor"),
						rs.getString("data")
						);
				lista.add(t);
			}
		} catch (SQLException | URISyntaxException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public double saldoMes(int contaId) {
		String sql = "SELECT tipo, valor, data FROM transacoes WHERE contaId = ?";
		double total = 0;
		
		String mesAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/yyyy"));
		
		try (Connection conn= Conn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, contaId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String data = rs.getString("data");
				String mesTransacao = data.substring(3, 10);
				System.out.println("Data banco: " + data + " | Mês extraído: " + mesTransacao);
				
				if (mesTransacao.equals(mesAtual)) {
					double valor = rs.getDouble("valor");
					String tipo = rs.getString("tipo");
					
					if (tipo.equals("deposito")) total += valor;
					else total -= valor;
				}
			}
			
		} catch (SQLException | URISyntaxException e) {
	        e.printStackTrace();
		}
	return total;
	}
}