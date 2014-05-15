package TpFinal.domain;

//import java.util.List;

//import javax.persistence.CascadeType;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;

@Entity
@DiscriminatorValue(value="P")
public class ComisionProducto extends Comision{
	@Transient
	private static final long serialVersionUID = 1L;
	//@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	//@Fetch(value = FetchMode.SUBSELECT)
	@ManyToOne
	private Producto producto;

	public ComisionProducto() {
		super();
		this.producto=new Producto();
	}

	public ComisionProducto(int unidades, float importe, Producto producto) {
		super(unidades, importe);
		this.producto = producto;
	}	

	public ComisionProducto(Date fechaCreacion, Date fechaDesde, Date fechaHasta, Vendedor vendedor, int unidades, float importe, Producto producto) {
		super(fechaCreacion, fechaDesde, fechaHasta, vendedor, unidades, importe);
		this.producto = producto;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public boolean equals(ComisionProducto registro)
	{
		if (!super.equals(registro))
			return false;
		
		if (!this.getProducto().equals(registro.getProducto()))
			return false;
		
		return true;
	}
}
