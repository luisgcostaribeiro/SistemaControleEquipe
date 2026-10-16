import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class main {
    public static void main(String[] args) {

        ArrayList<String> produtos = new ArrayList<>();
        ArrayList<Double> precos = new ArrayList<>();
        ArrayList<Integer> estoques = new ArrayList<>();
        ArrayList<Integer> codigos = new ArrayList<>();
        ArrayList<String> historicocompras = new ArrayList<>();
        ArrayList<String> historicovendas = new ArrayList<>();


        boolean continuar = true;

        while (continuar) {

            String menu = JOptionPane.showInputDialog(null,
                    "CONTROLE DE ESTOQUE\n" +
                            "1 - Cadastrar novo produto\n" +
                            "2 - Listar produtos\n" +
                            "3 - Historico de vendas e compras\n" +
                            "4 - Registrar entrada de estoque\n" +
                            "5 - Registrar saida do estoque\n" +
                            "6 - Excluir Produto\n" +
                            "7 - Estoque zerado ou baixo\n" +
                            "8 - Relatorio financeiro\n" +
                            "9 - Atualizar preço\n" +
                            "10 - Sair\n\n" +
                            "Digite uma opção: ");

            try {

                int opcao = Integer.parseInt(menu);

                if (opcao == 10) {
                    continuar = false;

                    JOptionPane.showMessageDialog(null, "Programa Encerrado!");
                }

                if (opcao == 1) {
                    String nome = JOptionPane.showInputDialog(null, "Digite o nome do produto: ");
                    produtos.add(nome);
                    double preco = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite o preço do produto R$: "));
                    precos.add(preco);
                    estoques.add(0);
                    codigos.add(produtos.size());
                }

                if (opcao == 2) {
                    int escolha = Integer.parseInt((JOptionPane.showInputDialog(null, "Prefere listar todos os produtos? (1) ou Filtar os produtos por nome? (2)")));

                    if (escolha == 1) {
                        for (int i = 0; i < produtos.size(); i++) {
                            JOptionPane.showMessageDialog(null,
                                    " PRODUTO\n" +
                                            " Nome: " + produtos.get(i) + "\n" +
                                            " Preço: R$: " + precos.get(i) + "\n" +
                                            " Código: " + codigos.get(i) + "\n" +
                                            " Estoque: " + estoques.get(i));
                        }
                    }
                    if (escolha == 2) {
                        String nomebusca = JOptionPane.showInputDialog(null, "Digite o nome do produto que deseja procurar: ");
                        boolean encontrou = false;
                        for (int i = 0; i < produtos.size(); i++) {
                            if (produtos.get(i).toLowerCase().contains(nomebusca.toLowerCase())) {
                                encontrou = true;
                                JOptionPane.showMessageDialog(null,
                                        "PRODUTO\n" +
                                                " Nome: " + produtos.get(i) + "\n" +
                                                " Preço: R$: " + precos.get(i) + "\n" +
                                                " Código: " + codigos.get(i) + "\n" +
                                                " Estoque: " + estoques.get(i));
                            }
                        }
                        if (encontrou == false) {
                            JOptionPane.showMessageDialog(null, "Nenhum produto foi encontrado!");
                        }
                    }
                }

                if (opcao == 3) {
                    for (int i = 0; i < historicocompras.size(); i++) {
                        JOptionPane.showMessageDialog(null, historicocompras.get(i));
                    }
                    if (historicocompras.size() == 0) {
                        JOptionPane.showMessageDialog(null, "Não há historico de compras!");
                    }
                    for (int i = 0; i < historicovendas.size(); i++) {
                        JOptionPane.showMessageDialog(null, historicovendas.get(i));
                    }
                    if (historicovendas.size() == 0) {
                        JOptionPane.showMessageDialog(null, "Não há historico de vendas!");
                    }
                }

                if (opcao == 4) {
                    int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o codigo do produto: "));
                    boolean codigoencontrado = false;

                    for (int i = 0; i < codigos.size(); i++) {
                        if (codigos.get(i) == codigo) {
                            codigoencontrado = true;

                            int quantidade = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a quantidade que entrou no estoque: "));

                            if (quantidade > 0) {
                                estoques.set(i, estoques.get(i) + quantidade);
                                LocalDateTime agora = LocalDateTime.now();
                                DateTimeFormatter formato = DateTimeFormatter.ofPattern(" dd/MM/yyyy  HH:mm:ss ");
                                historicocompras.add("Código: " + codigo + "  | Produto: " + produtos.get(i) + " | Quantidade: " + quantidade + " | Data/Hora: " + agora.format(formato));

                            } else {
                                JOptionPane.showMessageDialog(null, "A quantidade deve ser maior que 0.");
                            }
                            if (codigoencontrado == false) {
                                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                            }
                        }
                    }
                }
                if (opcao == 5) {
                    int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o código do produto: "));
                    boolean codigoencontrado = false;

                    for (int i = 0; i < codigos.size(); i++) {
                        if (codigos.get(i) == codigo) {
                            codigoencontrado = true;

                            int quantidadeSaiu = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a quantidade que foi vendida: "));

                            if (quantidadeSaiu > estoques.get(i)) {
                                JOptionPane.showMessageDialog(null, "FALHA NA VENDA: Estoque insuficiente.\n" +
                                        "Apenas " + estoques.get(i) + " unidades disponíveis.");
                            } else {
                                estoques.set(i, estoques.get(i) - quantidadeSaiu);

                                double subtotal = quantidadeSaiu * precos.get(i);

                                LocalDateTime agora = LocalDateTime.now();
                                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                                historicovendas.add("Código: " + codigo + " | Produto: " + produtos.get(i)
                                        + " | Quantidade: " + quantidadeSaiu
                                        + " | Valor unitário: R$ " + precos.get(i)
                                        + " | Subtotal: R$ " + subtotal
                                        + " | Data/Hora: " + agora.format(formato));}
                        }
                    }
                }
                if (opcao == 6) {

                    if (produtos.size() == 0) {
                        JOptionPane.showMessageDialog(null, " Nenhum produto cadastrado para excluir! ");
                    } else {
                        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o codigo do produto que deseja excluir: "));

                        boolean codigoencontrado = false;

                        for (int i = 0; i < codigos.size(); i++) {
                            if (codigos.get(i) == codigo) {
                                codigoencontrado = true;

                                int confirmacao = JOptionPane.showConfirmDialog(null, " Deseja realmente excluir o produto? \n\n" +
                                        " Código: " + codigos.get(i) +"\n" +
                                        " Produto: " + produtos.get(i) +"\n" +
                                        " Preço: " + precos.get(i) + "\n" +
                                        " Estoque: " + estoques.get(i) + "\n" + " Confirmar exclusão " +
                                        JOptionPane.YES_NO_OPTION);

                                if (confirmacao == JOptionPane.YES_NO_OPTION) {
                                    produtos.remove(i);
                                    precos.remove(i);
                                    estoques.remove(i);
                                    codigos.remove(i);

                                    JOptionPane.showMessageDialog(null, " Produto excluido com sucesso! ");
                                }
                            }

                            if (codigoencontrado == false) {
                                JOptionPane.showMessageDialog(null, "Produto não encontrado! ");
                            }
                        }
                    }
                }
                if (opcao == 7) {
                    if (produtos.size() == 0) {
                        JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado no sistema.");
                    } else {

                        String estoqueZerado = "--- ESTOQUE ZERADO ---\n";
                        String estoqueBaixo = "--- ESTOQUE BAIXO ---\n";

                        int qtdZerado = 0;
                        int qtdBaixo = 0;

                        for (int i = 0; i < produtos.size(); i++) {

                            if (estoques.get(i) == 0) {
                                qtdZerado++;

                                estoqueZerado += "Código: " + codigos.get(i)
                                        + " | Nome: " + produtos.get(i)
                                        + " | Preço: R$ " + precos.get(i) + "\n";
                            }

                            if (estoques.get(i) >= 1 && estoques.get(i) <= 5) {
                                qtdBaixo++;

                                estoqueBaixo += "Código: " + codigos.get(i)
                                        + " | Nome: " + produtos.get(i)
                                        + " | Estoque: " + estoques.get(i)
                                        + " | Reposição necessária!\n";
                            }
                        }

                        if (qtdZerado == 0 && qtdBaixo == 0) {
                            JOptionPane.showMessageDialog(null,
                                    "Todos os estoques estão regularizados!");
                        } else {

                            if (qtdZerado == 0) {
                                estoqueZerado += "Nenhum produto com estoque zerado.\n";
                            }

                            if (qtdBaixo == 0) {
                                estoqueBaixo += "Nenhum produto com estoque baixo.\n";
                            }

                            JOptionPane.showMessageDialog(null, estoqueZerado + "\n" + estoqueBaixo);
                        }
                    }
                }

                if (opcao == 8) {
                    double valorTotalEstoque = 0;
                    double faturamentoTotal = 0;

                    for (int i = 0; i < produtos.size(); i++) {
                        valorTotalEstoque += precos.get(i) * estoques.get(i);
                    }

                    for (int i = 0; i < historicovendas.size(); i++) {
                        String venda = historicovendas.get(i);
                        int posicao = venda.indexOf("Subtotal: R$ ");
                        if (posicao != -1) {
                            String valor = venda.substring(posicao + 13);
                            valor = valor.split(" \\|")[0];
                            faturamentoTotal += Double.parseDouble(valor);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "RELATÓRIO FINANCEIRO\n\n" + "Valor total em estoque: R$ " + valorTotalEstoque + "\n" + "Faturamento total em vendas: R$ " + faturamentoTotal);
                }

                if (opcao == 9) {
                    int codigo = Integer.parseInt(
                            JOptionPane.showInputDialog(null, "Digite o código do produto: "));
                    boolean codigoencontrado = false;
                    for (int i = 0; i < codigos.size(); i++) {
                        if (codigos.get(i) == codigo) {
                            codigoencontrado = true;
                            double novoPreco = Double.parseDouble(
                                    JOptionPane.showInputDialog(null,
                                            "Preço atual: R$ " + precos.get(i) +
                                                    "\nDigite o novo preço: "));
                            if (novoPreco > 0) {
                                precos.set(i, novoPreco);
                                JOptionPane.showMessageDialog(null, "Preço atualizado com sucesso!");

                            } else {
                                JOptionPane.showMessageDialog(null, "O preço deve ser maior que 0.");
                            }
                        }
                    }
                    if (codigoencontrado == false) {
                        JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                    }
                }
                        } catch(NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Digite apenas números validos!");
                        }
                    }
                }
            }