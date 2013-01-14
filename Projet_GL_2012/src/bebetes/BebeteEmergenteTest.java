package bebetes;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import bebetes.BebeteEmergente;
import bebetes.ChampDeBebetes;

public class BebeteEmergenteTest {

	@Mock
	private ChampDeBebetes champBebete;
	@Mock
	private BebeteEmergente bebeteMock;
	private BebeteEmergente bebeteSpy;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(champBebete.getLargeur()).thenReturn(640);
		when(champBebete.getHauteur()).thenReturn(480);
		ArrayList<BebeteEmergente> listeBebeteEmergente = new ArrayList<BebeteEmergente>();
		listeBebeteEmergente.add(bebeteMock);
		when(bebeteMock.getX()).thenReturn(320);
		when(bebeteMock.getY()).thenReturn(230);
		when(bebeteMock.getVitesseCourante()).thenReturn(20.0f);
		when(bebeteMock.getDirectionCourante()).thenReturn((float) Math.PI / 4);
		bebeteSpy = spy(new BebeteEmergente(champBebete, 320, 240, 0.0f, 10.0f,
				Color.RED));
		doReturn(listeBebeteEmergente).when(bebeteSpy).getChosesVues();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculeDeplacementAFaire() {
		float direction = bebeteSpy.getDirectionCourante();
		float vitesse = bebeteSpy.getVitesseCourante();
		bebeteSpy.calculeDeplacementAFaire();
		verify(bebeteSpy, times(1)).setVitesseCourante(anyFloat());
		verify(bebeteSpy, times(1)).setDirectionCourante(anyFloat());
		verify(bebeteSpy, times(1)).getChosesVues();
		assertEquals("Direction des bebetes", bebeteSpy.getDirectionCourante(),
				(direction + bebeteMock.getDirectionCourante()) / 2, 0.1f);
		assertEquals("Moyenne des vitesses des bebetes",
				bebeteSpy.getVitesseCourante(),
				(vitesse + bebeteMock.getVitesseCourante()) / 2, 0.1f);
	}

}
