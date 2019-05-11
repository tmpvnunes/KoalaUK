package gui;

import auth.Connect;

public class MyApp {

	public static void main(String[] args) {
		/*
		 * Aplicacao instagram para ver quem segue o meu perfil, saber quantos seguidores tenho etc..
		 * 5 dicas 
		 * 
		 */
		
		try {
			
			Connect.InstaAPP();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
