package com.github.jntakpe.j2utils.listener;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

/**
 * Injecte l'instance du MetricsHealthCheck créée par Spring dans la servlet correspondante
 *
 * @author jntakpe
 */
public class MetricsHealthCheckListener extends HealthCheckServlet.ContextListener {

    public static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return HEALTH_CHECK_REGISTRY;
    }
}
