package model;

public class Contas{
	private int id;
	private String nome;
	private double saldo;
	
	public Contas(String nome, double saldo) {
		this.nome = nome;
		this.saldo = saldo;
	}
	public Contas(int id, String nome, double saldo) {
		this.id = id;
		this.nome = nome;
		this.saldo = saldo;
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
}