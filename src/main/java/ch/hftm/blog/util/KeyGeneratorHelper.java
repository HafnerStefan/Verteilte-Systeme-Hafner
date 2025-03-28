package ch.hftm.blog.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.Date;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class KeyGeneratorHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeyGeneratorHelper.class);

	@ConfigProperty(name = "smallrye.jwt.sign.key.location")
	String privateKeyLocation;

	@ConfigProperty(name = "mp.jwt.verify.publickey.location")
	String publicKeyLocation;

	@ConfigProperty(name = "jwt.expiration.days")
	int jwtExpirationDays;
	public void generateKeys() {
		try {
			if (Files.exists(Path.of(privateKeyLocation))) {
				LOGGER.info("Keypair already exists.");
				return;
			}

			LOGGER.info("Create new Keypair!!");
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair pair = kpg.generateKeyPair();

			generateKey(privateKeyLocation, pair.getPrivate(), "PRIVATE");
			generateKey(publicKeyLocation, pair.getPublic(), "PUBLIC");
		} catch (Exception e) {
			LOGGER.warn("Problem while Writing Keys", e);
		}
	}

	private void generateKey(String keyLocation, Key key, String type) throws IOException {
		Path keyPath = Path.of(keyLocation);
		if (keyPath.getParent() != null && !Files.exists(keyPath.getParent())) {
			Files.createDirectories(keyPath.getParent());
		}

		Base64.Encoder encoder = Base64.getEncoder();
		try (Writer outPrivate = new FileWriter(keyLocation)) {
			outPrivate.write("-----BEGIN RSA " + type + " KEY-----\n");
			outPrivate.write(encoder.encodeToString(key.getEncoded()));
			outPrivate.write("\n-----END RSA " + type + " KEY-----\n");
		}
	}

}
