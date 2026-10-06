package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteSinistroDAO extends TesteDAO {
	private SinistroDAO dao = new SinistroDAO();

	protected Class getClasse() {
		return Sinistro.class;
	}

	private Sinistro criarSinistro(String numero, double valor, TipoSinistro tipo) {
		Veiculo veiculo = new Veiculo("ABC1D23", 2020, null, null, CategoriaVeiculo.BASICO);
		return new Sinistro(numero, veiculo, LocalDateTime.now().minusDays(1), LocalDateTime.now(), "USUARIO1",
				BigDecimal.valueOf(valor), tipo);
	}

	@Test
	public void teste01() {
		String numero = "00000000";
		cadastro.incluir(criarSinistro(numero, 1000.0, TipoSinistro.COLISAO), numero);
		Sinistro si = dao.buscar(numero);
		Assertions.assertNotNull(si);
	}

	@Test
	public void teste02() {
		String numero = "10000000";
		cadastro.incluir(criarSinistro(numero, 1001.0, TipoSinistro.COLISAO), numero);
		Sinistro si = dao.buscar("11000000");
		Assertions.assertNull(si);
	}

	@Test
	public void teste03() {
		String numero = "20000000";
		cadastro.incluir(criarSinistro(numero, 1002.0, TipoSinistro.INCENDIO), numero);
		boolean ret = dao.excluir(numero);
		Assertions.assertTrue(ret);
		Assertions.assertNull(dao.buscar(numero));
	}

	@Test
	public void teste04() {
		String numero = "30000000";
		cadastro.incluir(criarSinistro(numero, 1003.0, TipoSinistro.FURTO), numero);
		boolean ret = dao.excluir("31000000");
		Assertions.assertFalse(ret);
		Assertions.assertNotNull(dao.buscar(numero));
	}

	@Test
	public void teste05() {
		String numero = "40000000";
		boolean ret = dao.incluir(criarSinistro(numero, 1004.0, TipoSinistro.ENCHENTE));
		Assertions.assertTrue(ret);
		Sinistro si = dao.buscar(numero);
		Assertions.assertNotNull(si);
	}

	@Test
	public void teste06() {
		String numero = "50000000";
		Sinistro si = criarSinistro(numero, 1005.0, TipoSinistro.DEPREDACAO);
		cadastro.incluir(si, numero);
		boolean ret = dao.incluir(si);
		Assertions.assertFalse(ret);
	}

	@Test
	public void teste07() {
		String numero = "60000000";
		boolean ret = dao.alterar(criarSinistro(numero, 1006.0, TipoSinistro.COLISAO));
		Assertions.assertFalse(ret);
		Sinistro si = dao.buscar(numero);
		Assertions.assertNull(si);
	}

	@Test
	public void teste08() {
		String numero = "70000000";
		cadastro.incluir(criarSinistro(numero, 1007.0, TipoSinistro.COLISAO), numero);
		boolean ret = dao.alterar(criarSinistro(numero, 2500.0, TipoSinistro.FURTO));
		Assertions.assertTrue(ret);
		Sinistro si = dao.buscar(numero);
		Assertions.assertEquals(TipoSinistro.FURTO, si.getTipo());
		Assertions.assertEquals(BigDecimal.valueOf(2500.0), si.getValorSinistro());
	}
}