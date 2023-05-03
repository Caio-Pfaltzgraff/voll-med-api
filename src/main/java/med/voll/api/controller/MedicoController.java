package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional // diz que precisa ter uma transação ativa com o banco de dados
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) { //@RequestBody serve para receber o corpo da requisição, ou seja o conteúdo. @Valid o Bean validation irá validar os dados
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        //paginação é quando tem uma lista com muitos dados e exibe por exemplo 10 medicos por página
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //existe um padrão de nomenclatura, que se criar um método com determinado padrão de nomenclatura ele consegue gerar a query da maneira que desejamos, findAllByAtivoTrue irá trazer pelo select todos os médicos ativos
        return ResponseEntity.ok(page); //ResponseEntity serve para servir o status code correto
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());//busca no banco pelo id
        medico.atualizarInformacoes(dados); //não precisa fazer mais nada pois a anotação @Transactional já faz o update

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}") //diz ao Spring que é um parâmetro dinâmico
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) { //@PathVariable diz que é uma variavel da url, um parametro dinamico
//        repository.deleteById(id);
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build(); //para dar a resposta como 204, status ok mas sem resposta
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }



}
