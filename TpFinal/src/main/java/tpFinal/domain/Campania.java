package tpFinal.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Campania {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable=false)
	private Date fechaCreacion;
	@ManyToOne(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	private Producto producto;
	@Column(nullable=false)
	boolean activo;
	
	public Campania()
	{
		fechaCreacion=new Date();
		activo=true;
	}
	
	public Campania(Producto producto)
	{
		fechaCreacion=new Date();
		activo=true;
		this.producto=producto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public boolean equals(Campania campania)
	{
		if (this.getFechaCreacion().compareTo(campania.getFechaCreacion())!=0)
			return false;
		
		if (!this.getProducto().equals(campania.getProducto()))
			return false;
		
		return true;
	}
}
