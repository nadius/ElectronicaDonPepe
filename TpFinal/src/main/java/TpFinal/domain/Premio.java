package TpFinal.domain;

import java.io.Serializable;
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
public class Premio implements Serializable {
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
	//@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	//@Fetch(value = FetchMode.SUBSELECT)
	//private List<Venta> ventas;//conviene una tabla ventas_premio?
	@ManyToOne(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	private Vendedor premiado;
	@ManyToOne(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	private Producto producto;
	@Column(nullable=false)
	private boolean isCampania;//sólo en el caso de comisión por campaña
	@Column(nullable=false)
	private float importe;
	
	public Premio() {
		this.premiado = new Vendedor();
		this.producto = new Producto();
		this.isCampania = false;
		this.importe = 0F;
	}
	
	public Premio(Date fechaCreacion, Date fechaDesde, Date fechaHasta, Vendedor premiado, boolean isCampania, Producto producto, float importe) {
		super();
		this.fechaCreacion=fechaCreacion;
		this.fechaDesde=fechaDesde;
		this.fechaHasta=fechaHasta;
		this.producto = producto;
		this.premiado=premiado;
		this.isCampania = isCampania;
		this.importe = importe;
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public boolean isCampania() {
		return isCampania;
	}
	public void setCampania(boolean isCampania) {
		this.isCampania = isCampania;
	}
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vendedor getPremiado() {
		return premiado;
	}

	public void setPremiado(Vendedor premiado) {
		this.premiado = premiado;
	}
	
	public boolean equals(Premio registro)
	{
		if (this.isCampania() != registro.isCampania())
			return false;
		
		/*if (this.getFechaCreacion().compareTo(registro.getFechaCreacion())!=0)
			return false;*/
		
		if (this.getFechaDesde().compareTo(registro.getFechaDesde())!=0)
			return false;
		
		if (this.getFechaHasta().compareTo(registro.getFechaHasta())!=0)
			return false;
		
		if (!this.getPremiado().equals(registro.getPremiado()))
			return false;
		
		if (this.getProducto()!=null && !this.getProducto().equals(registro.getProducto()))
			return false;
		
		if (this.getImporte() != registro.getImporte())
			return false;
		
		return true;
	}
}
