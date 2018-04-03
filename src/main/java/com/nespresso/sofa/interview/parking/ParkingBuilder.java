package com.nespresso.sofa.interview.parking;

import java.util.stream.Stream;

import com.nespresso.sofa.interview.parking.entities.AbstractBay;
import com.nespresso.sofa.interview.parking.entities.Bay;
import com.nespresso.sofa.interview.parking.entities.DisabledBay;
import com.nespresso.sofa.interview.parking.entities.PedestrianExit;

public final class ParkingBuilder
{
	private AbstractBay[] bays;
	
	public ParkingBuilder withSquareSize (int squareSize)
	{
		bays = Stream.generate(Bay::new).limit(squareSize * squareSize).toArray(AbstractBay[]::new);
		
		return this;
	}
	
	public ParkingBuilder withPedestrianExit (int pedestrianExitIndex)
	{
		bays [pedestrianExitIndex] = new PedestrianExit();
		return this;
	}
	
	public ParkingBuilder withDisabledBay (int disabledBayIndex)
	{
		bays [disabledBayIndex] = new DisabledBay();
		return this;
	}
	
	public Parking build ()
	{
		return new Parking(bays);
	}
}
