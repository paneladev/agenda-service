package pdev.com.agenda.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteRequest {

    private Long id;

    @NotBlank(message = "Nome do paciente é obrigatório")
    private String nome;

    @NotBlank(message = "Sobrenome do paciente é obrigatório")
    private String sobrenome;

    private String email;

    @NotBlank(message = "CPF do paciente é obrigatório")
    private String cpf;
}