package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;

public class TelaSeguradoEmpresa extends JFrame {

	private static final long serialVersionUID = 1L;

	private final SeguradoEmpresaMediator mediator = SeguradoEmpresaMediator.getInstancia();

	private JFormattedTextField txtCnpj;
	private JTextField txtNome;
	private JFormattedTextField txtDataAbertura;
	private JSpinner spnFaturamento;
	private JCheckBox chkLocadora;
	private JTextField txtBonus;

	private JTextField txtLogradouro;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JFormattedTextField txtCep;
	private JTextField txtCidade;
	private JComboBox<String> cmbEstado;
	private JTextField txtPais;

	public TelaSeguradoEmpresa() {
		super("Segurado Empresa");
		montarTela();
	}

	private void montarTela() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		txtCnpj = UtilTela.criarCampoComMascara("##.###.###/####-##");
		txtNome = new JTextField(30);
		txtDataAbertura = UtilTela.criarCampoComMascara("##/##/####");
		spnFaturamento = UtilTela.criarSpinnerValor();
		chkLocadora = new JCheckBox("É locadora de veículos");
		txtBonus = new JTextField("0.00", 10);
		txtBonus.setEditable(false);

		txtLogradouro = new JTextField(30);
		txtNumero = new JTextField(10);
		txtComplemento = new JTextField(20);
		txtCep = UtilTela.criarCampoComMascara("#####-###");
		txtCidade = new JTextField(20);
		cmbEstado = new JComboBox<>(UtilTela.ESTADOS);
		txtPais = new JTextField("Brasil", 20);

		JPanel painelDados = new JPanel(new GridBagLayout());
		painelDados.setBorder(BorderFactory.createTitledBorder("Dados da empresa"));
		UtilTela.adicionarLinha(painelDados, 0, "CNPJ:", txtCnpj);
		UtilTela.adicionarLinha(painelDados, 1, "Nome:", txtNome);
		UtilTela.adicionarLinha(painelDados, 2, "Data de abertura:", txtDataAbertura);
		UtilTela.adicionarLinha(painelDados, 3, "Faturamento (R$):", spnFaturamento);
		UtilTela.adicionarLinha(painelDados, 4, "", chkLocadora);
		UtilTela.adicionarLinha(painelDados, 5, "Bônus (R$):", txtBonus);

		JPanel painelEndereco = new JPanel(new GridBagLayout());
		painelEndereco.setBorder(BorderFactory.createTitledBorder("Endereço"));
		UtilTela.adicionarLinha(painelEndereco, 0, "Logradouro:", txtLogradouro);
		UtilTela.adicionarLinha(painelEndereco, 1, "Número:", txtNumero);
		UtilTela.adicionarLinha(painelEndereco, 2, "Complemento:", txtComplemento);
		UtilTela.adicionarLinha(painelEndereco, 3, "CEP:", txtCep);
		UtilTela.adicionarLinha(painelEndereco, 4, "Cidade:", txtCidade);
		UtilTela.adicionarLinha(painelEndereco, 5, "Estado:", cmbEstado);
		UtilTela.adicionarLinha(painelEndereco, 6, "País:", txtPais);

		JPanel painelCampos = new JPanel();
		painelCampos.setLayout(new BoxLayout(painelCampos, BoxLayout.Y_AXIS));
		painelCampos.add(painelDados);
		painelCampos.add(painelEndereco);

		JButton btnBuscar = new JButton("Buscar");
		JButton btnIncluir = new JButton("Incluir");
		JButton btnAlterar = new JButton("Alterar");
		JButton btnExcluir = new JButton("Excluir");
		JButton btnLimpar = new JButton("Limpar");
		btnBuscar.addActionListener(e -> buscar());
		btnIncluir.addActionListener(e -> incluir());
		btnAlterar.addActionListener(e -> alterar());
		btnExcluir.addActionListener(e -> excluir());
		btnLimpar.addActionListener(e -> limpar());

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelBotoes.add(btnBuscar);
		painelBotoes.add(btnIncluir);
		painelBotoes.add(btnAlterar);
		painelBotoes.add(btnExcluir);
		painelBotoes.add(btnLimpar);

		setLayout(new BorderLayout());
		add(painelCampos, BorderLayout.CENTER);
		add(painelBotoes, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}

