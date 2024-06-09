package br.ce.testesUnitarios.servicos.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.testesUnitarios.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date>{

    private Integer qtdDias;

    public DataDiferencaDiasMatcher(Integer qtdDias){
        this.qtdDias =qtdDias;
    }

    @Override
    public void describeTo(Description arg0) {

    }

    @Override
    protected boolean matchesSafely(Date data) {
        return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdDias));
    }

    
}