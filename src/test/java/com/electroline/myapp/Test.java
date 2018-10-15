package com.electroline.myapp;


import junit.framework.Assert;

public class Test {
	
	@org.junit.Test
	public void mnozeniePrzezZeroZwracaZero() {
		int liczba = 0;
		int liczba2 = 0;
		Assert.assertEquals("Zero jest równe zero", liczba, liczba2);
	}
	
	@org.junit.Test
	public void sprawdzanieRownosciTrueFalse() {
		Assert.assertFalse("True nie jest rowne false",false);
	}
}
