package app.core.sessions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Session {
	
	public final String token;
	private Map<String, Object> attributes = new HashMap<>();
	private long lastAccessed;
	@Value("${session.max.inactive.interval:2}")
	private long maxInactiveInterval; // in milliseconds
	private static final int TOKEN_MAX_LENGTH = 15;
	
	{
		token = UUID.randomUUID().toString().replace("-","").substring(0,TOKEN_MAX_LENGTH);
		resetLastAccessed();
	}
	
	
	@PostConstruct
	private void init() {
		maxInactiveInterval = TimeUnit.MINUTES.toMillis(maxInactiveInterval);
	}
	
	public void resetLastAccessed() {
		lastAccessed = System.currentTimeMillis();
	}
	
	public void setAttribute(String attrName, Object attrVal) {
		attributes.put(attrName, attrVal);
	}
	
	public Object getAttribute(String attrName) {
		return attributes.get(attrName);
	}

	public long getLastAccessed() {
		return lastAccessed;
	}

	public long getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(long maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}
	
}
