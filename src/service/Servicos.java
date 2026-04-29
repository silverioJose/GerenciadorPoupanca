package service;

import model.ContaPai;

public class Servicos{
	
	private ContaPai conta;
	
	public Servicos(ContaPai conta) {
		this.conta = conta;
	}
	
	public double verSaldo() {
		
		double saldo = conta.getSaldo();
		return saldo;
	}
	public void deposito(double valor) {
		conta.setSaldo(conta.getSaldo()+valor);
	}
	public void saque(double valor ) {
		conta.setSaldo(conta.getSaldo()-valor);
	}
}