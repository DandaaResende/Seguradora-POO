package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaMediator {

	private static SeguradoPessoaMediator instancia = new SeguradoPessoaMediator();

	private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();

	private SeguradoPessoaDAO dao = new SeguradoPessoaDAO();

	private SeguradoPessoaMediator() {
	}

	public static SeguradoPessoaMediator getInstancia() {
		return instancia;
	}

	public String validarCpf(String cpf) {
		if (cpf == null || cpf.trim().isEmpty()) {
			return "CPF deve ser informado";
		}

		if (cpf.length() != 11) {
			return "CPF deve ter 11 caracteres";
		}

		if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
			return "CPF com dígito inválido";
		}

		return null;
	}

	public String validarRenda(double renda) {
		if (renda < 0) {
			return "Renda deve ser maior ou igual à zero";
		}

		return null;
	}

	public String incluirSeguradoPessoa(SeguradoPessoa seg) {
		String erro = validarSeguradoPessoa(seg);

		if (erro != null) {
			return erro;
		}

		if (dao.buscar(seg.getCpf()) != null) {
			return "CPF do segurado pessoa já existente";
		}

		dao.incluir(seg);
		return null;
	}

	public String alterarSeguradoPessoa(SeguradoPessoa seg) {
		String erro = validarSeguradoPessoa(seg);

		if (erro != null) {
			return erro;
		}

		if (dao.buscar(seg.getCpf()) == null) {
			return "CPF do segurado pessoa não existente";
		}

		dao.alterar(seg);
		return null;
	}

	public String excluirSeguradoPessoa(String cpf) {
		if (dao.buscar(cpf) == null) {
			return "CPF do segurado pessoa não existente";
		}

		dao.excluir(cpf);
		return null;
	}

	public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
		return dao.buscar(cpf);
	}

	public String validarSeguradoPessoa(SeguradoPessoa seg) {
		String erro;

		erro = seguradoMediator.validarNome(seg.getNome());
		if (erro != null) {
			return erro;
		}

		erro = seguradoMediator.validarEndereco(seg.getEndereco());
		if (erro != null) {
			return erro;
		}

		erro = seguradoMediator.validarDataCriacao(seg.getDataNascimento());
		if (erro != null) {
		    if (seg.getDataNascimento() == null) {
		        return "Data do nascimento deve ser informada";
		    }
		    return erro;
		}

		erro = validarCpf(seg.getCpf());
		if (erro != null) {
			return erro;
		}

		erro = validarRenda(seg.getRenda());
		if (erro != null) {
			return erro;
		}

		return null;
	}
}