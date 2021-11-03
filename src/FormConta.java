import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;



public class FormConta extends JFrame implements ActionListener {

    private final ContaRepositorio repositorio;

    private JLabel lblContas;
    private JLabel lblNumero;
    private JLabel lblNome;
    private JTable tblConta;
    private JButton btnDepositar;
    private JButton btnSacar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAdicionar;
    private JButton btnSair;
    private JPanel pnlNovaConta;
    private JTextField txtNumero;
    private JTextField txtNome;
    private DefaultTableModel model;

    public FormConta() {
        Connection conexao = Conexao.getInstance();
        repositorio = new ContaRepositorio(conexao);
        inicializaComponentes();
        configuraEventos();
        refreshTable();
    }

    private void inicializaComponentes() {
        
        setTitle("Controle Bancário");
        setLayout(null);
        setBounds(0, 0, 580, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblContas = new JLabel("Contas");
        lblNumero = new JLabel("Número");
        lblNome = new JLabel("Nome");

        btnDepositar = new JButton("Depositar");
        btnSacar = new JButton("Sacar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAdicionar = new JButton("Adicionar");
        btnSair = new JButton("Sair");

        txtNumero = new JTextField();
        txtNome = new JTextField();

        pnlNovaConta = new JPanel();
        pnlNovaConta.setBorder(BorderFactory.createEtchedBorder());

        model = new DefaultTableModel();
        model.addColumn("Número");
        model.addColumn("Nome");
        model.addColumn("Saldo");
        
        tblConta = new JTable(model);
        tblConta.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblConta.getColumnModel().getColumn(1).setPreferredWidth(330);
        tblConta.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblConta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTable = new JScrollPane(tblConta);

        lblContas.setBounds(20, 5, 100, 25);
        scrollTable.setBounds(20, 35, 540, 300);
        btnDepositar.setBounds(20, 340, 100, 30);
        btnSacar.setBounds(125, 340, 100, 30);
        btnEditar.setBounds(230, 340, 100, 30);
        btnExcluir.setBounds(380, 340, 100, 30);
        pnlNovaConta.setBounds(20, 375, 540, 100);
        pnlNovaConta.setLayout(null);
        lblNumero.setBounds(10, 10, 100, 30);
        lblNome.setBounds(120, 10, 300, 30);
        txtNumero.setBounds(10, 45, 100, 30);
        txtNome.setBounds(120, 45, 300, 30);
        btnAdicionar.setBounds(430, 45, 100, 30);
        btnSair.setBounds(20, 480, 100, 30);

        add(lblContas);
        add(lblNumero);
        add(lblNome);
        add(btnAdicionar);
        add(btnDepositar);
        add(btnEditar);
        add(btnExcluir);
        add(btnSacar);
        add(btnSair);
        add(scrollTable);
        add(pnlNovaConta);
        pnlNovaConta.add(lblNumero);
        pnlNovaConta.add(lblNome);
        pnlNovaConta.add(txtNumero);
        pnlNovaConta.add(txtNome);
        pnlNovaConta.add(btnAdicionar);

    }

    private void configuraEventos() {
        btnAdicionar.addActionListener(this);
        btnDepositar.addActionListener(this);
        btnEditar.addActionListener(this);
        btnExcluir.addActionListener(this);
        btnSacar.addActionListener(this);
        btnSair.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdicionar)  actionAdicionar();
        if (e.getSource() == btnDepositar)  actionDepositar();
        if (e.getSource() == btnEditar)  actionEditar();
        if (e.getSource() == btnExcluir)  actionExcluir();
        if (e.getSource() == btnSacar)  actionSacar();
        if (e.getSource() == btnSair)  actionSair();
    }

    private void actionAdicionar() {
        String numero = txtNumero.getText();
        String nome = txtNome.getText();
        ContaBancaria novaConta = new ContaBancaria(numero, nome);
        repositorio.adicionar(novaConta);

        txtNumero.setText("");
        txtNome.setText("");
        refreshTable();
    }
    
    private void actionDepositar() {
        if (tblConta.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma conta bancária para efetuar o depósito!", 
                                        "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int pos = tblConta.getSelectedRow();
        String numero = tblConta.getValueAt(pos, 0).toString();
        String valor = JOptionPane.showInputDialog(null, "Informe o valor a ser depositado:", "0.00");
        try {
            BigDecimal valorDeposito = new BigDecimal(valor);
            ContaBancaria conta = repositorio.pesquisar(numero);
            if (conta != null) {
                conta.depositar(valorDeposito);
                repositorio.atualizar(conta);
                refreshTable();
                tblConta.setRowSelectionInterval(pos, pos);
            }
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Valor inválido", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actionEditar() {
        if (tblConta.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma conta bancária para editar!", 
                                        "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int pos = tblConta.getSelectedRow();
        String numero = tblConta.getValueAt(tblConta.getSelectedRow(), 0).toString();
        String nome = tblConta.getValueAt(tblConta.getSelectedRow(), 1).toString();
        String novoNome = JOptionPane.showInputDialog(null, "Informe o nome do cliente", nome);
        if (novoNome != null) {
            if (!novoNome.trim().isEmpty()) {
                ContaBancaria conta;
                conta = repositorio.pesquisar(numero);
                if (conta != null) {
                    conta.setNome(novoNome);
                    repositorio.atualizar(conta);
                    refreshTable();
                    tblConta.setRowSelectionInterval(pos, pos);
                }
            }
        }
    }

    private void actionExcluir() {
        if (tblConta.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma conta bancária para realizar a exclusão!", 
                                        "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String numero = tblConta.getValueAt(tblConta.getSelectedRow(), 0).toString();
        int resposta = JOptionPane.showConfirmDialog (null, "Deseja remover a conta de número " + numero + "?", 
                                                        "Atenção", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            repositorio.remover(numero);
            refreshTable();
        }
    }

    private void actionSacar() {
        if (tblConta.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma conta bancária para efeutar o saque!", 
                                        "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int pos = tblConta.getSelectedRow();
        String numero = tblConta.getValueAt(pos, 0).toString();
        String valor = JOptionPane.showInputDialog(null, "Informe o valor a ser sacado:", "0.00");
        try {
            BigDecimal valorSaque = new BigDecimal(valor);
            ContaBancaria conta = repositorio.pesquisar(numero);
            if (conta != null) {
                try {
                    conta.sacar(valorSaque);
                    repositorio.atualizar(conta);
                    refreshTable();
                    tblConta.setRowSelectionInterval(pos, pos);
                }
                catch (SaldoInsuficienteException ex) {
                    JOptionPane.showMessageDialog(null, "Saldo insuficiente!", "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            }
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Valor inválido", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actionSair() {
        System.exit(0);
    }

    private void refreshTable() {
        List<ContaBancaria> lista = repositorio.pesquisar();
        model.setRowCount(0);
        for (ContaBancaria conta:lista) {
            model.addRow(new Object[]{conta.getNumero(), conta.getNome(), conta.getSaldo()});
        }
    }
    
}
