package org.ibra.license.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author docker
 *
 */
@Component
public class ServiceConfig {

	@Value(value = "${tracer.property}")
	private String tracerProperty;

	public String getTracerProperty() {
		return tracerProperty;
	}

	public void setTracerProperty(String tracerProperty) {
		this.tracerProperty = tracerProperty;
	}
}
