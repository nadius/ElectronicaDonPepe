package TpFinal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Payroll implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable=false)
	private Date fechaCreacion;
	@Column(nullable=false)
	private Date fechaDesde;
	@Column(nullable=false)
	private Date fechaHasta;
	@OneToOne//(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	@JoinColumn(name="id")
	private Vendedor vendedor;
	@Column(nullable=false)
	private double fijo;
	//@Column(nullable=false)
	@OneToOne//(cascade=CascadeType.ALL, fetch= FetchType.LAZY)
	@JoinColumn(name="id")
	private Adicional adicionales;
	@Column(nullable=false)
	private double total;
	
	public Payroll() {/*
		this.fechaCreacion=null;
		this.vendedor = null;
		this.fijo = 0;
		this.adicionales = null;
		this.total = 0;*/
	}
	
	public Payroll(Date fechaCreacion, Date fechaDesde, Date fechaHasta, Vendedor vendedor, double fijo, Adicional adicionales, double total) {
		this.fechaCreacion=fechaCreacion;
		this.fechaDesde=fechaDesde;
		this.fechaHasta=fechaHasta;
		this.vendedor = vendedor;
		this.fijo = fijo;
		this.adicionales = adicionales;
		this.total = total;
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

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	public double getFijo() {
		return fijo;
	}
	public void setFijo(double fijo) {
		this.fijo = fijo;
	}
	public Adicional getAdicionales() {
		return adicionales;
	}
	public void setAdicionales(Adicional adicionales) {
		this.adicionales = adicionales;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
