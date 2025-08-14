import java.text.Normalizer;
import java.util.*;

public class Forca {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Random random = new Random();

        List<String> palavras = new ArrayList<>();
        palavras.add("JAVA");
        palavras.add("PROGRAMACAO");
        palavras.add("OBJETOS");
        palavras.add("POLIMORFISMO");

        while (true) {
            System.out.println("\n=== FORCA ===");
            System.out.println("1 - Jogar");
            System.out.println("2 - Adicionar palavra");
            System.out.println("3 - Sair");
            System.out.print("Escolha: ");
            String opcao = entrada.nextLine();

            if (opcao.equals("1")) {
                String palavraSecreta = palavras.get(random.nextInt(palavras.size()));
                Set<Character> letrasTentadas = new HashSet<>();
                int erros = 0;

                while (true) {
                    System.out.println();
                    desenharForca(erros);
                    System.out.println("Palavra: " + mostrarPalavra(palavraSecreta, letrasTentadas));
                    System.out.print("Digite uma letra ou chute a palavra: ");
                    String chute = entrada.nextLine().toUpperCase();

                    if (chute.length() == 1) {
                        char letra = chute.charAt(0);
                        if (letrasTentadas.contains(letra)) {
                            System.out.println("Você já tentou essa letra!");
                        } else {
                            letrasTentadas.add(letra);
                            if (!normalizar(palavraSecreta).contains(normalizarChar(letra))) {
                                erros++;
                            }
                        }
                    } else {
                        if (normalizar(chute).equals(normalizar(palavraSecreta))) {
                            System.out.println("Parabéns! Você acertou a palavra: " + palavraSecreta);
                            break;
                        } else {
                            System.out.println("Palavra errada!");
                            erros++;
                        }
                    }

                    if (erros >= 6) {
                        desenharForca(erros);
                        System.out.println("Você perdeu! A palavra era: " + palavraSecreta);
                        break;
                    }

                    if (ganhou(palavraSecreta, letrasTentadas)) {
                        System.out.println("Parabéns! Você descobriu a palavra: " + palavraSecreta);
                        break;
                    }
                }

            } else if (opcao.equals("2")) {
                System.out.print("Digite a nova palavra: ");
                String nova = entrada.nextLine().toUpperCase();
                if (!nova.isEmpty()) {
                    palavras.add(nova);
                    System.out.println("Palavra adicionada!");
                }
            } else if (opcao.equals("3")) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }
        entrada.close();
    }

    private static String mostrarPalavra(String palavra, Set<Character> letras) {
        StringBuilder sb = new StringBuilder();
        for (char c : palavra.toCharArray()) {
            if (!Character.isLetter(c)) {
                sb.append(c).append(' ');
            } else if (letras.contains(c)) {
                sb.append(c).append(' ');
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }

    private static boolean ganhou(String palavra, Set<Character> letras) {
        for (char c : palavra.toCharArray()) {
            if (Character.isLetter(c) && !letras.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private static String normalizar(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toUpperCase();
    }

    private static String normalizarChar(char c) {
        return normalizar(Character.toString(c));
    }

    private static void desenharForca(int erros) {
        String[] forca = new String[]{
            """
              +---+
              |   |
                  |
                  |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
                  |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
              |   |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|   |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|\\  |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|\\  |
             /    |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|\\  |
             / \\  |
                  |
            =========
            """
        };
        System.out.println(forca[Math.min(erros, 6)]);
    }
}
