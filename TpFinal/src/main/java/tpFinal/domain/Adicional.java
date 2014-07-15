package tpFinal.domain;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import tpFinal.domain.ComisionProducto;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.Premio;
import tpFinal.domain.Vendedor;

@Entity
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
	private ComisionVenta comisionVentas;
	@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ComisionProducto> comisionesProducto;
	@OneToOne(cascade=CascadeType.ALL)
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
	
	public boolean equals(Adicional registro)
	{
		ArrayList<ComisionProducto> thisComision = null;
		ArrayList<ComisionProducto> registroComision = null;
		ArrayList<Premio> thisCampanias = null;
		ArrayList<Premio> registroCampanias = null;
		
		if (registro == null)
			return false;
		
		if (this.comisionesProducto != null && registro.getComisionesProducto() !=null){
			thisComision = new ArrayList<ComisionProducto>(this.getComisionesProducto());
			registroComision = new ArrayList<ComisionProducto>(registro.getComisionesProducto());
		}
		
		if (this.getCampanias() != null && registro.getCampanias() !=null){
			thisCampanias = new ArrayList<Premio>(this.getCampanias());
			registroCampanias = new ArrayList<Premio>(registro.getCampanias());
		}
		
/*		if (this.getFechaCreacion().compareTo(registro.getFechaCreacion())!=0)
			return false;*/
		
		if (this.getFechaDesde().compareTo(registro.getFechaDesde())!=0)
			return false;
		
		if (this.getFechaHasta().compareTo(registro.getFechaHasta())!=0)
			return false;
		
		if (!this.getVendedor().equals(registro.getVendedor()))
			return false;
		
		if ((this.comisionVentas != null && registro.getComisionVentas() != null) &&
				!this.getComisionVentas().equals(registro.getComisionVentas()))
			return false;
		
		if ((this.comisionesProducto != null && registro.getComisionesProducto() !=null) &&
			(!this.comisionesProducto.isEmpty() && !registro.getComisionesProducto().isEmpty()) &&
				!isMismasComisionesProducto(thisComision, registroComision))
			return false;
		
		if ((this.mejorVendedorMes != null && registro.getMejorVendedorMes() != null) &&
				!this.getMejorVendedorMes().equals(registro.getMejorVendedorMes()))
			return false;
		
		if ((this.getCampanias() != null && registro.getCampanias() !=null) &&
				(!this.getCampanias().isEmpty() && !registro.getCampanias().isEmpty()) &&
				!isMismosPremiosCampania(thisCampanias, registroCampanias))
			return false;
		
		if (this.getTotalAdicionales() != registro.getTotalAdicionales())
			return false;
		
		return true;
	}
	
	private boolean isMismasComisionesProducto(ArrayList<ComisionProducto> thisComision, ArrayList<ComisionProducto> registroComision){
		int cuenta=0;
		for (int i=0; i<thisComision.size(); i++){//uso un for tradicional para evitar que busque un null en elementos
			for (int j=0; j<registroComision.size(); j++){
				if (thisComision.get(i).getId() == registroComision.get(j).getId())//comparo los ids de las ventas
					cuenta++;
			}
		}
		
		if(cuenta == thisComision.size())
			return true;

		return false;
	}
	
	private boolean isMismosPremiosCampania(ArrayList<Premio> thisCampanias, ArrayList<Premio> registroCampanias){
		int cuenta=0;
		for (int i=0; i<thisCampanias.size(); i++){//uso un for tradicional para evitar que busque un null en elementos
			for (int j=0; j<registroCampanias.size(); j++){
				if (thisCampanias.get(i).getId() == registroCampanias.get(j).getId())//comparo los ids de las ventas
					cuenta++;
			}
		}
		
		if(cuenta == thisCampanias.size())
			return true;

		return false;
	}
}