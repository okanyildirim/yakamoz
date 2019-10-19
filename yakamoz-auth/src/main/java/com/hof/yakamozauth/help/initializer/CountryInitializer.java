package com.hof.yakamozauth.help.initializer;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CountryInitializer {
	//implements InitializingBean

	private final CountryRepository countryRepository;

	public void afterPropertiesSet() throws Exception {
		if (countryRepository.count() == 0) {
			CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
			CsvMapper mapper = new CsvMapper();
			File file = new ClassPathResource("countries.csv").getFile();
			MappingIterator<Map<String, String>> it = mapper.readerFor(Map.class).with(bootstrapSchema)
					.readValues(file);
			while (it.hasNextValue()) {
				Map<String, String> row = it.nextValue();
				Country country = new Country(row.get("Code"), row.get("Country"));
				countryRepository.save(country);
			}
		}
	}

}

