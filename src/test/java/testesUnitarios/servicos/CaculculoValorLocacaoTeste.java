package testesUnitarios.servicos;

import static builders.FilmeBuilder.umFilme;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import br.testesUnitarios.entidades.Filme;
import br.testesUnitarios.entidades.Locacao;
import br.testesUnitarios.entidades.Usuario;
import br.testesUnitarios.exceptions.FilmeSemEstoqueException;
import br.testesUnitarios.exceptions.LocadoraException;
import br.testesUnitarios.servicos.LocacaoService;


@RunWith(Parameterized.class)
public class CaculculoValorLocacaoTeste {

    @Parameterized.Parameter
    public List<Filme> filmes;

    @Parameterized.Parameter(value = 1)
    public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String nome;
    private LocacaoService service;

    @Before
    public void setup() {
        service = new LocacaoService();
    }

    private static  Filme filme1 = umFilme().agora();
    private static  Filme filme2 = umFilme().agora();
    private static  Filme filme3 = umFilme().agora();
    private static  Filme filme4 = umFilme().agora();
    private static  Filme filme5 = umFilme().agora();
    private static  Filme filme6 = umFilme().agora();
    private static  Filme filme7 = umFilme().agora();


    @Parameterized.Parameters(name = "Teste de Desconto[preço: {1}] : {index} - {2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem desconto"},
                {Arrays.asList(filme1, filme3, filme2), 11.0, "3 Filmes: 25% de desconto"},
                {Arrays.asList(filme1, filme3, filme2, filme4), 13.0, "4 Filmes: 50% de desconto"},
                {Arrays.asList(filme1, filme3, filme2, filme4, filme5), 14.0, "5 Filmes: 75% de desconto"},
                {Arrays.asList(filme1, filme3, filme2, filme4, filme5, filme6), 14.0, "6 Filmes: 100% de desconto"},
                {Arrays.asList(filme1, filme3, filme2, filme4, filme5, filme6, filme7), 18.0, "6 Filmes: 100% de desconto"},
        });
    }

    @Test
    public void calculaDescontoNosFilmes() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario 1");

        Locacao resultado = service.alugarFilme(usuario, filmes);

        assertThat(resultado.getValor(), is(valorLocacao));
    }
}
