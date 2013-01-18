package com.supinbank.humanresources;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="advisor")
public class Advisor extends Person implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	
}