	private SeguradoEmpresa lerSegurado(BigDecimal bonus) {
		Endereco endereco = new Endereco(txtLogradouro.getText().trim(), UtilTela.lerDigitos(txtCep),
				UtilTela.vazioParaNulo(txtNumero.getText()), UtilTela.vazioParaNulo(txtComplemento.getText()),
				txtPais.getText().trim(), (String) cmbEstado.getSelectedItem(), txtCidade.getText().trim());
		return new SeguradoEmpresa(txtNome.getText().trim(), endereco, UtilTela.lerData(txtDataAbertura), bonus,
				UtilTela.lerDigitos(txtCnpj), UtilTela.lerValor(spnFaturamento), chkLocadora.isSelected());
	}

	private void preencher(SeguradoEmpresa seg) {
		UtilTela.definirDigitos(txtCnpj, seg.getCnpj());
		txtNome.setText(seg.getNome());
		UtilTela.definirData(txtDataAbertura, seg.getDataAbertura());
		spnFaturamento.setValue(seg.getFaturamento());
		chkLocadora.setSelected(seg.isEhLocadoraDeVeiculos());
		txtBonus.setText(seg.getBonus() == null ? "0.00" : seg.getBonus().toPlainString());

		Endereco end = seg.getEndereco();
		if (end != null) {
			txtLogradouro.setText(end.getLogradouro());
			txtNumero.setText(end.getNumero());
			txtComplemento.setText(end.getComplemento());
			UtilTela.definirDigitos(txtCep, end.getCep());
			txtCidade.setText(end.getCidade());
			cmbEstado.setSelectedItem(end.getEstado());
			txtPais.setText(end.getPais());
		}
	}

	private void limpar() {
		UtilTela.definirDigitos(txtCnpj, null);
		txtNome.setText("");
		UtilTela.definirDigitos(txtDataAbertura, null);
		spnFaturamento.setValue(0.0);
		chkLocadora.setSelected(false);
		txtBonus.setText("0.00");
		txtLogradouro.setText("");
		txtNumero.setText("");
		txtComplemento.setText("");
		UtilTela.definirDigitos(txtCep, null);
		txtCidade.setText("");
		cmbEstado.setSelectedIndex(0);
		txtPais.setText("Brasil");
	}

	private void buscar() {
		String cnpj = UtilTela.lerDigitos(txtCnpj);
		if (cnpj.isEmpty()) {
			erro("CNPJ deve ser informado");
			return;
		}
		SeguradoEmpresa seg = mediator.buscarSeguradoEmpresa(cnpj);
		if (seg == null) {
			erro("CNPJ do segurado empresa não existente");
			return;
		}
		preencher(seg);
	}

	private void incluir() {
		try {
			String mensagem = mediator.incluirSeguradoEmpresa(lerSegurado(BigDecimal.ZERO));
			resultado(mensagem, "Segurado empresa incluído com sucesso");
		} catch (IllegalArgumentException ex) {
			erro(ex.getMessage());
		}
	}

	private void alterar() {
		try {
			SeguradoEmpresa existente = mediator.buscarSeguradoEmpresa(UtilTela.lerDigitos(txtCnpj));
			BigDecimal bonus = existente != null ? existente.getBonus() : BigDecimal.ZERO;
			String mensagem = mediator.alterarSeguradoEmpresa(lerSegurado(bonus));
			resultado(mensagem, "Segurado empresa alterado com sucesso");
		} catch (IllegalArgumentException ex) {
			erro(ex.getMessage());
		}
	}

	private void excluir() {
		String cnpj = UtilTela.lerDigitos(txtCnpj);
		if (cnpj.isEmpty()) {
			erro("CNPJ deve ser informado");
			return;
		}
		int opcao = JOptionPane.showConfirmDialog(this, "Excluir o segurado de CNPJ informado?", "Confirmar exclusão",
				JOptionPane.YES_NO_OPTION);
		if (opcao != JOptionPane.YES_OPTION) {
			return;
		}
		String mensagem = mediator.excluirSeguradoEmpresa(cnpj);
		if (mensagem == null) {
			limpar();
		}
		resultado(mensagem, "Segurado empresa excluído com sucesso");
	}

	private void resultado(String mensagemErro, String mensagemSucesso) {
		if (mensagemErro != null) {
			erro(mensagemErro);
		} else {
			JOptionPane.showMessageDialog(this, mensagemSucesso, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void erro(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// mantém o visual padrão
		}
		SwingUtilities.invokeLater(() -> new TelaSeguradoEmpresa().setVisible(true));
	}
}