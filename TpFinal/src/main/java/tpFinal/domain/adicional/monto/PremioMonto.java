package tpFinal.domain.adicional.monto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name="Premio_Monto")
public class PremioMonto implements Serializable{
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable=false)
	private boolean campania;
	@Column(nullable=false)
	private float monto;
	
	public PremioMonto()
	{
		
	}
	
	public PremioMonto(boolean campania, float monto) {
		super();
		this.campania = campania;
		this.monto = monto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isCampania() {
		return campania;
	}

	public void setCampania(boolean campania) {
		this.campania = campania;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}
}
