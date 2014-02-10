package com.github.jntakpe.j2utils.listener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.servlets.MetricsServlet;

/**
 * Injecte l'instance du MetricRegistry dans la servlet correspondante
 *
 * @author jntakpe
 */
public class MetricsServletListener extends MetricsServlet.ContextListener {

    public static final MetricRegistry METRIC_REGISTRY = SharedMetricRegistries.getOrCreate("fmkMetrics");

    @Override
    protected MetricRegistry getMetricRegistry() {
        return METRIC_REGISTRY;
    }

}
