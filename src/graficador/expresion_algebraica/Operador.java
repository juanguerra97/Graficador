package graficador.expresion_algebraica;

public final class Operador {
	
	// método que devuelve la prioridad de los operadores fuera de la expresion
	public static int getPrioridadFuera(char operador) {
		int prioridadFuera = -1;
		switch(operador) {
		case '^':
			prioridadFuera = 4;
			break;
		case '*':
		case '/':
			prioridadFuera = 2;
			break;
		case '+':
		case '-':
			prioridadFuera = 1;
			break;
		case '(':
			prioridadFuera = 5;
			break;
		}
		return prioridadFuera;
	}
	
	// método que devuelve la prioridad de un operador dentro de la expresión	
	public static int getPrioridadDentro(char operador) {
		int prioridadDentro = -1;
		switch(operador) {
		case '^': 
			prioridadDentro = 3;
			break;
		case '*':
		case '/':
			prioridadDentro = 2;
			break;
		case '+':
		case '-':
			prioridadDentro = 1;
			break;
		case '(':
			prioridadDentro = 0;
			break;
		}
		return prioridadDentro;
	}

}
