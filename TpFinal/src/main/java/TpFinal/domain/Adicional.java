package TpFinal.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import TpFinal.domain.ComisionVenta;
import TpFinal.domain.Vendedor;
import TpFinal.domain.ComisionProducto;
import TpFinal.domain.Premio;

@Entity
//no estoy segura si esto debería verse reflejado en la base de datos
public class Adicional implements Serializable {
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
	@ManyToOne
	private Vendedor vendedor;
	@OneToOne(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	@JoinColumn(name="comisionVenta_id")
	//@PrimaryKeyJoinColumn
	private ComisionVenta comisionVentas;
	@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ComisionProducto> comisionesProducto;
	@OneToOne//(cascade=CascadeType.ALL)
	@JoinColumn(name="premioVendedor_id")
	private Premio mejorVendedorMes;
	@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Premio> campanias;
	@Transient
	private float totalComisionProducto;
	@Transient
	private float totalPremiosCampania;
	@Column(name="total")
	private float totalAdicionales;
	@OneToOne//(cascade=CascadeType.ALL)
	@JoinColumn(name="premioCampania_id")
	private Premio campania;
	
	public Adicional() {
		this.vendedor=new Vendedor();
		this.comisionVentas=new ComisionVenta();
		this.comisionesProducto = new ArrayList<ComisionProducto>();
		this.mejorVendedorMes=new Premio();
		this.campanias=new ArrayList<Premio>();
	}
	
	public Adicional(Date fechaCreacion, Date fechaDesde, Date fechaHasta)
	{
		this.fechaCreacion = fechaCreacion;
		this.fechaDesde=fechaDesde;
		this.fechaHasta=fechaHasta;
		
		this.vendedor=new Vendedor();
		this.comisionVentas=new ComisionVenta();
		this.comisionesProducto = new ArrayList<ComisionProducto>();
		this.mejorVendedorMes=new Premio();
		this.campanias=new ArrayList<Premio>();
	}
	
	public Adicional(Date fechaCreacion, Date fechaDesde, Date fechaHasta, Vendedor vendedor, ComisionVenta ventas, List<ComisionProducto> producto, Premio mejorVendedorMes, List<Premio> campanias) {
		this.fechaCreacion = fechaCreacion;
		this.fechaDesde=fechaDesde;
		this.fechaHasta=fechaHasta;
		this.vendedor = vendedor;
		this.comisionVentas = ventas;
		this.comisionesProducto = producto;
		this.mejorVendedorMes = mejorVendedorMes;
		this.campanias = campanias;
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

	public ComisionVenta getComisionVentas() {
		return comisionVentas;
	}

	public void setComisionVentas(ComisionVenta comisionVentas) {
		this.comisionVentas = comisionVentas;
	}

	public List<ComisionProducto> getComisionesProducto() {
		return comisionesProducto;
	}

	public void setComisionesProducto(List<ComisionProducto> comisionesProducto) {
		this.comisionesProducto = comisionesProducto;
	}

	public Premio getMejorVendedorMes() {
		return mejorVendedorMes;
	}

	public void setMejorVendedorMes(Premio mejorVendedorMes) {
		this.mejorVendedorMes = mejorVendedorMes;
	}

	public List<Premio> getCampanias() {
		return campanias;
	}

	public void setCampanias(List<Premio> campanias) {
		this.campanias = campanias;
	}

	public float getTotalComisionProducto() {
		return totalComisionProducto;
	}

	public void setTotalComisionProducto(float totalComisionProducto) {
		this.totalComisionProducto = totalComisionProducto;
	}

	public float getTotalPremiosCampania() {
		return totalPremiosCampania;
	}

	public void setTotalPremiosCampania(float totalPremiosCampania) {
		this.totalPremiosCampania = totalPremiosCampania;
	}

	public float getTotalAdicionales() {
		return totalAdicionales;
	}

	public void setTotalAdicionales(float total) {
		this.totalAdicionales = total;
	}
}
