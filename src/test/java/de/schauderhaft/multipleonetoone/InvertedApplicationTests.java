package de.schauderhaft.multipleonetoone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvertedApplicationTests {

	@Autowired
	CarRepository cars;
	@Autowired
	TruckRepository trucks;

	@Autowired
	NamedParameterJdbcOperations template;

	@Test
	public void playWithVehicles() {

		Truck truck = createTruck();

		trucks.save(truck);

		assertThat(count(Truck.class)).isEqualTo(1);
		assertThat(count(Engine.class)).isEqualTo(1);
	}

	private Truck createTruck() {

		Truck truck = new Truck();
		truck.name = "Trucky McTruckload";
		truck.engine = new Engine();
		truck.engine.name = "Truck Engine";

		return truck;
	}

	private Long count(Class<?> type) {

		return template.queryForObject( //
				"select count(*) from " + type.getSimpleName(), //
				new HashMap<>(), //
				Long.class //
		);
	}

}
