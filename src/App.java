import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

// Mais informações em: https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#data

public class App {
    private CatalogoAplicativos catApps;
    private CatalogoClientes catClientes;
    private CatalogoAplicativosViewModel catAppsVM;
    private CatalogoAssinaturas catAssinaturas;
    private CatalogoClientesViewModel catClientesVM;

    private JTextField tfCodigo;
    private JTextField tfNome;
    private JTextField tfPreco;

    private JTextField tfNomeCliente;
    private JTextField tfCPF;
    private JTextField tfEmailCliente;

    private JComboBox<Aplicativo.SO> cbSo; // ComboBox dos Sistemas Operacionais
    private JButton btAdd;

    private JTextField tfCodigoAssinatura;
    private JTextField tfInicioVigencia;
    private JComboBox<Aplicativo> appsSelecionaveis;
    

    public App() {
        // Add outras tabelas
        catApps = new CatalogoAplicativos("apps.dat");
        catApps.loadFromFile();
        catClientes = new CatalogoClientes("clientes.dat");
        catClientes.loadFromFile();
        catAssinaturas = new CatalogoAssinaturas("assinaturas.dat", catApps, catClientes);
        catAssinaturas.loadFromFile();
    }

    public void criaJanela() throws Exception {
        catAppsVM = new CatalogoAplicativosViewModel(catApps);
        JTable tabela = new JTable(catAppsVM);
        tabela.setFillsViewportHeight(true);

        catClientesVM = new CatalogoClientesViewModel(catClientes);
        JTable tabelaClientes = new JTable(catClientesVM);
        tabelaClientes.setFillsViewportHeight(true);

        JFrame frame = new JFrame("Gestão de Apps");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Salvar dados antes de sair
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                
                catApps.saveToFile();
                catClientes.saveToFile();
                catAssinaturas.saveToFile();
                frame.dispose();
            }
        });

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(tabela, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha1.add(scrollPane);

        JPanel jpNovoApp = criaPainelNovoApp();

        // Mudar o editor da tabela para poder editar SO com dropdown
        TableColumn columnSo = tabela.getColumnModel().getColumn(3);
        columnSo.setCellEditor(new SOCellEditor());
        columnSo.setCellRenderer(new SOCellRenderer());

        // Layout

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(1, 2)); // Use GridLayout with 1 row and 2 columns

        // Add the first table (tabela) to the left side
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(linha1);
        leftPanel.add(jpNovoApp);
        contentPane.add(leftPanel);

        // Add the second table (tabelaClientes) to the right side
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollPaneClientes = new JScrollPane(tabelaClientes,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Botao da tabela cliente
        TableColumn columnVerAssinaturas = tabelaClientes.getColumnModel().getColumn(3);
        columnVerAssinaturas.setCellRenderer(new ButtonRenderer());
        columnVerAssinaturas.setCellEditor(new ButtonEditor());

        tabelaClientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tabelaClientes.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / tabelaClientes.getRowHeight();
    
                // Check if the click is within the "Ver assinaturas" column
                if (row < tabelaClientes.getRowCount() && column == 3) {
                    // Pegar valor só do cpf
                    Object cpfValue = tabelaClientes.getValueAt(row, 0);
                    if (cpfValue != null) { // Evitar bugs
                        String cpf = cpfValue.toString();
                        // Pegar o cliente da row
                        Cliente clienteClicado = catClientes.getClienteByCpf(cpf);
                        painelAssinatura(clienteClicado);
                        //clienteClicado.getAssinaturas().stream().forEach(a -> System.out.println(a.getCodigoAssinatura()));
                    }
                    
                }
            }
        });

        // Botao da tabela apps
        TableColumn columnVerAssinaturasApps = tabela.getColumnModel().getColumn(4);
        columnVerAssinaturasApps.setCellRenderer(new ButtonRenderer());
        columnVerAssinaturasApps.setCellEditor(new ButtonEditor());

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tabela.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / tabela.getRowHeight();
    
                // Check if the click is within the "Ver assinaturas" column
                if (row < tabela.getRowCount() && column == 4) {
                    // Pegar valor do codigo do app
                    Object codigoValue = tabela.getValueAt(row, 0);
                    if (codigoValue != null) { // Evitar bugs
                        Integer cod = (Integer) codigoValue;
                        // Pegar o cliente da row
                        Aplicativo appClicado = catApps.getCodigoAplicativo(cod);
                        painelAssinaturaApp(appClicado);
                        //clienteClicado.getAssinaturas().stream().forEach(a -> System.out.println(a.getCodigoAssinatura()));
                    }
                    
                }
            }
        });
        

        JPanel jpNovoCliente = criaPainelNovoCliente();

        rightPanel.add(scrollPaneClientes);
        rightPanel.add(jpNovoCliente);

        contentPane.add(rightPanel);

        //

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
     public void painelAssinaturaApp(Aplicativo appClicado){
        JFrame frame = new JFrame(appClicado.getNome());
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        CatalogoAssinaturas catAssinaturasCliente = new CatalogoAssinaturas(appClicado.getAssinaturas());
        

        CatalogoAssinaturasViewModel catAssinaturasVM = new CatalogoAssinaturasViewModel(catAssinaturasCliente, false);
        JTable tabelaAssinatura = new JTable(catAssinaturasVM);
        tabelaAssinatura.setFillsViewportHeight(true);

        
        JScrollPane scrollPane = new JScrollPane(tabelaAssinatura, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        


        frame.add(scrollPane);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
     }

    public void painelAssinatura(Cliente clienteClicado){
        JFrame frame = new JFrame(clienteClicado.getNome());
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        CatalogoAssinaturas catAssinaturasCliente = new CatalogoAssinaturas(clienteClicado.getAssinaturas());
        

        CatalogoAssinaturasViewModel catAssinaturasVM = new CatalogoAssinaturasViewModel(catAssinaturasCliente, true);
        JTable tabelaAssinatura = new JTable(catAssinaturasVM);
        tabelaAssinatura.setFillsViewportHeight(true);

        
        JScrollPane scrollPane = new JScrollPane(tabelaAssinatura, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        

        JPanel jpNovaAssinatura = criaPainelNovaAssinatura(clienteClicado, catAssinaturasVM);

        frame.add(scrollPane);
        frame.add(jpNovaAssinatura);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    

    public JPanel criaPainelNovaAssinatura(Cliente cliente, CatalogoAssinaturasViewModel cAssinaturasVM) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.PAGE_AXIS));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha1.add(new JLabel("Codigo Assinatura"));
        tfCodigoAssinatura = new JTextField(10);
        linha1.add(tfCodigoAssinatura);
        linha1.add(new JLabel("Início vigência"));
        tfInicioVigencia = new JTextField(10);
        linha1.add(tfInicioVigencia);
        linha1.add(new JLabel("Aplicativo"));
        appsSelecionaveis = new JComboBox<>();
        catApps.getStream().forEach(app -> appsSelecionaveis.addItem(app));
        linha1.add(appsSelecionaveis);
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEADING));

        btAdd = new JButton("Nova Assinatura");
        btAdd.addActionListener(e -> adicionaAssinatura(cliente, cAssinaturasVM));
        linha2.add(btAdd);

        painel.add(linha1);
        painel.add(linha2);
        return painel;
    }
    public void adicionaAssinatura(Cliente clienteClicado, CatalogoAssinaturasViewModel cAssinaturasVM){
        Aplicativo app = (Aplicativo) appsSelecionaveis.getSelectedItem();
        try {
            int codigoAssinatura = Integer.parseInt(tfCodigoAssinatura.getText());
            String inicioVigencia = tfInicioVigencia.getText();
            if(inicioVigencia.isEmpty()){
                showMessageDialog(null, "Formato inválido, não deixe campos em branco");
            }else{
                Assinatura novaAssinatura = new Assinatura(clienteClicado, app, codigoAssinatura, inicioVigencia);
                catAssinaturas.cadastra(novaAssinatura);
                cAssinaturasVM.fireTableDataChanged();
            }
        }
        catch (NumberFormatException e){
            showMessageDialog(null, "Formato inválido");
        }


    }

    public JPanel criaPainelNovoCliente() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.PAGE_AXIS));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha1.add(new JLabel("Nome"));
        tfNomeCliente = new JTextField(10);
        linha1.add(tfNomeCliente);
        linha1.add(new JLabel("CPF"));
        tfCPF = new JTextField(10);
        linha1.add(tfCPF);
        linha1.add(new JLabel("Email"));
        tfEmailCliente = new JTextField(15);
        linha1.add(tfEmailCliente);
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEADING));

        btAdd = new JButton("Novo Cliente");
        btAdd.addActionListener(e -> adicionaCliente());
        linha2.add(btAdd);


        JButton btFat = new JButton("Pagantes");
        btFat.addActionListener(e -> viewPagantes());
        linha2.add(btFat);

        painel.add(linha1);
        painel.add(linha2);
        return painel;
    }

    public void viewPagantes(){

        CatalogoClientes clientesPag = new CatalogoClientes(null);

        List<Cliente> listaClientesPag = catClientes.getStream().filter(c -> c.isPagante()).toList();
        clientesPag.setItens(listaClientesPag);

        CatalogoClientesPagantesViewModel catClientesPagVM = new CatalogoClientesPagantesViewModel(clientesPag);

        JTable tabela = new JTable(catClientesPagVM);
        tabela.setFillsViewportHeight(true);


        JScrollPane scrollPane = new JScrollPane(tabela, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);



        

        JFrame frame = new JFrame("Pagantes");
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void adicionaCliente() {

        String nome = tfNomeCliente.getText();
        String CPF = tfCPF.getText();
        String email = tfEmailCliente.getText();
        if (nome.isEmpty() || CPF.isEmpty() || email.isEmpty()) {
            showMessageDialog(null, "Formato inválido, não deixe campos em branco");
        } else {

            Cliente novo = new Cliente(nome, CPF, email);
            catClientes.cadastra(novo);
            catClientesVM.fireTableDataChanged();
        }

    }

    public JPanel criaPainelNovoApp() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.PAGE_AXIS));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha1.add(new JLabel("Codigo"));
        tfCodigo = new JTextField(10);
        linha1.add(tfCodigo);
        linha1.add(new JLabel("Nome"));
        tfNome = new JTextField(20);
        linha1.add(tfNome);
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha2.add(new JLabel("Preco"));
        tfPreco = new JTextField(10);
        linha2.add(tfPreco);
        linha2.add(new JLabel("Sist. Oper."));
        cbSo = new JComboBox<>(Aplicativo.SO.values());

        linha2.add(cbSo);
        btAdd = new JButton("Novo App");
        btAdd.addActionListener(e -> adicionaApp());
        linha2.add(btAdd);

        JButton btFat = new JButton("Faturamento");
        btFat.addActionListener(e -> viewFaturamento());
        linha2.add(btFat);


        painel.add(linha1);
        painel.add(linha2);
        return painel;
    }
    public void viewFaturamento(){
        double faturamentoTotal;
        double faturamentoIOS;
        double faturamentoAndroid;
       

        faturamentoTotal = catApps.getStream().mapToDouble(a -> a.valorAssinaturas()).sum();
        faturamentoIOS = catApps.getStream().filter(a -> a.getSo().equals(Aplicativo.SO.IOS)).mapToDouble(a -> a.valorAssinaturas()).sum();
        faturamentoAndroid = catApps.getStream().filter(a -> a.getSo().equals(Aplicativo.SO.Android)).mapToDouble(a -> a.valorAssinaturas()).sum();
        
        JFrame frame = new JFrame("Faturamento");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        JLabel linha1 = new JLabel("Total: R$" + faturamentoTotal);
        JLabel linha2 = new JLabel("IOS: R$" + faturamentoIOS);
        JLabel linha3 = new JLabel("Android: R$" + faturamentoAndroid);
        frame.add(linha1);
        frame.add(linha2);
        frame.add(linha3);

        Font biggerFont = new Font("Arial", Font.PLAIN, 20);
        linha1.setFont(biggerFont);
        linha2.setFont(biggerFont);
        linha3.setFont(biggerFont);

        frame.setSize(300,200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void adicionaApp() {
        try {

            int codigo = Integer.parseInt(tfCodigo.getText());
            String nome = tfNome.getText();
            if (nome.isEmpty()) {
                showMessageDialog(null, "Formato inválido, não é permitido criar apps sem nome");
            } else {
                double preco = Double.parseDouble(tfPreco.getText());
                Aplicativo.SO so = (Aplicativo.SO) cbSo.getSelectedItem();
                Aplicativo novo = new Aplicativo(codigo, nome, preco, so);
                catApps.cadastra(novo);
                catAppsVM.fireTableDataChanged();
            }
        } catch (NumberFormatException e) {
            showMessageDialog(null, "Formato inválido, tente novamente");
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.criaJanela();
    }
}