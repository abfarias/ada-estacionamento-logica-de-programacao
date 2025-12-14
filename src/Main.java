void main() {

    Scanner scanner = new Scanner(System.in);

    Map<String, LocalDateTime> carros = new HashMap<>();
    ArrayList<String> registroDeSaidas = new ArrayList<>();

    exibirMenu(scanner, carros, registroDeSaidas);

}

private static void registrarCarro(Scanner scanner, Map<String, LocalDateTime> carros, ArrayList<String> registroDeSaidas) {
    // Solicitar a placa do veículo (formato XXX-0000)
    String numeroDePlaca;

    while (true) {
        IO.println("Informe o número de placa (XXX-0000)");
        numeroDePlaca = scanner.next();

        if (numeroDePlaca.matches("[A-Z]{3}-\\d{4}")) {
            break;
        }
        IO.println("Formato invalido");
    }
    IO.println("Placa valida: " + numeroDePlaca);

    // Verificar se existe um carro com a placa inserida
    if (carros.containsKey(numeroDePlaca)) {

        // Calcular tempo de permanencia
        Duration tempoDePermanencia = Duration.between(carros.get(numeroDePlaca), LocalDateTime.now());
        long minutos = tempoDePermanencia.toMinutes();

        // Calcular valor a ser cobrado
        double valor = calcularCobranca(minutos);

        // Salvar relatorio de saida
        String relatorioDeSaida = "Placa " + numeroDePlaca +
                " - tempo permanência: " + minutos + " minutos" +
                " - valor cobrado: " + valor;

        registroDeSaidas.add(relatorioDeSaida);

        // Remover carro do array
        carros.remove(numeroDePlaca);

        // Informar saida do carro com tempo de permanencia e valor a ser cobrado
        IO.println("Saída do veículo placa " + numeroDePlaca +
                ". Tempo no estacionamento " + minutos + " minutos" +
                ". Valor a ser cobrado " + valor);

        // Demonstrar relatorio do estacionamento
        demonstrarRelatorio(carros, registroDeSaidas);

        // Mostrar as opcoes do menu novamente
        exibirMenu(scanner, carros, registroDeSaidas);

    } else {

        // Adicionar o carro no hashmap
        carros.put(numeroDePlaca, LocalDateTime.now());

        // Informar entrada do carro
        IO.println("Entrada do veículo placa " + numeroDePlaca + " realizada.");

        // Demonstrar relatorio do estacionamento
        demonstrarRelatorio(carros, registroDeSaidas);

        // Mostrar as opcoes do menu novamente
        exibirMenu(scanner, carros, registroDeSaidas);
    }
}

private static void demonstrarRelatorio(Map<String, LocalDateTime> carros, ArrayList<String> registroDeSaidas) {
    IO.println("\n===============================================================\n");
    IO.println("VEÍCULOS ESTACIONADOS");

    for (var carro: carros.entrySet()) {

        LocalDateTime horaDeEntrada = carro.getValue();

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String horaDeEntradaFormatada = horaDeEntrada.format(formato);

        IO.println(
                "\tPlaca " + carro.getKey() +
                " \t\tHora de entrada: " + horaDeEntradaFormatada
        );
    }

    IO.println("\n");

    IO.println("REGISTRO DE SAÍDAS");
    for (String relatorio : registroDeSaidas) {
        IO.println("\t" + relatorio);
    }

    IO.println("\n\n================================================================");
}

private static double calcularCobranca(long minutos) {
    double valor;
    if (minutos <= 5) {
        valor = 0;
    }
    // Acima de 5 minutos até 60 minutos -> 1 hora (5 reais)
    else if (minutos <= 60) {
        valor = 5;
    }
    // Acima de 1 hora -> 5 reais + 6 * por hora adicional (fracao de minutos conta)
    else {
        // Minutos excedentes a primeira hora
        long minutosExtras = minutos - 60;

        // Horas extras sem contar minutos que possam ter sobrado
        long horasExtras = minutosExtras / 60;


        /*
            Se o resultado for diferente de 0, significa que a divisao nao e inteira,
            ou seja, sobra minutos que devem ser contados como 1 hora adicional
         */
        if (minutosExtras % 60 != 0) {
            horasExtras++;
        }

        valor = 5 + (6 * horasExtras);
    }
    return valor;
}

private static void exibirMenu(Scanner scanner, Map<String, LocalDateTime> carros, ArrayList<String> registroDeSaidas) {

    exibirTitulo();
    String menu;
    menu = """
            Opções
                1 - Adicionar número de placa
                2 - Sair do programa
            """;
    IO.println(menu);

    // Validacao de entrada
    int opcao = 0;
    while (true) {
        try {
            opcao = scanner.nextInt();

            if (opcao < 1 || opcao > 2) {
                IO.println("""
                        Opção não encontrada. Digite:
                        1 - Para Adicionar número de placa
                        2 - Para sair do programa
                        """);
            } else { break; }

        } catch (InputMismatchException e) {
            IO.println("""
                    Entrada inválida. Digite:
                    1 - Para Adicionar número de placa
                    2 - Para sair do programa
                    """);
            scanner.next();
        }
    }

    switch (opcao) {
        case 1:
            registrarCarro(scanner, carros, registroDeSaidas);
            break;
        case 2:
            IO.println("Tchau! obrigado por visitar nosso estacionamento!");
            break;
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
