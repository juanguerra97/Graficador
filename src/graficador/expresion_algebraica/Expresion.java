package graficador.expresion_algebraica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Stack;

// Clase para representar una expresion
public class Expresion {
	
	private Elemento elementos[];// arreglo con todos los elementos de la expresion
	private int n;
	
	// métodos getter
	public Elemento[] getElementos() {return elementos; }
	public int getNumeroElementos() {return elementos.length;}
	
	// método para establecer los elementos
	public void setElementos(Elemento elementos[]) {
		this.elementos = elementos;
	}
	
	public void setN(int n) {
		this.n = n;
	}
	
	// devuelve el valor numérico de un elemento de la expresion, null si es un operador
	private Double getValor(int n, Double x) {
		if(elementos[n].getTipo() == Tipo.CONSTANTE)
			return Double.parseDouble(elementos[n].getElemento());
		else if(elementos[n].getTipo() == Tipo.VARIABLE)
			return x;
		else 
			return null;
	}
	
	public Double evalua(Double x) {
		if(x != null) {
			ArrayList<Double> operandos = new ArrayList<>();
			for(int i = 0; i <= n; ++i) {
				String elemento = elementos[i].getElemento();
				if(elementos[i].getTipo() == Tipo.OPERADOR) {
					Double resultado = null;
					int size = operandos.size();
					switch(elemento.charAt(0)) {
					case '+':
						resultado = operandos.get(size - 2) + operandos.get(size - 1);
						break;
					case '-':
						resultado = operandos.get(size - 2) - operandos.get(size - 1);
						break;
					case '*':
						resultado = operandos.get(size - 2) * operandos.get(size - 1);
						break;
					case '/':
						resultado = operandos.get(size - 2) / operandos.get(size - 1);
						break;
					case '^':
						resultado = Math.pow(operandos.get(size - 2), (int) Math.round(operandos.get(size - 1)));
						break;
					default:
						resultado = null;
						break;
					}
					operandos.remove(size - 1);
	;				operandos.remove(size - 2);
					operandos.add(resultado);
				}else
					operandos.add(getValor(i,x));			
			}
			return operandos.get(0);
		}		
		return null;
	}
	
	//x 3 + 3 / x +
	public Double evaluar(Double x) {
		if(x != null && elementos != null && elementos.length >= 1) {
			if(getNumeroOperandos() == (getNumeroOperadores() + 1)) {
				Double resultado;
				if(elementos[0].getElemento().equalsIgnoreCase("x"))
					resultado = x;
				else
					resultado = Double.parseDouble(elementos[0].getElemento());
				for(int i = 2; i <= n; i+=2) {
					Double otro;
					if(elementos[i-1].getElemento().equalsIgnoreCase("x"))
						otro = x;
					else
						otro = Double.parseDouble(elementos[i-1].getElemento());
					switch(elementos[i].getElemento()) {
					case "+":
						resultado+=otro;
						break;
					case "-":			
						resultado-=otro;
						break;
					case "*":
						resultado*=otro;
						break;
					case "/":
						resultado/=otro;
						break;
					case "^":
						resultado = Math.pow(resultado, Integer.parseInt(elementos[i-1].getElemento()));
						break;
					default:
						break;
					}
				}
				return resultado;
			}
		}
		return null;
	}
	
	public int getNumeroOperandos() {
		int cont = 0;
		for(int i = 0; i <= n; ++i) {
			if(elementos[i].getTipo() == Tipo.CONSTANTE || elementos[i].getTipo() == Tipo.VARIABLE)
				++cont;
		}
		return cont;
	}
	
	public int getNumeroOperadores() {
		int cont = 0;
		for(int i = 0; i <= n; ++i) {
			if(elementos[i].getTipo() == Tipo.OPERADOR)
				++cont;
		}
		return cont;
	}
	
	// método para convertir una expresión a notación postfija
	public static Expresion postFija(String expresionOriginal) throws IllegalArgumentException {
		Stack<String> pila = new Stack<>();
		Elemento expresion[];
		boolean desapila;
		int n = -1;
		
		if(!Expresion.valida(expresionOriginal))
			throw new IllegalArgumentException("Expresión inválida!");
		
		String tokens[] = expresionOriginal.trim().split("[ ]+");// divide la cadena en tokens
		
		expresion = new Elemento[tokens.length];
		for(int i = 0; i < tokens.length; ++i)
			expresion[i] = new Elemento();
		
		for(int i = 0; i < tokens.length; ++i) {
			String operadorCima;
			if(Elemento.getTipo(tokens[i]) == Tipo.CONSTANTE || Elemento.getTipo(tokens[i]) == Tipo.VARIABLE) {
				expresion[++n].setElemento(tokens[i]);
			}else if(!tokens[i].equals(")")) {
				desapila = true;
				while(desapila) {
					operadorCima = " ";
					if(!pila.isEmpty())
						operadorCima = pila.peek();
					if(pila.isEmpty() || (Operador.getPrioridadFuera(tokens[i].charAt(0)) > Operador.getPrioridadDentro(operadorCima.charAt(0)))) {
						pila.push(tokens[i]);
						desapila = false;
					}else if(Operador.getPrioridadFuera(tokens[i].charAt(0)) <= Operador.getPrioridadDentro(operadorCima.charAt(0))) {
						expresion[++n].setElemento(pila.pop());
					}
				}
				
			}else {
				operadorCima = pila.pop();
				do {
					expresion[++n].setElemento(operadorCima);
					operadorCima = pila.pop();
				}while(!operadorCima.equals("("));
			}
		}
		
		while(!pila.isEmpty()) 
			expresion[++n].setElemento(pila.pop());
		
		Expresion expresionPostFija = new Expresion();
		expresionPostFija.setElementos(expresion);
		expresionPostFija.setN(n);
		
		return expresionPostFija;
	}
	
	// método para establecer si una cadena es válida como expresión algebraica
	public static boolean valida(String cadena) {
		String tokens[] = cadena.trim().split("[ ]+");
		if(tokens != null && tokens.length >= 1) {
			for(int i = 0; i < tokens.length; ++i) {
				try {
					new BigDecimal(tokens[i]);
				}catch(NumberFormatException e1) {
					try {
						Elemento.getTipo(tokens[i]);
					}catch(IllegalArgumentException e2){
						return false;
					}
				}
			}// termina bucle for
			return true;
		}else
			return false;
	}
	
	@Override
	public String toString() {
		String expresion = "";
		for(int i = 0; i <= n; ++i)
			expresion+=elementos[i].getElemento() + " ";
		return expresion;
	}

}
