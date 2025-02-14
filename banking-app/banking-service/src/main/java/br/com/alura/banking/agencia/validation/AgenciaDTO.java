package br.com.alura.banking.agencia.validation;

public record AgenciaDTO(String nome,
                         String razaoSocial,
                         String cnpj,
                         SituacaoCadastral situacaoCadastral) {
}
