package com.redis.isch.redisComm;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisURI;
import com.redis.isch.redisComm.modelo.PaqRedis;

@Component
public class RedisCommSCH  implements InitializingBean{
	
	@Value("${redis.server}")
	String server;
	
	boolean inicializado=false;
	RedisClient redisClient;
	RedisConnection<String, String> connection;
	Queue <PaqRedis> listaMensajes;
	PaqDaemon paqDaemon;
	
	
	class PaqDaemon extends Thread{  
		public void run(){  
		System.out.println("Proceso de monitoreo de cola de paquetes inicializado");  

		while (true) {
			
			if(inicializado) {
				
				if(!listaMensajes.isEmpty()) {
					PaqRedis paquete = listaMensajes.poll();
					if(paquete!=null)
						enviaRedis(paquete );
				}
			}
		}
		
		}; 
	}
	
	
	@Scheduled(fixedDelay = 1)
	public void revisorColaMsg() {
		
		if(inicializado) {
			
			if(!listaMensajes.isEmpty()) {
				PaqRedis paquete = listaMensajes.poll();
				if(paquete!=null)
					enviaRedis(paquete );
			}
		}
		
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		//System.out.println(server);
		listaMensajes = new LinkedList<PaqRedis>();
		conectaServer();
		//Revisar que se tildaba con este thread TODO
		//	paqDaemon = new PaqDaemon();
//		paqDaemon.start();
	}
	
	private void conectaServer() {
		redisClient = new RedisClient(
		    RedisURI.create(server));
		connection = redisClient.connect();
		inicializado = true;
	    System.out.println("Servidor Redis Conectado");
	    escribeTest();
	    recibeTest();
	}
	

	private void escribeTest() {
		connection.set("foo", "bar");
	}
	
	private void enviaRedis(PaqRedis paquete ) {
		
		if(paquete.getKey()!=null && paquete.getMessage()!=null) {
			
			connection.set(paquete.getKey(), paquete.getMessage());
		}
		
	}
	
	private void recibeTest() {
		String value = connection.get("foo");
		System.out.println("Valor Leido: " + value);
		
	}

	public void cierraConexion() {

		try {
			connection.close();
			redisClient.shutdown();
		}
		catch(Exception e) {
			System.out.println("Error de cierre de conexión");
		}
	}
	
	public void colocaEnvio(PaqRedis paquete) {
		if(paquete!=null && listaMensajes!=null) {
			listaMensajes.add(paquete);
		}
		
	}
}
