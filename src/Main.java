void main() {
    /*
        Criação de um sistema para controlar veículos em um estacionamento.
        O sistema deve ser capaz de:
        Permitir a entrada de veículos no estacionamento
        Permitir a saída de veículos do estacionamento
        Controlar o tempo de permanência de cada veículo no estacionamento
        Controlar a lotação do estacionamento
        Calcular o valor a ser pago pelo cliente
        Exibir um relatório com as informações de todos os veículos que passaram pelo estacionamento
    */

    // Informar o limite de vagas do estacionamento
    // Solicitar a placa do veiculo (formato XXX-0000)
    // Verificar se ja existe um veiculo com a placa informada
        // Sim:
            // Remover da base de dados e informar que o veiculo saiu do estacionamento, demonstrando o tempo de residencia e o valor a ser pago
        // Senao:
            // Verificar se o estacionamento esta cheio
                // Sim: informar que o estacionamento esta cheio
                // Senao: Adicionar a base de dados e informar que o veiculo entrou no estacionamento
    // Demonstrar o relatorio de veiculos no estacionamento

    exibirTitulo();

    String menu;
    menu = """
            Opções
                1 - Adicionar número de placa
                2 - Sair do programa
            """;
    IO.println(menu);

    Scanner scanner = new Scanner(System.in);
    boolean valido = false;

    while (!valido) {
        try {
            int opcao = scanner.nextInt();

            if (opcao < 1 || opcao > 2) {
                IO.println("""
                              Opção não encontrada. Digite:
                              1 - Para Adicionar número de placa
                              2 - Para sair do programa
                              """);
            } else {
                valido = true;
            }

        } catch (InputMismatchException e) {
            IO.println("""
                          Entrada inválida. Digite:
                          1 - Para Adicionar número de placa
                          2 - Para sair do programa
                          """);
            scanner.next();
        }
    }
}

public static void exibirTitulo() {
    IO.println(
            """
            ██████   ██████  ██████  ████████  ██████      ███████ ███████  ██████  ██    ██ ██████   ██████ \s
            ██   ██ ██    ██ ██   ██    ██    ██    ██     ██      ██      ██       ██    ██ ██   ██ ██    ██\s
            ██████  ██    ██ ██████     ██    ██    ██     ███████ █████   ██   ███ ██    ██ ██████  ██    ██\s
            ██      ██    ██ ██   ██    ██    ██    ██          ██ ██      ██    ██ ██    ██ ██   ██ ██    ██\s
            ██       ██████  ██   ██    ██     ██████      ███████ ███████  ██████   ██████  ██   ██  ██████ \s
            =================================================================================================
            """);
    // ref:https://patorjk.com/software/taag/#p=display&f=ANSI+Regular&t=&x=none&v=4&h=4&w=80&w
}
