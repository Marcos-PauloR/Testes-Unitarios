package br.ce.testesUnitarios.servicos.matchers;

import java.util.Calendar;

public class MatchersProprios {

    public static DiaSemanaMatcher caiEm(Integer diaSemana){
        return new DiaSemanaMatcher(diaSemana);
    }
    public static DiaSemanaMatcher eSegunda(){
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }

    public static DataDiferencaDiasMatcher eHojeComDiferencaDias(Integer dias){
        return new DataDiferencaDiasMatcher(dias);
    }

    public static DataDiferencaDiasMatcher eHoje(){
        return new DataDiferencaDiasMatcher(0);
    }
    
}
