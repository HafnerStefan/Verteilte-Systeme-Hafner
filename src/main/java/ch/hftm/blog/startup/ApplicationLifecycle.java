package ch.hftm.blog.startup;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.runtime.Startup;
import ch.hftm.blog.util.KeyGeneratorHelper;

@Startup
@ApplicationScoped
public class ApplicationLifecycle {

	private final KeyGeneratorHelper keyGeneratorHelper;

	public ApplicationLifecycle(KeyGeneratorHelper keyGeneratorHelper) {
		this.keyGeneratorHelper = keyGeneratorHelper;
	}

	@PostConstruct
	public void onStart() {
		keyGeneratorHelper.generateKeys();
	}
}
