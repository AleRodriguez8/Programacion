package app;

import java.sql.Time;
import java.time.LocalDate;

public class Chat implements Comparable <Chat> {
	public LocalDate fecha;
	public Time hora;
	public String nombre; 
	public String mensaje;
	
	public Chat(LocalDate fecha, Time hora, String nombre, String mensaje) {
		this.fecha=fecha;
		this.hora=hora;
		this.nombre=nombre;
		this.mensaje=mensaje;
	}
	@Override
	public int compareTo (Chat o) {
		if(fecha.isBefore(o.fecha)) {
			return -1;
		}
		if(fecha.isAfter(o.fecha)) {
			return 1;
		}
		return 0;
	}
	@Override
	public String toString() {
		return fecha+" "+hora+" "+nombre+" "+mensaje;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public Time getHora() {
		return hora;
	}
	public void setHora(Time hora) {
		this.hora = hora;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}