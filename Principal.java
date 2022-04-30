import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Principal {

    private static String inicio = "<!DOCTYPE html><html><body><svg height=\"2160\" width=\"3840\">";
    private static String fim = "</svg></body></html>";
    private static String conteudo = "";

    private static int x1;
    private static int y1;
    private static int x2;
    private static int y2;

    private static double cx;
    private static int cy;
    private static int r;

    private static int rx;
    private static int ry;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("A gramática aceita apenas os caracteres L, E e C");
        Boolean repete = false;

        do {

            x1 = 0;
            y1 = 0;
            x2 = 0;
            y2 = 0;
            cx = 0;
            cy = 0;
            r = 1;
            rx = 0;
            ry = 0;

            System.out.println("Insira o caminho do arquivo a ser processado:");
            String caminho = System.console().readLine();

            Path path = Paths.get(caminho);
            List<String> lista = Files.readAllLines(path, StandardCharsets.UTF_8);

            if (lista.isEmpty()) {
                System.out.println("O arquivo é invalido.");
            } else {
                acertaTxt(lista);
                repete = false;
                criaArquivo();
                System.out.println("Arquivo processado com sucesso.");
            }
        } while (repete);

        scanner.close();
    }

    static void acertaTxt(List<String> list) {
        for (String line : list) {
            line = line.trim();
            char[] caracteres = line.toUpperCase().toCharArray();

            for (char carac : caracteres) {
                if (carac != 'L' && carac != 'E' && carac != 'C') {
                    System.out.println("A gramatica do arquivo nao está correta");
                }
            }
            gerar(line);
            System.out.println(line);
        }
    }

    private static void criaArquivo() {
        Path path = Paths.get("Linha e Circulo.html");
        String arquivoConteudo = inicio + conteudo + fim;
        byte[] bytes = arquivoConteudo.getBytes();

        try {
            Files.write(path, bytes);
        } catch (IOException exception) {
            System.out.println("Ocorreu um erro ao gerar seu arquivo, misericordia.");
        }
    }

    private static void gerar(String aux) {
        int desloca = x2;
        for (char a : aux.toCharArray()) {
            if (a == 'L') {
                x1 = x2;
                y1 = y2;
                x2 += geraAleatorio();
                y2 += geraAleatorio();

                geraLinha();
            }

            else if (a == 'C') {

                desloca = x2++;

                r = 15;

                if (x2 % 2 == 0)
                    cx = desloca + r;
                else
                    cx = desloca - r;

                cy = y2;

                geraCirculo();

            } else if (a == 'E') {

                desloca = x2++;

                cy = geraAleatorio();
                cx = geraAleatorio();
                rx = desloca;
                ry = y2;

                geraElipse();
            }

        }
    }

    private static int geraAleatorio() {
        Random random = new Random();
        return random.nextInt(100 - (-100 - 1) + -100);
    }

    private static void geraLinha() {
        String forma = "<line ";
        forma += "x1=\"" + x1 + "\" ";
        forma += "x2=\"" + x2 + "\" ";
        forma += "y1=\"" + y1 + "\" ";
        forma += "y2=\"" + y2 + "\" ";
        forma += "style=\"stroke:rgb(122, 255, 61);stroke-width:5\" />\"";
        conteudo += forma;

    }

    private static void geraCirculo() {
        String forma = "<circle ";
        forma += "cx=\"" + cx + "\" ";
        forma += "cy=\"" + cy + "\" ";
        forma += "r=\"" + r + "\" ";
        forma += "fill=\"none\" ";
        forma += "style=\"stroke:rgb(0, 0, 0);stroke-width:3;\" />\"";
        conteudo += forma;

    }

    private static void geraElipse() {
        String forma = "<ellipse ";
        forma += "cx=\"" + cx + "\" ";
        forma += "cy=\"" + cy + "\"";
        forma += "rx=\"" + rx + "\" ";
        forma += "ry=\"" + ry + "\"";
        forma += "style=\"fill:none;stroke:gray;stroke-width:5\" />\"";
        conteudo += forma;
    }

}
