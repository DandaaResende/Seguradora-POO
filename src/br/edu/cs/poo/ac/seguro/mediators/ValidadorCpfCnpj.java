package br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {

	public static boolean ehCnpjValido(String cnpj) {
		if (cnpj == null || cnpj.length() != 14
				|| !StringUtils.temSomenteNumeros(cnpj)) {
			return false;
		}

		int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
		int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

		int soma = 0;

		for (int i = 0; i < 12; i++) {
			soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
		}

		int resto = soma % 11;
		int digito1 = resto < 2 ? 0 : 11 - resto;

		if (digito1 != Character.getNumericValue(cnpj.charAt(12))) {
			return false;
		}

		soma = 0;

		for (int i = 0; i < 13; i++) {
			soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
		}

		resto = soma % 11;
		int digito2 = resto < 2 ? 0 : 11 - resto;

		return digito2 == Character.getNumericValue(cnpj.charAt(13));
	}

	public static boolean ehCpfValido(String cpf) {
		if (cpf == null || cpf.length() != 11
				|| !StringUtils.temSomenteNumeros(cpf)) {
			return false;
		}

		int soma = 0;

		for (int i = 0; i < 9; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
		}

		int resto = soma % 11;
		int digito1 = resto < 2 ? 0 : 11 - resto;

		if (digito1 != Character.getNumericValue(cpf.charAt(9))) {
			return false;
		}

		soma = 0;

		for (int i = 0; i < 10; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
		}

		resto = soma % 11;
		int digito2 = resto < 2 ? 0 : 11 - resto;

		return digito2 == Character.getNumericValue(cpf.charAt(10));
	}
}