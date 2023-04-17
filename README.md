# Fórum de Devs - Testes em Spring Boot
Projeto criado para o fórum de devs da PagSeguro, trazendo diferentes opções de ideias 
e frameworks de testes que se encaixam no contexto de um projeto Spring Boot Kotlin

## Metodologia
O projeto em questão implementa um simples cadastro e listagem de clientes, diferenciados pelo tipo de pessoa -- Física ou Jurídica -- e de sócios, aplicável apenas às pessoas jurídicas.

Com base na implementação, iremos explorar a implementação de testes unitários e de componente com diferentes frameworks.

Para facilitar a comparação, o código será dividido em diferentes branches de acordo com as tecnologias abordadas:
- main: Apenas o código base com suas regras de negócio.
- mockito-mockMvc: Testes implementados utilizando os frameworks Mockito e MockMVC.
