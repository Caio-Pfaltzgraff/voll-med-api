package med.voll.api.domain.medico;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //utilizada para testar uma interface repository, uma camada da JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //para usar a config do meu banco de dados, e não de um banco para tests
@ActiveProfiles("test") //quando rodar o test irá carregar o application-test.props
class MedicoRepositoryTest {

    @Test
    void escolherMedicoAleatorioLivreNaData() {
    }
}