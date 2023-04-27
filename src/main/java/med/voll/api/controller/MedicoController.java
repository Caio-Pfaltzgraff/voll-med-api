package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosAtualizacaoMedico;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosListagemMedico;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional // diz que precisa ter uma transação ativa com o banco de dados
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) { //@RequestBody serve para receber o corpo da requisição, ou seja o conteúdo. @Valid o Bean validation irá validar os dados
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        //paginação é quando tem uma lista com muitos dados e exibe por exemplo 10 medicos por página
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        //existe um padrão de nomenclatura, que se criar um método com determinado padrão de nomenclatura
        //ele consegue gerar a query da maneira que desejamos, findAllByAtivoTrue irá trazer pelo select todos os médicos ativos
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());//busca no banco pelo id
        medico.atualizarInformacoes(dados); //não precisa fazer mais nada pois a anotação @Transactional já faz o update
    }

    @DeleteMapping("/{id}") //diz ao spring que é um parâmetro dinâmico
    @Transactional
    public void excluir(@PathVariable Long id) { //@PathVariable diz que é uma variavel da url, um parametro dinamico
//        repository.deleteById(id);
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }

}
