package TpFinal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

//import org.hibernate.annotations.DiscriminatorOptions;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorOptions(force = true)
@DiscriminatorColumn(name="tipo", discriminatorType=DiscriminatorType.STRING)
public class Comision implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	@Column(nullable=false)
	private Date fechaCreacion;
	@Column(nullable=false)
	private Date fechaDesde;
	@Column(nullable=false)
	private Date fechaHasta;
	//@Column(nullable=false)
	@ManyToOne(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	//@Fetch(value = FetchMode.SUBSELECT)
	private Vendedor vendedor;
	@Column(nullable=false)
	private int unidades=0;
	@Column(nullable=false)
	private float importe=0;
	
	public Comision() {
		this.vendedor= new Vendedor();
	}
	
	public Comision(int unidades, float importe) {
		this.unidades= unidades;
		this.importe = importe;
	}
	
	
	
	public Comision(Date fechaCreacion, Date fechaDesde, Date fechaHasta, Vendedor vendedor, int unidades, float importe) {
		this.fechaCreacion = fechaCreacion;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.vendedor = vendedor;
		this.unidades = unidades;
		this.importe = importe;
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
	
	public int getUnidades(){
		return unidades;
	}
	
	public void setUnidades(int unidades){
		this.unidades=unidades;
	}
	
	public float getImporte() {
		return importe;
	}
	
	public void setImporte(float importe) {
		this.importe = importe;
	}
}
