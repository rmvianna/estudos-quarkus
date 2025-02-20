package br.com.alura.banking.agencia;

public class AgenciaDuplicadaException extends RuntimeException {

    public AgenciaDuplicadaException(String cnpj) {
        super("Agência com CNPJ " + cnpj + " já existe");
    }

}
