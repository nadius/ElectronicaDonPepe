package tpFinal.service.calculation;

import java.util.Date;
import java.util.GregorianCalendar;

import tpFinal.domain.Vendedor;

public interface CalculationService<E> {
	public E calcular(Vendedor author, Date today, GregorianCalendar from, GregorianCalendar to);
	public void showResult();
}
