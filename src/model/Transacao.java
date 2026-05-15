package model;

public class Transacao {
	private int id;
	private int contaId;
	private String tipo;
	private double valor;
	private String data;
	
	public Transacao(int contaId, String tipo, double valor, String data) {
		this.contaId = contaId;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
	}
	
	public int getContaId() { return contaId; }
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public String getData() { return data; }
}