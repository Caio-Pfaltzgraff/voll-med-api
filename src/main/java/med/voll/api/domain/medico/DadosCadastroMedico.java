package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank // não pode ser vazio ou null
        String nome,
        @NotBlank
        @Email //verifica se é email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") //diz que é de 4 a 6 dígitos
        String crm,
        @NotNull // pois não é string e o spring já valida se está regular
        Especialidade especialidade,
        @NotNull @Valid DadosEndereco endereco) { //@Valid é pro Bean validation validar outra classe igual essa está sendo validada
}
