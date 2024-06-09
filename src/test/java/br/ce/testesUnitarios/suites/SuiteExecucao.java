package br.ce.testesUnitarios.suites;

import org.junit.runners.Suite.SuiteClasses;

import br.ce.testesUnitarios.servicos.CaculculoValorLocacaoTeste;
import br.ce.testesUnitarios.servicos.CalculadoraTest;
import br.ce.testesUnitarios.servicos.LocacaoServiceTest;

//Comentado para não executar os mesmos testes duas vezes
//@RunWith(Suite.class)
@SuiteClasses({
    CalculadoraTest.class,
    CaculculoValorLocacaoTeste.class,
    LocacaoServiceTest.class
})
public class SuiteExecucao {


}
