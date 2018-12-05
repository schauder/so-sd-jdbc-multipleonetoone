package de.schauderhaft.multipleonetoone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static java.util.Arrays.*;
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

		trucks.save(createTruck("Trucky McTruckload", "Truck Engine"));

		Car car = createCar("Racer Racy", "Strong engine");
		cars.saveAll(asList(
				car,
				createCar("Snail", "Weak engine"))
		);

		assertThat(count(Truck.class)).isEqualTo(1);
		assertThat(count(Car.class)).isEqualTo(2);
		assertThat(count(Engine.class)).isEqualTo(3);

		cars.delete(car);

		assertThat(count(Truck.class)).isEqualTo(1);
		assertThat(count(Car.class)).isEqualTo(1);
		assertThat(count(Engine.class)).isEqualTo(2);

		trucks.deleteAll();

		assertThat(count(Truck.class)).isEqualTo(0);
		assertThat(count(Car.class)).isEqualTo(1);
		assertThat(count(Engine.class)).isEqualTo(1);
	}

	private Truck createTruck(String truckName, String engineName) {

		Truck truck = new Truck();
		truck.name = truckName;
		truck.engine = new Engine();
		truck.engine.name = engineName;

		return truck;
	}

	private Car createCar(String carName, String engineName) {

		Car car = new Car();
		car.name = carName;
		car.engine = new Engine();
		car.engine.name = engineName;

		return car;
	}

	private Long count(Class<?> type) {

		return template.queryForObject( //
				"select count(*) from " + type.getSimpleName(), //
				new HashMap<>(), //
				Long.class //
		);
	}

}
