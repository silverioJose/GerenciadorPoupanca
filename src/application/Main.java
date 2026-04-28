package application;

import model.Conta;
import model.Mov;
import service.Servicos;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		Conta conta = new Conta();
		Mov mov = new Mov();
		String a = input.nextLine();
		double saldo = conta.getSaldo();
		System.out.println("Saldo: "+ saldo);
		
		
		
		
		input.close();
	}
}