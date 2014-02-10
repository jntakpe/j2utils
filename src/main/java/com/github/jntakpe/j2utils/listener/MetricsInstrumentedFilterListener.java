package com.github.jntakpe.j2utils.listener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;

/**
 * Injecte l'instance du MetricRegistry créée par Spring dans le contexte de la servlet Metrics
 *
 * @author jntakpe
 */
public class MetricsInstrumentedFilterListener extends InstrumentedFilterContextListener {

    /**
     * {@inheritDoc}
     */
    @Override
    protected MetricRegistry getMetricRegistry() {
        return MetricsServletListener.METRIC_REGISTRY;
    }
}
