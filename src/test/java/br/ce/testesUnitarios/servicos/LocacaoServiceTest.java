package br.ce.testesUnitarios.servicos;

import static br.testesUnitarios.utils.DataUtils.isMesmaData;
import static br.testesUnitarios.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.*;

import br.testesUnitarios.servicos.LocacaoService;
import br.testesUnitarios.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.testesUnitarios.entidades.Filme;
import br.testesUnitarios.entidades.Locacao;
import br.testesUnitarios.entidades.Usuario;
import br.testesUnitarios.exceptions.FilmeSemEstoqueException;
import br.testesUnitarios.exceptions.LocadoraException;

public class LocacaoServiceTest {

	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		service = new LocacaoService();
	}

	@Test
	public void deveAlugarFilme() throws Exception {

		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Collections.singletonList(new Filme("Filme 1", 0, 4.0));

		// acao
		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void validaUsuarioVazio() throws FilmeSemEstoqueException {
		// cenario
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		// acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}

	@Test
	public void validaLocacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("Usuario 1");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		service.alugarFilme(usuario, null);
	}

	@Test
	public  void naoDeveDevolverAosDomingos() throws FilmeSemEstoqueException, LocadoraException {

 		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0));


		Locacao retorno = service.alugarFilme(usuario, filmes);

		boolean eSegunda =  DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(eSegunda);
	}

}