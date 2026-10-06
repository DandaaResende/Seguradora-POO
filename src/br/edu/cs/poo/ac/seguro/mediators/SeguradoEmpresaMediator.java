package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {

	private static SeguradoEmpresaMediator instancia = new SeguradoEmpresaMediator();

	private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();

	private SeguradoEmpresaDAO dao = new SeguradoEmpresaDAO();

	private SeguradoEmpresaMediator() {
	}

	public static SeguradoEmpresaMediator getInstancia() {
		return instancia;
	}

	public String validarCnpj(String cnpj) {
		if (cnpj == null || cnpj.trim().isEmpty()) {
			return "CNPJ deve ser informado";
		}

		if (cnpj.length() != 14) {
			return "CNPJ deve ter 14 caracteres";
		}

		if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
			return "CNPJ com dígito inválido";
		}

		return null;
	}

	public String validarFaturamento(double faturamento) {
		if (faturamento <= 0) {
			return "Faturamento deve ser maior que zero";
		}

		return null;
	}

	public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
		String erro = validarSeguradoEmpresa(seg);

		if (erro != null) {
			return erro;
		}

		if (dao.buscar(seg.getCnpj()) != null) {
			return "CNPJ do segurado empresa já existente";
		}

		dao.incluir(seg);
		return null;
	}

	public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
		String erro = validarSeguradoEmpresa(seg);

		if (erro != null) {
			return erro;
		}

		if (dao.buscar(seg.getCnpj()) == null) {
			return "CNPJ do segurado empresa não existente";
		}

		dao.alterar(seg);
		return null;
	}

	public String excluirSeguradoEmpresa(String cnpj) {
		if (dao.buscar(cnpj) == null) {
			return "CNPJ do segurado empresa não existente";
		}

		dao.excluir(cnpj);
		return null;
	}

	public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
		return dao.buscar(cnpj);
	}

	public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
		String erro;

		erro = seguradoMediator.validarNome(seg.getNome());
		if (erro != null) {
			return erro;
		}

		erro = seguradoMediator.validarEndereco(seg.getEndereco());
		if (erro != null) {
			return erro;
		}

		erro = seguradoMediator.validarDataCriacao(seg.getDataAbertura());
		if (erro != null) {
		    if (seg.getDataAbertura() == null) {
		        return "Data da abertura deve ser informada";
		    }
		    return erro;
		}

		erro = validarCnpj(seg.getCnpj());
		if (erro != null) {
			return erro;
		}

		erro = validarFaturamento(seg.getFaturamento());
		if (erro != null) {
			return erro;
		}

		return null;
	}
}