package br.com.alura.banking.agencia;

public class AgenciaInativaOuInexistenteException extends RuntimeException {

    public AgenciaInativaOuInexistenteException(String cnpjAgencia) {
        super("Agência com CNPJ " + cnpjAgencia + " está inativa ou não existe");
    }

    public AgenciaInativaOuInexistenteException(Long idAgencia) {
        super("Agência de ID " + idAgencia + " está inativa ou não existe");
    }

}
