package com.nespresso.sofa.interview.parking.entities;

public final class DisabledBay extends Bay
{
	@Override
	String whenAvailable()
	{
		return "@";
	}
}
