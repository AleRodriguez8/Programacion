package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Principal {
	
	public static void main(String args[]) throws IOException {
		//ruta del  archivo
		File archivo = new File("C:\\Users\\NOX\\Desktop\\Progamacion\\Whatsapp\\app\\chat.txt");
		// BR para leer fichero codificado UTF-8
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8));
		//Arraylist en el que guardo los mensajes completos
		ArrayList <String> mensajes = new ArrayList <String>();
		//ArrayList en el que guardo los mensajes transformados a objetos Chat
		ArrayList <Chat> datos = new ArrayList <Chat>();
		//HashMap en la que se encuentran las veces que mandan mensajes cada usuario
		HashMap <String, Integer> userMsjs = new HashMap<>();
		//HashMap en el que se encuentra la cantidad de mensajes enviadas al dia
		HashMap <LocalDate, Integer> mensajesAlDia = new HashMap<>();
		//solo para que funcione metodo contarUsuariosRepetidos, ArrayList con solo nombres de usuarios
		ArrayList <String> usuarios = new ArrayList <String>();
		//solo para que funcione metodo contarMensajesDiarios, ArrayList con solo fechas de envio de mensajes
		ArrayList <LocalDate> fechas = new ArrayList <LocalDate>();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		mensajes = rellenarArrayString(br);
		datos = almacenarDatos(mensajes);
		usuarios = obtenerUser(mensajes);
		fechas = obtenerFecha(mensajes);
		userMsjs = contarUsuariosRepetidos(usuarios);
		mensajesAlDia = contarMensajesDiarios(fechas);
		
		
		
	}
	public static ArrayList <String> rellenarArrayString(BufferedReader x) throws IOException{
		String linea = "";
		ArrayList <String> mensajes = new ArrayList <String>();
		
		while(linea != null) {
			linea = x.readLine();
			if(linea != null) {
				mensajes.add(linea);
			}
		}
		x.close();
		return mensajes;
	}
	public static String extraerFecha(String x) {
		String [] split = x.split("-");
		String cadena = split[0];
		String [] split2 = cadena.split("\\s+");
		String fecha = split2[0];
		return fecha;
	}
	public static ArrayList <LocalDate> parsearFecha(ArrayList <String> x) {
		ArrayList <LocalDate> fechaParseada = new ArrayList <LocalDate>();
		Integer sumaAno = 2000;
		for(int i = 0 ; i < x.size() ; i++) {
			String [] split = x.get(i).split("/");
			Integer dia = Integer.parseInt(split[0]);
			Integer mes = Integer.parseInt(split[1]);
			Integer ano = sumaAno+Integer.parseInt(split[2]);
			fechaParseada.add(LocalDate.of(ano, mes, dia));
		}
		return fechaParseada;
	}
	public static ArrayList <LocalDate> obtenerFecha(ArrayList <String> x){
		ArrayList <String> fechaExtraida = new ArrayList <String>();
		ArrayList <LocalDate> fechaParseada = new ArrayList <LocalDate>();
		for(int i = 0 ; i < x.size() ; i++) {
			String stringFecha = extraerFecha(x.get(i));
			fechaExtraida.add(stringFecha);
		}
		fechaParseada = parsearFecha(fechaExtraida);
		return fechaParseada;
	}
	public static String extraerHora(String x) {
		String [] split = x.split("-");
		String cadena = split[0];
		String [] split2 = cadena.split("\\s+");
		String hora = split2[1];
		return hora;
	}
	public static ArrayList <Time> parsearHora(ArrayList <String> x){
		ArrayList <Time> horaParseada = new ArrayList <Time>();
		String segundos = ":00";
		for(int i = 0 ; i < x.size() ; i++) {
			String hora = x.get(i);
			hora = hora + segundos;
			Time horaParse = Time.valueOf(hora);
			horaParseada.add(horaParse);
		}
		return horaParseada;
	}
	public static ArrayList <Time> obtenerHora(ArrayList <String> x){
		ArrayList <String> horaString = new ArrayList <String>();
		ArrayList <Time> horas = new ArrayList <Time>();
		for(int i=0;i<x.size();i++) {
			String stringHora = extraerHora(x.get(i));
			horaString.add(stringHora);
		}
		horas = parsearHora(horaString);
		return horas;
	}
	public static String extraerUser(String x) {
		String [] split = x.split("-");
		String cadena = split[1];
		String [] split2 = cadena.split(":");
		String userName = split2[0];
		userName = userName.trim();
		return userName;
	}
	public static ArrayList <String> obtenerUser(ArrayList <String> x){
		ArrayList <String> users = new ArrayList <String>();
		for(int i = 0 ; i < x.size() ; i++) {
			String nombre = extraerUser(x.get(i));
			users.add(nombre);
		}
		return users;
	}
	public static String extraerMensaje(String x) {
		String [] split = x.split("-");
		String cadena = split[1];
		String [] split2 = cadena.split(":");
		String mensaje = split2[1];
		mensaje = mensaje.trim();
		return mensaje;
	}
	public static ArrayList <String> obtenerMensaje(ArrayList <String> x){
		ArrayList <String> mensajes = new ArrayList <String>();
		for(int i = 0 ; i < x.size() ; i++) {
			String mensaje = extraerMensaje(x.get(i));
			mensajes.add(mensaje);
		}
		return mensajes;
	}
	public static ArrayList <Chat> almacenarDatos(ArrayList <String> x){
		ArrayList <LocalDate> fechas = new ArrayList <LocalDate>();
		ArrayList <Time> horas = new ArrayList <Time>();
		ArrayList <String> users = new ArrayList <String>();
		ArrayList <String> mensajes = new ArrayList <String>();
		ArrayList <Chat> datos = new ArrayList <Chat>();
		fechas = obtenerFecha(x);
		horas = obtenerHora(x);
		users = obtenerUser(x);
		mensajes = obtenerMensaje(x);
		for(int i = 0 ; i < x.size() ; i++) {
			LocalDate fecha = fechas.get(i);
			Time hora = horas.get(i);
			String user = users.get(i);
			String mensaje = mensajes.get(i);
			Chat dato = new Chat(fecha,hora,user,mensaje);
			datos.add(dato);
		}
		return datos;
	}
	public static ArrayList <String> eliminarUsuariosRepetidos(ArrayList <String> x){
		ArrayList <String> usuarios = new ArrayList <String>();
		ArrayList <String> usuariosLimpio = new ArrayList <String>();
		usuarios = obtenerUser(x);
		HashSet hs = new HashSet();
		hs.addAll(usuarios);
		usuariosLimpio.addAll(hs);
		return usuariosLimpio;
	}
	public static HashMap <String, Integer> contarUsuariosRepetidos(ArrayList <String> x){
		HashMap <String, Integer> hm = new HashMap<>();
		for(int i=0; i<x.size();i++) {
			if(hm.containsKey(x.get(i))) {
				hm.put(x.get(i), hm.get(x.get(i))+1);
			}
			else {
				hm.put(x.get(i), 1);
			}
		}
		return hm;
	}
	public static ArrayList <LocalDate> eliminarFechasRepetidas(ArrayList <String> x){
		ArrayList <LocalDate> fechas = new ArrayList <LocalDate>();
		ArrayList <LocalDate> fechasLimpias = new ArrayList <LocalDate>();
		fechas = obtenerFecha(x);
		HashSet hs = new HashSet();
		hs.addAll(fechas);
		fechasLimpias.addAll(hs);
		Collections.sort(fechasLimpias);
		return fechasLimpias;
	}
	public static HashMap <LocalDate, Integer> contarMensajesDiarios(ArrayList <LocalDate> x){
		HashMap <LocalDate, Integer> hm = new HashMap<>();
		for(int i=0; i<x.size();i++) {
			if(hm.containsKey(x.get(i))) {
				hm.put(x.get(i), hm.get(x.get(i))+1);
			}
			else {
				hm.put(x.get(i), 1);
			}
		}
		return hm;
	}
}
