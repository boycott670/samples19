package com.nespresso.sofa.interview.parking.entities;

import java.util.Optional;

public class Bay extends AbstractBay
{
	private Car car;
	
	public void spotCar (final Car car)
	{
		this.car = car;
	}
	
	public boolean isAvailable ()
	{
		return !Optional.ofNullable(car).isPresent();
	}
	
	String whenAvailable ()
	{
		return "U";
	}
	
	@Override
	public String toString()
	{
		return Optional.ofNullable(car).map(Object::toString).orElse(whenAvailable());
	}

}
