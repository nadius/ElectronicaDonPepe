package tpFinal.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@DiscriminatorValue(value="V")
public class ComisionVenta extends Comision{
	@Transient
	private static final long serialVersionUID = 1L;
	@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Venta> elementos;

	public ComisionVenta() {
		super();
		elementos=new ArrayList<Venta>();
	}

	public ComisionVenta(int unidades, float importe, List<Venta> elementos) {
		super(unidades, importe);
		this.elementos = elementos;
	}

	public List<Venta> getVentas() {
		return elementos;
	}

	public void setVentas(List<Venta> elementos) {
		this.elementos = elementos;
	}
	
	public boolean equals(ComisionVenta registro)
	{
		ArrayList<Venta> thisVentas=new ArrayList<Venta>(getVentas());
		ArrayList<Venta> registroVentas=new ArrayList<Venta>(registro.getVentas());
		
		if (!super.equals(registro))
			return false;
		
		if (thisVentas.size() != registroVentas.size())//si no tienen la misma cantidad de ventas
			return false;
		
		if (!isMismasVentas(thisVentas, registroVentas))//verifico id a id que las ventas de los dos registros sean las mismas.
			return false;
		
		return true;
	}
	
	private boolean isMismasVentas(ArrayList<Venta> thisVentas, ArrayList<Venta> registroVentas){
		int cuenta=0;
		for (int i=0; i<thisVentas.size(); i++){//uso un for tradicional para evitar que busque un null en elementos
			for (int j=0; j<registroVentas.size(); j++){
				if (thisVentas.get(i).getId() == registroVentas.get(j).getId())//comparo los ids de las ventas
					cuenta++;
			}
		}
		
		if(cuenta == thisVentas.size())
			return true;

		return false;
	}
}
