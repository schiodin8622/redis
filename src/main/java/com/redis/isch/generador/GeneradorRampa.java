package com.redis.isch.generador;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GeneradorRampa implements InitializingBean{

	float valor=0.0F;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Scheduled(fixedDelay = 500)
	public void scheduleFixedDelayTask() {
		valor  = valor +0.1F;
		if(valor >=10.0F)
			valor=0.0F;
		
		System.out.println(valor);
	
	}
	

}
