package br.edu.cs.poo.ac.seguro.telas;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.MaskFormatter;

public class UtilTela {

	public static final String[] ESTADOS = { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
			"MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };

	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("ddMMuuuu")
			.withResolverStyle(ResolverStyle.STRICT);

	private UtilTela() {
	}

	
	public static JFormattedTextField criarCampoComMascara(String mascara) {
		try {
			MaskFormatter formatador = new MaskFormatter(mascara);
			formatador.setPlaceholderCharacter('_');
			formatador.setValueContainsLiteralCharacters(false);
			return new JFormattedTextField(formatador);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Máscara inválida: " + mascara, e);
		}
	}

	
	public static JSpinner criarSpinnerValor() {
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.0E12, 100.0));
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "#,##0.00"));
		return spinner;
	}

	public static String lerDigitos(JFormattedTextField campo) {
		return campo.getText() == null ? "" : campo.getText().replaceAll("\\D", "");
	}

	public static void definirDigitos(JFormattedTextField campo, String digitos) {
		campo.setValue(digitos == null || digitos.isEmpty() ? null : digitos);
	}

	/** Devolve null se o campo estiver vazio; lança IllegalArgumentException se a data for inválida. */
	public static LocalDate lerData(JFormattedTextField campo) {
		String digitos = lerDigitos(campo);
		if (digitos.isEmpty()) {
			return null;
		}
		try {
			return LocalDate.parse(digitos, FORMATO_DATA);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Data inválida. Use o formato dd/mm/aaaa.");
		}
	}

	public static void definirData(JFormattedTextField campo, LocalDate data) {
		campo.setValue(data == null ? null : data.format(FORMATO_DATA));
	}

	public static double lerValor(JSpinner spinner) {
		try {
			spinner.commitEdit();
		} catch (ParseException e) {
			throw new IllegalArgumentException("Valor numérico inválido.");
		}
		return ((Number) spinner.getValue()).doubleValue();
	}

	public static String vazioParaNulo(String texto) {
		return texto == null || texto.trim().isEmpty() ? null : texto.trim();
	}

	
	public static void adicionarLinha(JPanel painel, int linha, String rotulo, JComponent campo) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = linha;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(3, 6, 3, 6);
		painel.add(new JLabel(rotulo), c);
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		painel.add(campo, c);
	}
}