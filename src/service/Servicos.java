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
	public double verPercentual() {
		double saldo = conta.getSaldo();
		double meta = conta.getMeta();
		double percentual = ((saldo/meta)*100);
		return percentual;
	}
	
	public void deposito(double valor) {
		conta.setSaldo(conta.getSaldo()+valor);
	}
	
	public boolean saque(double valor ) {
		if (conta.getSaldo()>=valor) {
			conta.setSaldo(conta.getSaldo()-valor);
			return true;
		} else {
			return false;
		}
		
	}
}