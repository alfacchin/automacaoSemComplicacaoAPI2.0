package tranferencia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    private Cliente clienteA;
    private Cliente clienteB;
    private Conta contaCliA;
    private Conta contaCliB;
    private boolean resultado;

    @BeforeEach
    void setUp() {
        clienteA = new Cliente("ClienteA", "05866472944", "91860398");
        clienteB = new Cliente("ClienteB", "96488932144", "64319875");
        contaCliA = new Conta("ContaA", "1234", 500.00, clienteA);
        contaCliB = new Conta("ContaB", "5678", 1000.58, clienteB);
    }

    @Test
    public void realizarTransacao(){
       resultado = contaCliA.realizarTransferencia(230.00,contaCliB);
       assertEquals(270.00, contaCliA.getSaldo());
       assertEquals(1230.58, contaCliB.getSaldo());
    }

    @Test
    public void realizarTransacaoInvalida(){
        resultado = contaCliA.realizarTransferencia(730.00,contaCliB);
        assertFalse(resultado);
    }

}