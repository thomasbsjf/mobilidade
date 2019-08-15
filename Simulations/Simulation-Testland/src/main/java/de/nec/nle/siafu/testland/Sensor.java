package de.nec.nle.siafu.testland;
import de.nec.nle.siafu.types.FlatData;
import de.nec.nle.siafu.types.Publishable;
import org.apache.commons.lang.math.RandomUtils;
import java.text.DecimalFormat;

/**
 * Representa um Sensor com variaveis usadas pelo QoC
 */

public class Sensor implements Publishable {
	private String nome;
	private int UPDATE_MIN = 1;
	private int UPDATE_MAX = 120;
	private double UPDATE_PROB = 0.5;
	private double PRECISION_MIN;
	private double PRECISION_MAX;
	private double VALOR_ATUAL;
	private double LAST_VALOR_REAL;
	private double LAST_PRECISION;
	private int lastUpdate;
	
	public Sensor(String nome, int update_min, int update_max, double update_prob,
			double precision_min, double precision_max) {
		this.nome = nome;
		this.UPDATE_MIN = update_min;
		this.UPDATE_MAX = update_max;
		this.UPDATE_PROB = update_prob;
		PRECISION_MIN = precision_min;
		PRECISION_MAX = precision_max;
	}
	public FlatData flatten() {
		return new FlatData(this.toString());
	}
	public void tick(Double valorReal) {
		LAST_VALOR_REAL = valorReal;
		if (lastUpdate >= UPDATE_MIN) {
			if (lastUpdate > UPDATE_MAX || RandomUtils.nextDouble() <=
					UPDATE_PROB) {
				// atualizar valor
				double precision = ( (PRECISION_MAX - PRECISION_MIN) *
						RandomUtils.nextDouble()) + PRECISION_MIN;
				precision = (precision *2.0) - 1.0;
				LAST_PRECISION = Math.abs(precision);
				VALOR_ATUAL = valorReal * precision;
				lastUpdate = -1;
			}
		}
		lastUpdate++;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(nome +" - R: ");
		DecimalFormat df = new DecimalFormat("###.##");
		builder.append(df.format(LAST_VALOR_REAL));
		builder.append(" L: ");
		builder.append(df.format(VALOR_ATUAL));
		builder.append("com  P: ");
		builder.append(df.format(LAST_PRECISION));
		builder.append(" U: ");
		builder.append(lastUpdate);
		return builder.toString();
	}
}