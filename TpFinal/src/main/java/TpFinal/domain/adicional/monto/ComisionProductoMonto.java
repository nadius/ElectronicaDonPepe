package TpFinal.domain.adicional.monto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import TpFinal.domain.Producto;

@Entity(name="Comision_Producto_Monto")
public class ComisionProductoMonto implements Serializable{
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	private Producto producto;
	@Column(nullable=false)
	private double monto;
	
	public ComisionProductoMonto()
	{
		
	}

	public ComisionProductoMonto(Producto producto, double monto) {
		this.producto = producto;
		this.monto = monto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}
}
