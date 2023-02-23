package com.redis.isch.generador;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.redis.isch.redisComm.RedisCommSCH;
import com.redis.isch.redisComm.modelo.PaqRedis;

@Component
public class GeneradorRampa implements InitializingBean{

	@Autowired 
	RedisCommSCH redisCommSCH;
	
	
	float valor=0.0F;
	int contador=0;
	int contadorPrev=-1;
	
	boolean inicializado=false;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		inicializado = true;
	}
	
	
	
	@Scheduled(fixedDelay = 500)
	public void scheduleFixedDelayTask() {
		
		if(inicializado) {
			valor  = valor +0.5F;
			if(valor >=10.0F) {
				valor=0.0F;
				PaqRedis paqContador = new PaqRedis("heartbeat",contador); 
				redisCommSCH.colocaEnvio(paqContador);
				contador= contador+1;
				if(contador >= 400)
					contador = 0;
			}
		
			PaqRedis paqRampa = new PaqRedis("rampa/id/001",valor);
			
			
			redisCommSCH.colocaEnvio(paqRampa);
			
			
			if(contador != contadorPrev) {
				contadorPrev = contador;
				System.out.println("Contador HeartBeat:" + contador);
			}
		}
	}
	

}
