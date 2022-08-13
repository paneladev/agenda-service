package pdev.com.agenda.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteCompletoResponse {

    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private List<EnderecoResponse> enderecos;
}
