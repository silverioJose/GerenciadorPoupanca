package model;

public class Contas{
	private int id;
	private String nome;
	private double saldo;
	private double meta;
	
	public Contas(String nome, double saldo, double meta) {
		this.nome = nome;
		this.saldo = saldo;
		this.meta = meta;
	}
	public Contas(int id, String nome, double saldo, double meta) {
		this.id = id;
		this.nome = nome;
		this.saldo = saldo;
		this.meta = meta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public String getNome() {
		return nome;
	}
	public int getId() {
		return id;
	}
	public double getMeta() {
		return meta;
	}
}