package com.nespresso.sofa.interview.parking.entities;

import java.util.Arrays;

public final class Car
{
	public static enum Type
	{
		C ('C'),
		M ('M'),
		D ('D');
		
		private final char equivalentCharacter;

		private Type(char equivalentCharacter)
		{
			this.equivalentCharacter = equivalentCharacter;
		}

		public char getEquivalentCharacter()
		{
			return equivalentCharacter;
		}

		@Override
		public String toString ()
		{
			return String.valueOf(equivalentCharacter);
		}
		
		public static Type fromEquivalentCharacter (final char equivalentCharacter)
		{
			return Arrays.stream(values())
				.filter(type -> type.getEquivalentCharacter() == equivalentCharacter)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException());
		}
	}
	
	private final Type type;

	public Car(Type type)
	{
		this.type = type;
	}
	
	@Override
	public String toString()
	{
		return type.toString();
	}
}
