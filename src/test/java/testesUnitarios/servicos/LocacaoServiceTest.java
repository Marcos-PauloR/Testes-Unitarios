package testesUnitarios.servicos;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.testesUnitarios.entidades.Filme;
import br.testesUnitarios.entidades.Locacao;
import br.testesUnitarios.entidades.Usuario;
import br.testesUnitarios.exceptions.FilmeSemEstoqueException;
import br.testesUnitarios.exceptions.LocadoraException;
import br.testesUnitarios.servicos.LocacaoService;
import br.testesUnitarios.utils.DataUtils;
import static builders.FilmeBuilder.umFilme;
import static builders.UsuarioBuilder.umUsuario;
import static testesUnitarios.servicos.matchers.MatchersProprios.eHoje;
import static testesUnitarios.servicos.matchers.MatchersProprios.eHojeComDiferencaDias;
import static testesUnitarios.servicos.matchers.MatchersProprios.eSegunda;

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
	public void deveAlugarFilme() throws FilmeSemEstoqueException, LocadoraException {

		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SUNDAY));

		Double precoFilme = 5.0;
		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comPreco(precoFilme).agora());

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(precoFilme)));
		error.checkThat(locacao.getDataLocacao(), eHoje());
		error.checkThat(locacao.getDataRetorno(), eHojeComDiferencaDias(1));
		// error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		// error.checkThat(isMesmaData(locacao.getDataRetorno(),
		// obterDataComDiferencaDias(1)), is(true));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Collections.singletonList(umFilme().semEstoqueFilmeBuilder().agora());

		// acao
		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void validaUsuarioVazio() throws FilmeSemEstoqueException {
		// cenario
		List<Filme> filmes = Arrays.asList(umFilme().agora());

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
		Usuario usuario = umUsuario().agora();

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		service.alugarFilme(usuario, null);
	}

	@Test
	public void naoDeveDevolverAosDomingos() throws FilmeSemEstoqueException, LocadoraException {

		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		Locacao retorno = service.alugarFilme(usuario, filmes);

		assertThat(retorno.getDataRetorno(), eSegunda());
	}

}