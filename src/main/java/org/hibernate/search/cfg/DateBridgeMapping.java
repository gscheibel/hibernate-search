package org.hibernate.search.cfg;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.analysis.TokenizerFactory;
import org.hibernate.search.SearchException;
import org.hibernate.search.annotations.Resolution;

public class DateBridgeMapping {
	
	private final SearchMapping mapping;
	private final Map<String, Object> resolution;
	private EntityDescriptor entity;
	private PropertyDescriptor property;

	public DateBridgeMapping(SearchMapping mapping,EntityDescriptor entity,PropertyDescriptor property, Resolution resolution) {
		if (resolution == null) {
			throw new SearchException("Resolution required in order to index calendar property");
		}
		this.mapping = mapping;
		this.resolution = new HashMap<String, Object>();
		this.entity = entity;
		this.property = property;
		this.resolution.put("resolution", resolution);
		property.setDateBridge(this.resolution);
	}
	
	
	public FieldMapping field() {
		return new FieldMapping(property, entity, mapping);
	}

	public PropertyMapping property(String name, ElementType type) {
		return new PropertyMapping(name, type, entity, mapping);
	}

	public AnalyzerDefMapping analyzerDef(String name, Class<? extends TokenizerFactory> tokenizerFactory) {
		return new AnalyzerDefMapping(name, tokenizerFactory, mapping);
	}

	public EntityMapping entity(Class<?> entityType) {
		return new EntityMapping(entityType, mapping);
	}

}