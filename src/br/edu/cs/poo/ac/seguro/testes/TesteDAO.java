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
	    String sep = File.separator;

	    File dir = new File("." + sep + getClasse().getSimpleName());

	    System.out.println("CAMINHO: " + dir.getAbsolutePath());
	    System.out.println("EXISTE: " + dir.exists());
	    System.out.println("É DIRETÓRIO: " + dir.isDirectory());

	    File[] files = dir.listFiles();

	    for (File file : files) {
	        file.delete();
	    }
	}
}