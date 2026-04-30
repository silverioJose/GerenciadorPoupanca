package service;

import model.Contas;

public class Servicos{
	
	private Contas conta;
	
	public Servicos(Contas conta) {
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