package graficador.graficos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import graficador.expresion_algebraica.Expresion;

// JPanel donde se dibuja la gráfica
public class Grafica extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static final Color FONDO = new Color(33.0f/255,33.0f/255,33.0f/255);
	public static final Color EJES = new Color(96.0f/255,125.0f/255,139.0f/255);
	public static final Color LINEAS = new Color(96.0f/255,125.0f/255,139.0f/255, 0.05f);
	public static final Color GRAFICA = new Color(255.0f/255,87.0f/255,34.0f/255);
	
	private int escala;
	private String funcion;
	private Expresion funcionPostFija;
	
	// constructor
	public Grafica() {
		setBackground(FONDO);
		escala = 20;
		funcion = null;
		funcionPostFija = null;
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent mwl) {
				if(mwl.getWheelRotation() > 0) {
					if(escala - 5 >= 5) {
						escala-=5;
						updateUI();
					}
				}else {
					if(escala + 5 <= 100) {
						escala+=5;
						updateUI();
					}
				}
			}
		});
	}
	
	// método para establecer la expresion de la funcion a graficar
	public void setFuncion(String funcion) throws IllegalArgumentException {
		boolean valido = Expresion.valida(funcion);
		if(valido) {
			this.funcion = funcion;
			funcionPostFija = Expresion.postFija(funcion);
			updateUI();
		}else {
			this.funcion = null;
			this.funcionPostFija = null;
			updateUI();
			throw new IllegalArgumentException("Función inválida");
		}
	}
	
	// método para establecer la escala
	public void setEscala(int escala) {
		if(escala >= 5 && escala <= 100)
			this.escala = escala;
	}
	
	// método que dibuja en el panel
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int ancho = getWidth(), alto = getHeight();// ancho y alto(en pixeles)
		g2.setColor(EJES);
		g2.translate(ancho / 2, alto / 2);// traslada el eje central de coordenadas el centro del panel
		g2.drawLine(0, -(alto / 2), 0, (alto/2));// dibuja la linea del eje Y
		g2.drawLine(-(ancho / 2), 0, (ancho / 2), 0);// dibuja la linea del eje X
		Line2D.Float linea = new Line2D.Float();	
		
		// bucle para dibujar las lineas verticales
		for(int i = escala; i <= ancho / 2; i+=escala) {
			
			g2.setColor(LINEAS);
			
			linea.setLine(i, -(alto / 2), i	, (alto / 2));	
			g2.draw(linea);
			
			linea.setLine(-i, -(alto / 2), -i	, (alto / 2));
			g2.draw(linea);

			g2.setColor(EJES);
			
			linea.setLine(i, -1.0f, i, 1.0f);
			g2.draw(linea);
			
			linea.setLine(-i, -1.0f, -i, 1.0f);
			g2.draw(linea);
			
		}
		
		// bucle para dibujar las lineas horizontales
		for(int i = escala; i <= (alto / 2); i+=escala) {
			
			g2.setColor(LINEAS);
			
			linea.setLine(-(ancho / 2), i, (ancho / 2), i);
			g2.draw(linea);
			
			linea.setLine(-(ancho / 2), -i, (ancho / 2), -i);
			g2.draw(linea);
			
			g2.setColor(EJES);
			
			linea.x1 = -1.0f;
			linea.x2 = 1.0f;
			g2.draw(linea);
			
			linea.y1 = i;
			linea.y2 = i;
			g2.draw(linea);
			
		}	
		
		graficar(g2);
		
	}
	
	// método que dibuja la gráfica de una función
	public void graficar(Graphics2D g) {
		if(funcion != null && funcionPostFija != null) {
			int mitadAncho = getWidth() / 2;
			int mitadAlto = getHeight() / 2;
			Line2D.Double linea = new Line2D.Double();
			double x2, y2;
			g.setColor(GRAFICA);
			for(double X = -(mitadAlto / escala) - 0.01; X <= mitadAncho; X+=0.01) {
				try {
					double Y = funcionPostFija.evalua(X);
					x2 = X+0.01;
					y2 = funcionPostFija.evalua(x2);
					if((Y <= (mitadAlto / escala + 100) && Y >= -(mitadAlto / escala)) && (y2 <=  (mitadAlto / escala + 100) && y2 >= -(mitadAlto / escala + 10))) {
						linea.x1 = X * escala;
						linea.y1 = -(Y * escala);
						linea.x2 = x2 * escala;
						linea.y2 = -(y2 * escala);
						g.draw(linea);
					}
				}catch(Exception e) {}
			}
			
		}
	}

}
