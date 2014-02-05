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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
	@OneToOne//(cascade=CascadeType.ALL)
	@JoinColumn(name="id")
	private ComisionVenta comisionVentas;
	@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ComisionProducto> comisionesProducto;
	@OneToOne//(cascade=CascadeType.ALL)
	@JoinColumn(name="id")
	private Premio mejorVendedorMes;
	@OneToOne//(cascade=CascadeType.ALL)
	@JoinColumn(name="id")
	private Premio campania;
	
	public Adicional() {
		/*this.fechaCreacion=null;
		this.vendedor=null;
		this.Ventas=new ComisionVenta();
		this.Producto = new ComisionProducto();
		this.mejorVendedorMes=new Premio();
		this.campania=new Premio();
		
		Ventas.setFechaCreacion(fechaCreacion);
		Ventas.setVendedor(vendedor);
		Producto.setFechaCreacion(fechaCreacion);
		Producto.setVendedor(vendedor);
		mejorVendedorMes.setFechaCreacion(fechaCreacion);
		campania.setFechaCreacion(fechaCreacion);*/
		//this.campania.setCampania(true);//probablemente esto deba ser seteado más tarde.
	}
	
	public Adicional(Date fechaCreacion, Date fechaDesde, Date fechaHasta, Vendedor vendedor, ComisionVenta ventas, List<ComisionProducto> producto, Premio mejorVendedorMes, Premio campania) {
		//comisionVentas.setFechaCreacion(fechaCreacion);
		//comisionVentas.setVendedor(vendedor);
		//Producto.setFechaCreacion(fechaCreacion);
		//Producto.setVendedor(vendedor);
		//mejorVendedorMes.setFechaCreacion(fechaCreacion);
		//campania.setFechaCreacion(fechaCreacion);
		//this.campania.setCampania(true);
		
		this.fechaCreacion = fechaCreacion;
		this.fechaDesde=fechaDesde;
		this.fechaHasta=fechaHasta;
		this.vendedor = vendedor;
		this.comisionVentas = ventas;
		this.comisionesProducto = producto;
		this.mejorVendedorMes = mejorVendedorMes;
		this.campania = campania;
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

	public Premio getCampania() {
		return campania;
	}

	public void setCampania(Premio campania) {
		this.campania = campania;
	}
}