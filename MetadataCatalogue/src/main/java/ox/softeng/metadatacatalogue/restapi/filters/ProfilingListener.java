package ox.softeng.metadatacatalogue.restapi.filters;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfilingListener implements ApplicationEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(ProfilingListener.class);
	
	@Override
    public void onEvent(ApplicationEvent applicationEvent) {
        switch (applicationEvent.getType()) {
            case INITIALIZATION_FINISHED:
                logger.info("Jersey application started.");
                break;
		default:
			break;
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return new MyRequestEventListener();
    }


    public static class MyRequestEventListener implements RequestEventListener {
        private volatile long methodStartTime;

        @Override
        public void onEvent(RequestEvent requestEvent) {
            switch (requestEvent.getType()) {
                case RESOURCE_METHOD_START:
                    methodStartTime = System.currentTimeMillis();
                    break;
                case RESOURCE_METHOD_FINISHED:
                    long methodExecution = System.currentTimeMillis() - methodStartTime;
                    final String methodName = requestEvent.getUriInfo().getMatchedResourceMethod().getInvocable().getHandlingMethod().getName();
                    final String servicePath = requestEvent.getUriInfo().getPath();
                    logger.info("Service path '" + servicePath + "' called: method '" + methodName + "' executed. Processing time: " + methodExecution + " ms");
                    break;
			default:
				break;
            }
        }
    }
}