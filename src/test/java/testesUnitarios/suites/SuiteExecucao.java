package testesUnitarios.suites;

import org.junit.runners.Suite.SuiteClasses;

import testesUnitarios.servicos.CaculculoValorLocacaoTeste;
import testesUnitarios.servicos.CalculadoraTest;
import testesUnitarios.servicos.LocacaoServiceTest;

//Comentado para não executar os mesmos testes duas vezes
//@RunWith(Suite.class)
@SuiteClasses({
    CalculadoraTest.class,
    CaculculoValorLocacaoTeste.class,
    LocacaoServiceTest.class
})
public class SuiteExecucao {


}
