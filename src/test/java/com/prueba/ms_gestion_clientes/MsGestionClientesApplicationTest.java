package com.prueba.ms_gestion_clientes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
class MsGestionClientesApplicationTests {

    @Test
    void applicationStartsSuccessfully() {
        MsGestionClientesApplication.main(new String[] {});
    }
}