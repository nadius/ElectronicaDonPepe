package TpFinal.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Venta implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	@Column(nullable=false)
	private Date fecha=null;
	@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Producto> productos=null;
	@Column(nullable=true)
	private double importe=0;
	@OneToOne//(cascade=CascadeType.ALL, fetch= FetchType.LAZY)
	private Vendedor vendedor=null;
	
	public Venta() {/*
		this.fecha=new Date();
		this.productos = null;
		this.importe = 0;
		this.vendedor = null;*/
	}
	
	public Venta(List<Producto> productos, double importe, Vendedor vendedor) {
		this.productos = productos;
		this.importe = importe;
		this.vendedor = vendedor;
	}
	
	public Venta(Date fecha, List<Producto> productos, double importe, Vendedor vendedor) {
		this.fecha = fecha;
		this.productos = productos;
		this.importe = importe;
		this.vendedor = vendedor;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
}
