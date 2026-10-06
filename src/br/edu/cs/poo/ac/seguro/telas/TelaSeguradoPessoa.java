package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;

public class TelaSeguradoPessoa extends JFrame {

	private static final long serialVersionUID = 1L;

	private final SeguradoPessoaMediator mediator = SeguradoPessoaMediator.getInstancia();

	private JFormattedTextField txtCpf;
	private JTextField txtNome;
	private JFormattedTextField txtDataNascimento;
	private JSpinner spnRenda;
	private JTextField txtBonus;

	private JTextField txtLogradouro;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JFormattedTextField txtCep;
	private JTextField txtCidade;
	private JComboBox<String> cmbEstado;
	private JTextField txtPais;

	public TelaSeguradoPessoa() {
		super("Segurado Pessoa");
		montarTela();
	}

	private void montarTela() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		txtCpf = UtilTela.criarCampoComMascara("###.###.###-##");
		txtNome = new JTextField(30);
		txtDataNascimento = UtilTela.criarCampoComMascara("##/##/####");
		spnRenda = UtilTela.criarSpinnerValor();
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
		painelDados.setBorder(BorderFactory.createTitledBorder("Dados do segurado"));
		UtilTela.adicionarLinha(painelDados, 0, "CPF:", txtCpf);
		UtilTela.adicionarLinha(painelDados, 1, "Nome:", txtNome);
		UtilTela.adicionarLinha(painelDados, 2, "Data de nascimento:", txtDataNascimento);
		UtilTela.adicionarLinha(painelDados, 3, "Renda (R$):", spnRenda);
		UtilTela.adicionarLinha(painelDados, 4, "Bônus (R$):", txtBonus);

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

	private SeguradoPessoa lerSegurado(BigDecimal bonus) {
		Endereco endereco = new Endereco(txtLogradouro.getText().trim(), UtilTela.lerDigitos(txtCep),
				UtilTela.vazioParaNulo(txtNumero.getText()), UtilTela.vazioParaNulo(txtComplemento.getText()),
				txtPais.getText().trim(), (String) cmbEstado.getSelectedItem(), txtCidade.getText().trim());
		return new SeguradoPessoa(txtNome.getText().trim(), endereco, UtilTela.lerData(txtDataNascimento), bonus,
				UtilTela.lerDigitos(txtCpf), UtilTela.lerValor(spnRenda));
	}

	private void preencher(SeguradoPessoa seg) {
		UtilTela.definirDigitos(txtCpf, seg.getCpf());
		txtNome.setText(seg.getNome());
		UtilTela.definirData(txtDataNascimento, seg.getDataNascimento());
		spnRenda.setValue(seg.getRenda());
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
		UtilTela.definirDigitos(txtCpf, null);
		txtNome.setText("");
		UtilTela.definirDigitos(txtDataNascimento, null);
		spnRenda.setValue(0.0);
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
		String cpf = UtilTela.lerDigitos(txtCpf);
		if (cpf.isEmpty()) {
			erro("CPF deve ser informado");
			return;
		}
		SeguradoPessoa seg = mediator.buscarSeguradoPessoa(cpf);
		if (seg == null) {
			erro("CPF do segurado pessoa não existente");
			return;
		}
		preencher(seg);
	}

	private void incluir() {
		try {
			String mensagem = mediator.incluirSeguradoPessoa(lerSegurado(BigDecimal.ZERO));
			resultado(mensagem, "Segurado pessoa incluído com sucesso");
		} catch (IllegalArgumentException ex) {
			erro(ex.getMessage());
		}
	}

	private void alterar() {
		try {
			SeguradoPessoa existente = mediator.buscarSeguradoPessoa(UtilTela.lerDigitos(txtCpf));
			BigDecimal bonus = existente != null ? existente.getBonus() : BigDecimal.ZERO;
			String mensagem = mediator.alterarSeguradoPessoa(lerSegurado(bonus));
			resultado(mensagem, "Segurado pessoa alterado com sucesso");
		} catch (IllegalArgumentException ex) {
			erro(ex.getMessage());
		}
	}

	private void excluir() {
		String cpf = UtilTela.lerDigitos(txtCpf);
		if (cpf.isEmpty()) {
			erro("CPF deve ser informado");
			return;
		}
		int opcao = JOptionPane.showConfirmDialog(this, "Excluir o segurado de CPF informado?", "Confirmar exclusão",
				JOptionPane.YES_NO_OPTION);
		if (opcao != JOptionPane.YES_OPTION) {
			return;
		}
		String mensagem = mediator.excluirSeguradoPessoa(cpf);
		if (mensagem == null) {
			limpar();
		}
		resultado(mensagem, "Segurado pessoa excluído com sucesso");
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
		SwingUtilities.invokeLater(() -> new TelaSeguradoPessoa().setVisible(true));
	}
}