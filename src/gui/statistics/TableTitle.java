package gui.statistics;

public enum TableTitle {
PUNTUACION, FECHA, TIEMPO, NIVEL;

	@Override
	public String toString() {
		switch (this) {
		case PUNTUACION:
			return "Puntuación";
		case FECHA:
			return "Fecha";
		case TIEMPO:
			return "Tiempo";
		case NIVEL:
			return "Nivel";
		default:
			return "";
		}
	}
}
