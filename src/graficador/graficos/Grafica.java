package graficador.graficos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

// JPanel donde se dibuja la gráfica
public class Grafica extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static final Color FONDO = new Color(33.0f/255,33.0f/255,33.0f/255);
	public static final Color EJES = new Color(96.0f/255,125.0f/255,139.0f/255);
	public static final Color LINEAS = new Color(96.0f/255,125.0f/255,139.0f/255, 0.05f);
	
	private int escala;
	
	// constructor
	public Grafica() {
		setBackground(FONDO);
		escala = 20;
		
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
		
	}

}
