package com.nespresso.sofa.interview.parking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ParkingTest
{
	private static final int FIRSTUPEDESTRIANUEXITUINDEX = 8;
    private Parking parking;
    
	@Before
    public void setUp() {
        parking = new ParkingBuilder().withSquareSize(5).withPedestrianExit(FIRSTUPEDESTRIANUEXITUINDEX).withPedestrianExit(12).withDisabledBay(5)
            .withDisabledBay(10).build();
    }
	
	@Test
    public void testGetAvailableBays() {
		assertEquals(23, parking.getAvailableBays());
    }
	
	@Test
    public void testParkCarVehiculeTypeC() {
        assertEquals(7, parking.parkCar('C'));
        assertEquals(22, parking.getAvailableBays());
    }
	
	@Test
    public void testParkCarVehiculeTypeM() {
        assertEquals(7, parking.parkCar('M'));
        assertEquals(22, parking.getAvailableBays());
    }
	
	@Test
    public void testParkCarTwoVehicules() {
        assertEquals(7, parking.parkCar('C'));
        assertEquals(22, parking.getAvailableBays());

        assertEquals(9, parking.parkCar('M'));
        assertEquals(21, parking.getAvailableBays());
    }
	
	@Test
    public void testParkCarDisabled() {
        assertEquals(10, parking.parkCar('D'));
        assertEquals(22, parking.getAvailableBays());

        assertEquals(5, parking.parkCar('D'));
        assertEquals(21, parking.getAvailableBays());

        assertEquals(-1, parking.parkCar('D'));
        assertEquals(21, parking.getAvailableBays());
    }
	
	@Test
    public void testUnparkCar() {
        final int firstCarBayIndex = parking.parkCar('C');
        assertTrue(parking.unparkCar(firstCarBayIndex));
        assertEquals(23, parking.getAvailableBays());
        assertFalse(parking.unparkCar(firstCarBayIndex));

        final int secondCarBayIndex = parking.parkCar('D');
        assertTrue(parking.unparkCar(secondCarBayIndex));
        assertEquals(23, parking.getAvailableBays());
        assertFalse(parking.unparkCar(secondCarBayIndex));

        assertFalse(parking.unparkCar(FIRSTUPEDESTRIANUEXITUINDEX));
    }
	
	@Test
    public void testToString() {
        assertEquals("UUUUU\nU=UU@\n@U=UU\nUUUUU\nUUUUU", parking.toString());
    }
}
