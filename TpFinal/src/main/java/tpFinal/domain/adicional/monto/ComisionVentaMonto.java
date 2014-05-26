package tpFinal.domain.adicional.monto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name="Comision_Venta_Monto")
public class ComisionVentaMonto implements Serializable{
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable=false)
	private int min;
	@Column(nullable=true)
	private int max;
	@Column(nullable=false)
	private float monto;
	
	public ComisionVentaMonto()
	{
		
	}

	public ComisionVentaMonto(int min, int max, float monto) {
		super();
		this.min = min;
		this.max = max;
		this.monto = monto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}
}
