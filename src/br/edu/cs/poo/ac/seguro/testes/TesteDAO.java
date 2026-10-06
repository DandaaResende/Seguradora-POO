package br.edu.cs.poo.ac.seguro.testes;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public abstract class TesteDAO {

	protected CadastroObjetos cadastro;

	protected abstract Class getClasse();

	protected TesteDAO() {
		cadastro = new CadastroObjetos(getClasse());
	}

	@BeforeEach
	public void setUp() {
		FileUtils.limparDiretorio("." + File.separator + getClasse().getSimpleName());
	}
}