package TpFinal.domain;

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
		/*super();
		elementos=new List<Venta>();*/
	}

	public ComisionVenta(int unidades, double importe, List<Venta> elementos) {
		super(unidades, importe);
		this.elementos = elementos;
	}

	public List<Venta> getVentas() {
		return elementos;
	}

	public void setVentas(List<Venta> elementos) {
		this.elementos = elementos;
	}
}
