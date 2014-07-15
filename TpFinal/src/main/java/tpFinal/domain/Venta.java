package tpFinal.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	//@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	@Column(nullable=false)
	private Date fecha=null;
	@ManyToMany(fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Producto> productos=null;
	@Column(nullable=false)
	private float importe=0;
	@OneToOne//(cascade=CascadeType.ALL, fetch= FetchType.LAZY)
	private Vendedor vendedor=null;
	
	public Venta() {
		//nada para hacer
	}
	
	public Venta(List<Producto> productos, float importe, Vendedor vendedor) {
		this.productos = productos;
		this.importe = importe;
		this.vendedor = vendedor;
	}
	
	public Venta(Date fecha, List<Producto> productos, float importe, Vendedor vendedor) {
		this.fecha = fecha;
		this.productos = productos;
		this.importe = importe;
		this.vendedor = vendedor;
	}

	public Venta(int id, Date fecha, List<Producto> productos, float importe, Vendedor vendedor) {
		this.id = id;
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
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	
	public boolean equals(Venta registro)
	{
		ArrayList<Producto> thisProductos=new ArrayList<Producto>(this.getProductos());
		ArrayList<Producto> registroProductos=new ArrayList<Producto>(registro.getProductos());
		
		if (this.getFecha().compareTo(registro.getFecha())!=0)
			return false;
		
		if (!this.getVendedor().equals(registro.getVendedor()))
			return false;
		
		if (this.getImporte()!= registro.getImporte())
			return false;
		
		if (!isMismosProductos(thisProductos, registroProductos))
			return false;
		
		return true;
	}
	
	private boolean isMismosProductos(ArrayList<Producto> thisProductos, ArrayList<Producto> registroProductos){
		int cuenta=0;
		for (int i=0; i<thisProductos.size(); i++){//uso un for tradicional para evitar que busque un null en elementos
			for (int j=0; j<registroProductos.size(); j++){
				if (thisProductos.get(i).getId() == registroProductos.get(j).getId())//comparo los ids de las ventas
					cuenta++;
			}
		}
		
		if(cuenta == thisProductos.size())
			return true;

		return false;
	}
}
