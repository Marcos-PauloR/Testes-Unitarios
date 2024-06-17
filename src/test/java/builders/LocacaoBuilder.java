package builders;

import static builders.FilmeBuilder.umFilme;
import static builders.UsuarioBuilder.umUsuario;

import java.util.Arrays;
import java.util.Date;

import br.testesUnitarios.entidades.Filme;
import br.testesUnitarios.entidades.Locacao;
import br.testesUnitarios.entidades.Usuario;
import br.testesUnitarios.utils.DataUtils;

public class LocacaoBuilder {
    private Locacao elemento;

    private LocacaoBuilder() {
    }

    public static LocacaoBuilder umLocacao() {
        LocacaoBuilder builder = new LocacaoBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(LocacaoBuilder builder) {
        builder.elemento = new Locacao();
        Locacao elemento = builder.elemento;
        elemento.setUsuario(umUsuario().agora());
        elemento.setFilmes(Arrays.asList(umFilme().agora()));
        elemento.setDataLocacao(new Date());
        elemento.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
        elemento.setValor(4.0);
    }

    public LocacaoBuilder comUsuario(Usuario usuario) {
        elemento.setUsuario(usuario);
        return this;
    }

    public LocacaoBuilder comListaFilmes(Filme... filme) {
        elemento.setFilmes(Arrays.asList(filme));
        return this;
    }

    public LocacaoBuilder comDataLocacao(Date data) {
        elemento.setDataLocacao(data);
        return this;
    }

    public LocacaoBuilder comdataRetorno(Date data) {
        elemento.setDataRetorno(data);
        return this;
    }

    public LocacaoBuilder comValor(Double preco) {
        elemento.setValor(preco);
        return this;
    }

    public Locacao build() {
        return elemento;
    }

}
