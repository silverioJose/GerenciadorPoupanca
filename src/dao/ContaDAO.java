package dao;
import db.Conn;
import model.Contas;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class ContaDAO {
	
	public ArrayList<Contas> listAll() {
		ArrayList <Contas> list = new ArrayList<Contas>();
		
			    try (Connection conn = Conn.getConnection();
			         Statement select = conn.createStatement()) {
			        
				    ResultSet rs1 = select.executeQuery("SELECT id, nome, saldo, meta, criacao FROM contas");
	
				    while (rs1.next()) {
				    	int id = rs1.getInt("id");
				    	String nome = rs1.getString("nome");
				    	double saldo = rs1.getDouble("saldo");
				    	double meta = rs1.getDouble("meta");
				    	
				    	String data = rs1.getString("criacao");
				    	
				        list.add(new Contas(id, nome, saldo, meta, data));
				    }
	
			    } catch (SQLException | URISyntaxException e) {
			        e.printStackTrace();
			    }
			    
		return list;
	}	
	
	public void insertConta(Contas conta) {
		
		String data = LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		
		String sql = "INSERT INTO contas (nome, meta, criacao) VALUES (?, ?, ?)";
		
		try (Connection conn = Conn.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
	            pstmt.setString(1, conta.getNome());
	            pstmt.setDouble(2, conta.getMeta());
	            pstmt.setString(3, data);
	            pstmt.executeUpdate();
	            System.out.println("Cofre inserido com sucesso!");
		} catch (SQLException | URISyntaxException e) {
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
            
        } catch (SQLException | URISyntaxException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }
	
	public void deletar(int id) {
	    String sql = "DELETE FROM contas WHERE id = ?";
	    
	    try (Connection conn = Conn.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, id);
	        stmt.executeUpdate();
	        
	    } catch (SQLException | URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deletarTudo() {
		String sql = "DELETE FROM contas";
		
		try (Connection conn = Conn.getConnection();
			Statement stmt = conn.createStatement()) {
			
				stmt.executeUpdate(sql);
				
				} catch (SQLException | URISyntaxException e) {
					
					e.printStackTrace();
				}
	}
}