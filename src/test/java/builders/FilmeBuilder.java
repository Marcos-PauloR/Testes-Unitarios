package builders;

import br.testesUnitarios.entidades.Filme;

public class FilmeBuilder {
    
    private Filme filme;
    
    private FilmeBuilder(){}

    public static FilmeBuilder umFilme(){
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(2);
        builder.filme.setNome("Filme 1");
        builder.filme.setPrecoLocacao(4.0);
        return builder;
    }

    public FilmeBuilder semEstoqueFilmeBuilder(){
        filme.setEstoque(0);
        return this;
    }
    public FilmeBuilder comPreco(Double valor){
        filme.setPrecoLocacao(valor);
        return this;
    }
    public Filme agora(){return filme;}

}