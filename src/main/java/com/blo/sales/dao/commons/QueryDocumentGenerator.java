package com.blo.sales.dao.commons;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public final class QueryDocumentGenerator {
	
	private QueryDocumentGenerator() {
		
	}
	
	/**
	 * Genera filtros de fechas con inicio y fin
	 * @param inicioMes
	 * @param inicioAnio
	 * @param finMes
	 * @param finAnio
	 * @return
	 */
	public static List<Document> buildMatchConditions(
	        Integer inicioMes, Integer inicioAnio,
	        Integer finMes, Integer finAnio) {

	    List<Document> conditions = new ArrayList<>();

	    if (inicioMes != null && inicioAnio != null && finMes != null && finAnio != null) {
	        // Caso 2: fecha inicio y fin
	        long startEpoch = LocalDate.of(inicioAnio, inicioMes, 1)
	                .atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
	        long endEpoch = LocalDate.of(finAnio, finMes, 1)
	                .plusMonths(1).minusDays(1)
	                .atTime(23, 59, 59).toInstant(ZoneOffset.UTC).toEpochMilli();

	        conditions.add(new Document("open_date", new Document("$gte", startEpoch).append("$lte", endEpoch)));
	    }
	    else if (finMes != null && finAnio != null) {
	        // Caso 3: desde el inicio hasta fecha fin
	        long endEpoch = LocalDate.of(finAnio, finMes, 1)
	                .plusMonths(1).minusDays(1)
	                .atTime(23, 59, 59).toInstant(ZoneOffset.UTC).toEpochMilli();

	        conditions.add(new Document("open_date", new Document("$lte", endEpoch)));
	    }
	    else {
	        // Caso 1: todos → no se añade condición
	        conditions.add(new Document()); 
	    }

	    return conditions;
	}

}
