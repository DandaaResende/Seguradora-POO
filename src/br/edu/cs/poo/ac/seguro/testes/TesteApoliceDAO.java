package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteApoliceDAO extends TesteDAO {
	private ApoliceDAO dao = new ApoliceDAO();

	protected Class getClasse() {
		return Apolice.class;
	}

	private Apolice criarApolice(String numero, double premio) {
		Veiculo veiculo = new Veiculo("ABC1D23", 2020, null, null, CategoriaVeiculo.BASICO);
		Apolice apolice = new Apolice(veiculo, new BigDecimal("1000.00"), BigDecimal.valueOf(premio),
				new BigDecimal("50000.00"));
		apolice.setNumero(numero);
		return apolice;
	}

	@Test
	public void teste01() {
		String numero = "00000000";
		cadastro.incluir(criarApolice(numero, 1000.0), numero);
		Apolice ap = dao.buscar(numero);
		Assertions.assertNotNull(ap);
	}

	@Test
	public void teste02() {
		String numero = "10000000";
		cadastro.incluir(criarApolice(numero, 1001.0), numero);
		Apolice ap = dao.buscar("11000000");
		Assertions.assertNull(ap);
	}

	@Test
	public void teste03() {
		String numero = "20000000";
		cadastro.incluir(criarApolice(numero, 1002.0), numero);
		boolean ret = dao.excluir(numero);
		Assertions.assertTrue(ret);
		Assertions.assertNull(dao.buscar(numero));
	}

	@Test
	public void teste04() {
		String numero = "30000000";
		cadastro.incluir(criarApolice(numero, 1003.0), numero);
		boolean ret = dao.excluir("31000000");
		Assertions.assertFalse(ret);
		Assertions.assertNotNull(dao.buscar(numero));
	}

	@Test
	public void teste05() {
		String numero = "40000000";
		boolean ret = dao.incluir(criarApolice(numero, 1004.0));
		Assertions.assertTrue(ret);
		Apolice ap = dao.buscar(numero);
		Assertions.assertNotNull(ap);
	}

	@Test
	public void teste06() {
		String numero = "50000000";
		Apolice ap = criarApolice(numero, 1005.0);
		cadastro.incluir(ap, numero);
		boolean ret = dao.incluir(ap);
		Assertions.assertFalse(ret);
	}

	@Test
	public void teste07() {
		String numero = "60000000";
		boolean ret = dao.alterar(criarApolice(numero, 1006.0));
		Assertions.assertFalse(ret);
		Apolice ap = dao.buscar(numero);
		Assertions.assertNull(ap);
	}

	@Test
	public void teste08() {
		String numero = "70000000";
		cadastro.incluir(criarApolice(numero, 1007.0), numero);
		boolean ret = dao.alterar(criarApolice(numero, 2500.0));
		Assertions.assertTrue(ret);
		Apolice ap = dao.buscar(numero);
		Assertions.assertEquals(BigDecimal.valueOf(2500.0), ap.getValorPremio());
	}
}