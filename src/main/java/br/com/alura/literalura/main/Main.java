package br.com.alura.literalura.main;

import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoAPI;
import br.com.alura.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Main {
    private Scanner leitura = new Scanner(System.in);
    private final String URL = "https://gutendex.com/books?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();

    @Autowired
    private LivroRepository livroRepositorio;

    @Autowired
    private AutorRepository autorRepositorio;

    public Main(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    ================================================                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livro em um determinado idioma                                  
                    0 - Sair
                    ================================================
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivreIdioma();
                    break;
                case 0:
                    System.out.println("Encerrando");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }


    }

    public ResultadoDados buscaDadosLivro() {
        System.out.println("Digite o título do livro: ");
        var nomeLivro = leitura.nextLine();
        var json = consumoAPI.obterDados(URL + nomeLivro.replace(" ", "%20"));

        ResultadoDados dados = converteDados.obterDados(json, ResultadoDados.class);
        return dados;
    }

    private void buscarLivro() {
        ResultadoDados dados = buscaDadosLivro();
        Livro livro = new Livro(dados.resultado().get(0));
        livroRepositorio.save(livro);
        var autor = livro.getAutor().get(0).getNome();
        System.out.println("\n");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autor: " + autor);
        System.out.println("Idioma: " + livro.getIdioma());
        System.out.println("Downloads: " + livro.getNumeroDownloads());
    }


    private void listarLivros() {
        System.out.println("Lista de livros");
        System.out.println("================================================");

        livroRepositorio.findAll().forEach(l ->
                System.out.println(
                                "\nTítulo: " + l.getTitulo() + "\n" +
                                "Autor: " + l.getAutor().get(0).getNome() + "\n" +
                                "Idioma: " + l.getIdioma() + "\n" +
                                "Downloads: " + l.getNumeroDownloads() +
                                "\n================================================"
                )
        );
    }

    private void listarAutores() {
        System.out.println("Lista de autores");
        System.out.println("================================================");
        autores = autorRepositorio.findAll();

        autores.forEach(a -> System.out.println(
                        "\nNome: " + a.getNome() + "\n" +
                        "Data de Nascimento: " + a.getAnoNascimento() + "\n" +
                        "Data de Falecimento: " + a.getAnoFalecimento() + "\n" +
                        "================================================"
        ));

    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano:");
        var ano = leitura.nextInt();
        autores = autorRepositorio.findAll();

        autores.stream()
                .filter(a -> a.getAnoFalecimento() != null && ano <= a.getAnoFalecimento())
                .forEach(a -> System.out.println(
                        "\nNome: " + a.getNome() + "\n" +
                                "Data de Nascimento: " + a.getAnoNascimento() + "\n" +
                                "Data de Falecimento: " + a.getAnoFalecimento()
                ));
    }

    private void listarLivreIdioma() {
        System.out.println("Escolha o idioma: ");
        var idioma = leitura.nextLine();

        if (idioma.equalsIgnoreCase("portugues")) {
            List<Livro> livroIdioma = livroRepositorio.findByIdioma("pt");
            livroIdioma.forEach(l ->
                    System.out.println(
                            "\n------------------------------------------" +
                                    "\nTítulo: " + l.getTitulo() + "\n" +
                                    "Autor: " + l.getAutor().get(0).getNome() + "\n" +
                                    "Idioma: " + l.getIdioma() + "\n" +
                                    "Downloads: " + l.getNumeroDownloads() +
                                    "\n------------------------------------------"
                    )
            );
        } else if (idioma.equalsIgnoreCase("ingles")) {
            List<Livro> livroIdioma = livroRepositorio.findByIdioma("en");
            livroIdioma.forEach(l ->
                    System.out.println(
                            "\n------------------------------------------" +
                                    "\nTítulo: " + l.getTitulo() + "\n" +
                                    "Autor: " + l.getAutor().get(0).getNome() + "\n" +
                                    "Idioma: " + l.getIdioma() + "\n" +
                                    "Downloads: " + l.getNumeroDownloads() +
                                    "\n------------------------------------------"
                    )
            );
        } else if (idioma.equalsIgnoreCase("espanhol")) {
            List<Livro> livroIdioma = livroRepositorio.findByIdioma("es");
            livroIdioma.forEach(l ->
                    System.out.println(
                            "\n------------------------------------------" +
                                    "\nTítulo: " + l.getTitulo() + "\n" +
                                    "Autor: " + l.getAutor().get(0).getNome() + "\n" +
                                    "Idioma: " + l.getIdioma() + "\n" +
                                    "Downloads: " + l.getNumeroDownloads() +
                                    "\n------------------------------------------"
                    )
            );
        } else if (idioma.equalsIgnoreCase("italiano")) {
            List<Livro> livroIdioma = livroRepositorio.findByIdioma("it");
            livroIdioma.forEach(l ->
                    System.out.println(
                            "\n------------------------------------------" +
                                    "\nTítulo: " + l.getTitulo() + "\n" +
                                    "Autor: " + l.getAutor().get(0).getNome() + "\n" +
                                    "Idioma: " + l.getIdioma() + "\n" +
                                    "Downloads: " + l.getNumeroDownloads() +
                                    "\n------------------------------------------"
                    )
            );
        } else if (idioma.equalsIgnoreCase("frances")) {
            List<Livro> livroIdioma = livroRepositorio.findByIdioma("fr");
            livroIdioma.forEach(l ->
                    System.out.println(
                            "\n------------------------------------------" +
                                    "\nTítulo: " + l.getTitulo() + "\n" +
                                    "Autor: " + l.getAutor().get(0).getNome() + "\n" +
                                    "Idioma: " + l.getIdioma() + "\n" +
                                    "Downloads: " + l.getNumeroDownloads() +
                                    "\n------------------------------------------"
                    )
            );
        } else {
            System.out.println("Idioma inválido");
        }
    }
}