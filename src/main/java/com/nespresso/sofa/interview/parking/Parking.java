package com.nespresso.sofa.interview.parking;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;

import com.nespresso.sofa.interview.parking.entities.AbstractBay;
import com.nespresso.sofa.interview.parking.entities.Bay;
import com.nespresso.sofa.interview.parking.entities.Car;
import com.nespresso.sofa.interview.parking.entities.DisabledBay;
import com.nespresso.sofa.interview.parking.entities.PedestrianExit;

public final class Parking
{
	private final AbstractBay[] bays;

	public Parking(AbstractBay[] bays)
	{
		this.bays = bays;
	}
	
	public long getAvailableBays ()
	{
		return Arrays.stream(bays)
			.filter(bay -> Bay.class.isAssignableFrom(bay.getClass()))
			.map(Bay.class::cast)
			.filter(Bay::isAvailable)
			.count();
	}
	
	private int nextAvailableBay ()
	{
		final IntPredicate isValidNextAvailableBayIndex = index -> Bay.class == bays[index].getClass() && ((Bay)bays[index]).isAvailable();
		
		int neighborAt = 1;
		
		while (neighborAt < bays.length)
		{
			for (int bayIndex = 0 ; bayIndex < bays.length ; bayIndex ++)
			{
				if (PedestrianExit.class == bays[bayIndex].getClass())
				{
					if (bayIndex - neighborAt >= 0 && isValidNextAvailableBayIndex.test(bayIndex - neighborAt))
					{
						return bayIndex - neighborAt;
					}
					
					if (bayIndex + neighborAt < bays.length && isValidNextAvailableBayIndex.test(bayIndex + neighborAt))
					{
						return bayIndex + neighborAt;
					}
				}
			}
			
			neighborAt ++;
		}
		
		return -1;
	}
	
	private int nextAvailableDisabledBay ()
	{
		for (int bayIndex = bays.length - 1 ; bayIndex >= 0 ; bayIndex --)
		{
			if (bays[bayIndex] instanceof DisabledBay && ((Bay)bays[bayIndex]).isAvailable())
			{
				return bayIndex;
			}
		}
		
		return -1;
	}
	
	public int parkCar (final char carTypeEquivalentCharacter)
	{
		final Car.Type carType = Car.Type.fromEquivalentCharacter(carTypeEquivalentCharacter);
		
		final IntSupplier nextAvailableIndexSupplier = carType == Car.Type.D ? this::nextAvailableDisabledBay : this::nextAvailableBay;
		
		final int nextAvailableIndex = nextAvailableIndexSupplier.getAsInt();

		if (nextAvailableIndex != -1)
		{
			((Bay)bays[nextAvailableIndex]).spotCar(new Car(carType));
		}
		
		return nextAvailableIndex;
	}
	
	public boolean unparkCar (final int notAvailableBayIndex)
	{
		if (bays[notAvailableBayIndex] instanceof Bay && !((Bay)bays[notAvailableBayIndex]).isAvailable())
		{
			((Bay)bays[notAvailableBayIndex]).spotCar(null);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		final int rows = Double.valueOf(Math.sqrt(bays.length)).intValue();
		
		boolean leftToRight = true;
		
		final StringBuilder output = new StringBuilder();
		
		for (int baysRowIndex = 0 ; baysRowIndex < rows ; baysRowIndex ++)
		{
			if (leftToRight)
			{
				for (int bayIndex = baysRowIndex * rows ; bayIndex < baysRowIndex * rows + rows ; bayIndex ++)
				{
					output.append(bays[bayIndex]);
				}
			}
			else
			{
				for (int bayIndex = baysRowIndex * rows + rows - 1 ; bayIndex >= baysRowIndex * rows ; bayIndex --)
				{
					output.append(bays[bayIndex]);
				}
			}
			
			leftToRight = !leftToRight;
			
			output.append("\n");
		}
		
		return output.toString().trim();
	}
}
