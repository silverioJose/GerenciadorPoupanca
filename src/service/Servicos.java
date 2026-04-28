package service;

import model.Conta;


public class Servicos{
	public double verSaldo() {
		Conta conta = new Conta();
		
		double saldo = conta.getSaldo();
		return saldo;
	}
}