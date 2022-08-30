package tcm.laq.bitcoinProjectLAQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BitcoinProjectLaqApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitcoinProjectLaqApplication.class, args);
	}

}
