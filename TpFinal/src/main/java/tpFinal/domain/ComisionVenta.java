package tpFinal.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@DiscriminatorValue(value="V")
public class ComisionVenta extends Comision{
	@Transient
	private static final long serialVersionUID = 1L;
	@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Venta> elementos;

	public ComisionVenta() {
		super();
		elementos=new ArrayList<Venta>();
	}

	public ComisionVenta(int unidades, float importe, List<Venta> elementos) {
		super(unidades, importe);
		this.elementos = elementos;
	}

	public List<Venta> getVentas() {
		return elementos;
	}

	public void setVentas(List<Venta> elementos) {
		this.elementos = elementos;
	}
	
	public boolean equals(ComisionVenta registro)
	{
		if (!super.equals(registro))//FIXIT: funciona?
			return false;
		
		if (!this.getVentas().containsAll(registro.getVentas()))
			return false;
		
		return true;
	}
}
