package com.xavier.trans.service;

import com.github.jsonzou.jmockdata.JMockData;
import com.xavier.trans.TransApplicationTests;
import com.xavier.trans.model.PetitionHighLevelReduce;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

public class PetitionHighLevelReduceServiceTest extends TransApplicationTests {

	@Autowired
	private PetitionHighLevelReduceService petitionHighLevelReduceService;

	@Test
	public void serviceFindByIdTest() {
		Optional<PetitionHighLevelReduce> petitionHighLevelReduceOptional = petitionHighLevelReduceService.get("7dd62acaea49433ba42d925ad0d2d907");
		if (petitionHighLevelReduceOptional.isPresent()) {
			System.out.println(petitionHighLevelReduceOptional.get());
		} else {
			System.out.println("GG");
		}
	}

	@Test
	public void serviceSaveTestByReflect() throws ClassNotFoundException, IllegalAccessException {
		PetitionHighLevelReduce petitionHighLevelReduce = new PetitionHighLevelReduce();
		Class<?> clazz = Class.forName(PetitionHighLevelReduce.class.getName());
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			field.set(petitionHighLevelReduce, JMockData.mock(field.getType()));
		}
		petitionHighLevelReduce.setId(UUID.randomUUID().toString());
		//System.out.println(petitionHighLevelReduceService.save(petitionHighLevelReduce));
		System.out.println(petitionHighLevelReduce);
	}
}
