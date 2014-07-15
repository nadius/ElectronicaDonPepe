package tpFinal.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value="P")
public class ComisionProducto extends Comision{
	@Transient
	private static final long serialVersionUID = 1L;
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
		
		if ((this.getProducto() != null && registro.getProducto() != null) &&
				!this.getProducto().equals(registro.getProducto()))
			return false;
		
		return true;
	}
}
