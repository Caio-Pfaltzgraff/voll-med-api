package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoEncerramentoaDaClinica = dataConsulta.getHour() > 18;//18 pois 19h fecha , logo a ultima consulta é as 18h

        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoaDaClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }

    }

}
