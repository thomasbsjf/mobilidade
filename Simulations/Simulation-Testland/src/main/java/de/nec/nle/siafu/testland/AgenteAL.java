package de.nec.nle.siafu.testland;
import de.nec.nle.siafu.model.Agent;
import de.nec.nle.siafu.model.Position;
import de.nec.nle.siafu.model.World;
import org.apache.commons.lang.math.RandomUtils;

/**
 * Representa um Agente no ambiente de AL
 */
public class AgenteAL extends Agent {
	private double TEMPERATURA_MIN = 35;
	private double TEMPERATURA_MAX = 39;
	private double TEMPERATURA_VAR= 0.2;
	private double TEMPERATURA;
	
	private int PRESSAO_DIASTOLICA_MIN = 80;
	private int PRESSAO_DIASTOLICA_MAX = 110;
	private double PRESSAO_DIASTOLICA_VAR = 20;
	private int PRESSAO_DIASTOLICA;
	private int PRESSAO_SISTOLICA_MIN = 110;
	private int PRESSAO_SISTOLICA_MAX = 180;
	private double PRESSAO_SISTOLICA_VAR = 20;
	private int PRESSAO_SISTOLICA;
	private int PULSO_MIN = 60;
	private int PULSO_MAX = 160;
	private double PULSO_VAR = 30;
	private int PULSO;
	
	public AgenteAL(final Position start, final String image, final World world) {
		super(start, image, world);
	}
	public AgenteAL(final String name, final Position start, final String image, final
			World world) {
		super(name, start, image, world);
	}
	public AgenteAL(final String name, final Position start, final String image, final
			World world, final int zPriority) {
		super(name, start, image, world, zPriority);
	}
	public void initialize() {
		TEMPERATURA = (RandomUtils.nextDouble() * (TEMPERATURA_MAX -
				TEMPERATURA_MIN)) + TEMPERATURA_MIN;
		PRESSAO_DIASTOLICA = (int) ((RandomUtils.nextDouble() *
				(PRESSAO_DIASTOLICA_MAX - PRESSAO_DIASTOLICA_MIN)) +
				PRESSAO_DIASTOLICA_MIN);
		PRESSAO_SISTOLICA = (int) ((RandomUtils.nextDouble() *
				(PRESSAO_SISTOLICA_MAX - PRESSAO_SISTOLICA_MIN)) +
				PRESSAO_SISTOLICA_MIN);
		PULSO = (int) ((RandomUtils.nextDouble() * (PULSO_MAX - PULSO_MIN)) +
				PULSO_MIN);
		// Sensor, U MIN, U MAX, Probabilidade de U, PRECISAO MIN, PRECISAO MAX
		Sensor sensor_temp = new
		Sensor(Constants.Fields.TEMPERATURA,1,10,0.3,0.7,1);
		Sensor sensor_pressao_diastolica = new
				Sensor(Constants.Fields.PRESSAO_BAIXA,10,120,0.3,0.7,1);
		Sensor sensor_pressao_sistolica = new
				Sensor(Constants.Fields.PRESSAO_ALTA,10,120,0.3,0.7,1);
		Sensor sensor_pulso = new Sensor(Constants.Fields.PULSO,1,120,0.3,0.5,1);
		this.set(Constants.Fields.TEMPERATURA,sensor_temp);
		this.set(Constants.Fields.PRESSAO_BAIXA,sensor_pressao_diastolica);
		this.set(Constants.Fields.PRESSAO_ALTA,sensor_pressao_sistolica);
		this.set(Constants.Fields.PULSO,sensor_pulso);
	}
	
	public void tick() {
		// Usar a variação
		TEMPERATURA =
				processVariable(TEMPERATURA,TEMPERATURA_MIN,TEMPERATURA_MAX,TEMPERATURA_VAR);
		PRESSAO_DIASTOLICA = (int)
				processVariable(PRESSAO_DIASTOLICA,PRESSAO_DIASTOLICA_MIN,PRESSAO_DIASTOLICA_MAX,PRESSAO_DIASTOLICA_VAR);
		PRESSAO_SISTOLICA = (int)
				processVariable(PRESSAO_SISTOLICA,PRESSAO_SISTOLICA_MIN,PRESSAO_SISTOLICA_MAX,PRESSAO_SISTOLICA_VAR);
		PULSO = (int) 
				processVariable(PULSO,PULSO_MIN,PULSO_MAX,PULSO_VAR);
		
		((Sensor) this.get(Constants.Fields.TEMPERATURA)).tick(TEMPERATURA);
		((Sensor) this.get(Constants.Fields.PRESSAO_BAIXA)).tick((double)PRESSAO_DIASTOLICA);
		((Sensor) this.get(Constants.Fields.PRESSAO_ALTA)).tick((double)PRESSAO_SISTOLICA);
		((Sensor) this.get(Constants.Fields.PULSO)).tick((double)PULSO);
	
	}
	private double processVariable (double actual, double min, double max, double
			variance) {
		double normalVar = ((RandomUtils.nextDouble() * 2.0) - 1.0) * variance;
		double novo = actual + normalVar;
		if (novo < min)
			novo = min;
		else if (novo > max)
			novo = max;
		return novo;
	}
	public double getTEMPERATURA() {
		return TEMPERATURA;
	}
	public void setTEMPERATURA(double TEMPERATURA) {
		this.TEMPERATURA = TEMPERATURA;
	}
	public double getPRESSAO_DIASTOLICA() {
		return PRESSAO_DIASTOLICA;
	}
	public void setPRESSAO_DIASTOLICA(int PRESSAO_DIASTOLICA) {
		this.PRESSAO_DIASTOLICA = PRESSAO_DIASTOLICA;
	}
	public double getPRESSAO_SISTOLICA() {
		return PRESSAO_SISTOLICA;
	}
	public void setPRESSAO_SISTOLICA(int PRESSAO_SISTOLICA) {
		this.PRESSAO_SISTOLICA = PRESSAO_SISTOLICA;
	}
	public double getPULSO() {
		return PULSO;
	}
	public void setPULSO(int PULSO) {
		this.PULSO = PULSO;
	}
}