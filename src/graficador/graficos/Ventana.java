package graficador.graficos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import graficador.expresion_algebraica.Elemento;

public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fieldFuncion;
	private Grafica grafica;
	
	// Constructor
	public Ventana() {
		super("Graficador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(800,550);		
		setSize(size);
		setMinimumSize(size);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	
		contentPane.setBackground(Color.WHITE);
		
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		grafica = new Grafica();
		contentPane.add(grafica, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0};
		gbl_panel.rowHeights = new int[] {0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		JLabel lblFuncion = new JLabel("f(x)");
		lblFuncion.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblFuncion = new GridBagConstraints();
		gbc_lblFuncion.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFuncion.insets = new Insets(5, 5, 5, 5);
		gbc_lblFuncion.gridx = 0;
		gbc_lblFuncion.gridy = 0;
		panel.add(lblFuncion, gbc_lblFuncion);
		
		fieldFuncion = new JTextField();
		GridBagConstraints gbc_fieldFuncion = new GridBagConstraints();
		gbc_fieldFuncion.fill = GridBagConstraints.BOTH;
		gbc_fieldFuncion.insets = new Insets(5, 5, 5, 5);
		gbc_fieldFuncion.gridx = 1;
		gbc_fieldFuncion.gridy = 0;
		panel.add(fieldFuncion, gbc_fieldFuncion);
		fieldFuncion.setColumns(12);
		
		JButton btnGraficar = new JButton("Graficar");
		GridBagConstraints gbc_btnGraficar = new GridBagConstraints();
		gbc_btnGraficar.gridwidth = 2;
		gbc_btnGraficar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGraficar.insets = new Insets(5, 5, 5, 5);
		gbc_btnGraficar.gridx = 0;
		gbc_btnGraficar.gridy = 1;
		panel.add(btnGraficar, gbc_btnGraficar);
		
		fieldFuncion.setForeground(Grafica.FONDO);
		btnGraficar.setBackground(Color.WHITE);
		btnGraficar.setFocusable(false);
		
		fieldFuncion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent k) {
				try {
					char key = k.getKeyChar();
					if(key != ' ')
						Elemento.getTipo(""+key);
				}catch(IllegalArgumentException e) {
					k.consume();
				}
			}
		});
		
		Action eventoGraficar = new Graficar();
		InputMap inputMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = contentPane.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "graficar");
		actionMap.put("graficar", eventoGraficar);
		btnGraficar.setAction(eventoGraficar);
		
	}
	
	private class Graficar extends AbstractAction{
		
		private static final long serialVersionUID = 1L;

		// constructor
		public Graficar() {
			putValue(Action.NAME, "Graficar");
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				grafica.setFuncion(fieldFuncion.getText());
			}catch(IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(Ventana.this, ex.getMessage());
				fieldFuncion.selectAll();
				fieldFuncion.requestFocus();
			}
		}
		
	}

}
