package bebetes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bebetes.ChampDeBebetes;
import bebetes.FabriqueEntites;

public class BebeteHasardTest {

	private BebeteHasard bebeteHasard = null;
	private ChampDeBebetes champ;

	protected FabriqueEntites fabrique; // r�f�rence sur la fabrique
	protected List<Bebete> lb ;
	private int count = 0;
	
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		FabriqueEntites.init(FabriquePlugins.class);
	}
	
	@Before
	public void setUp() throws Exception {
		fabrique = FabriqueEntites.getFabriqueEntites();
		champ = fabrique.creeChampDeBebetes(640, 480, 1);
		lb = fabrique.fabriqueBebetes(champ,1);
		bebeteHasard = (BebeteHasard) lb.get(0).getCurrentState();
	}


	@Test
	public void testEffectueDeplacementMilieu() {
		// mise en place : rien a changer, juste sauvegarder les anciennes
		// valeurs pour l'oracle
		float oldvit = bebeteHasard.getVitesseCourante();
		float olddir = bebeteHasard.getDirectionCourante();
		bebeteHasard.effectueDeplacement();
		// oracle
		assertEquals("direction inchangee",
				bebeteHasard.getDirectionCourante(), olddir, 0.1f);
		assertEquals("vitesse inchangee", bebeteHasard.getVitesseCourante(),
				oldvit, 0.1f);
	}

	@Test
	public void testEffectueDeplacementDepassementDroit() {
		// mise en place
		bebeteHasard.setX(637);
		bebeteHasard.setY(240);
		bebeteHasard.setDirectionCourante((float) Math.PI / 5);
		// test
		bebeteHasard.effectueDeplacement();
		// oracle
		assertEquals("nouvelle direction apr�s rebond", bebeteHasard
				.getDirectionCourante(), (float) (Math.PI * 4 / 5), 0.1f);
		assertTrue("x revenu dans le champ", bebeteHasard.getX() < 640);
		assertTrue("y plus grand", bebeteHasard.getY() > 240);
	}

	@Test
	public void testEffectueDeplacementDepassementGauche() {
		// mise en place
		bebeteHasard.setX(3);
		bebeteHasard.setY(240);
		bebeteHasard.setDirectionCourante((float) Math.PI * 6 / 5);
		// test
		bebeteHasard.effectueDeplacement();
		// oracle
		assertEquals("nouvelle direction apr�s rebond", bebeteHasard
				.getDirectionCourante(), (float) (Math.PI * 9 / 5), 0.1f);
		assertTrue("x revenu dans le champ", bebeteHasard.getX() > 0);
		assertTrue("y plus petit", bebeteHasard.getY() < 240);
	}

	@Test
	public void testEffectueDeplacementDepassementHaut() {
		// mise en place
		bebeteHasard.setX(320);
		bebeteHasard.setY(4);
		bebeteHasard.setDirectionCourante((float) Math.PI * 6 / 5);
		// test
		bebeteHasard.effectueDeplacement();
		// oracle
		assertEquals("nouvelle direction apr�s rebond", bebeteHasard
				.getDirectionCourante(), (float) (Math.PI * 4 / 5), 0.1f);
		assertTrue("x plus petit", bebeteHasard.getX() < 320);
		assertTrue("y revenu dans le champ", bebeteHasard.getY() > 0);
	}

	@Test
	public void testEffectueDeplacementDepassementBas() {
		// mise en place
		bebeteHasard.setX(320);
		bebeteHasard.setY(477);
		bebeteHasard.setDirectionCourante((float) Math.PI / 5);
		// test
		bebeteHasard.effectueDeplacement();
		// oracle
		assertEquals("nouvelle direction apr�s rebond", bebeteHasard
				.getDirectionCourante(), (float) (Math.PI * 9 / 5), 0.1f);
		assertTrue("x plus grand", bebeteHasard.getX() > 320);
		assertTrue("y revenu dans le champ", bebeteHasard.getY() < 480);
	}

	@Test
	public void testEffectueDeplacementZeroZeroAngle45() {
		// mise en place
		bebeteHasard.setX(3);
		bebeteHasard.setY(3);
		bebeteHasard.setDirectionCourante((float) Math.PI * 5 / 4); // on se
																	// dirige
																	// vers le
																	// coin
																	// haut/gauche
		// test
		bebeteHasard.effectueDeplacement();
		// oracle
		assertEquals("nouvelle direction apr�s rebond", (float) (Math.PI / 4),
				bebeteHasard.getDirectionCourante(), 0.1f);
		// le comportement d'un rebond dans l'angle n'est pas d�fini. Cela
		// d�pendrait effectivement de la forme qu'a l'objet, etc...
		assertTrue("x revenu dans le champ, x = " + bebeteHasard.getX(),
				0 < bebeteHasard.getX() && bebeteHasard.getX() < 12);
		assertTrue("y revenu dans le champ, y = " + bebeteHasard.getY(),
				0 < bebeteHasard.getY() && bebeteHasard.getY() < 12);
	}

	@Test
	public void testEffectueDeplacementZeroZeroTravers() {
		// mise en place
		bebeteHasard.setX(1);
		bebeteHasard.setY(1);
		bebeteHasard.setDirectionCourante((float) Math.PI * 9 / 8); // on se
																	// dirige
																	// vers le
																	// coin
																	// haut/gauche
		// test
		bebeteHasard.effectueDeplacement();
		// oracle
		assertEquals("nouvelle direction apr�s rebond", (float) (Math.PI / 8),
				bebeteHasard.getDirectionCourante(), 0.1f);
		// le comportement d'un rebond dans l'angle n'est pas d�fini. Cela
		// d�pendrait effectivement de la forme qu'a l'objet, etc...
		assertTrue("x revenu dans le champ, x = " + bebeteHasard.getX(),
				0 < bebeteHasard.getX() && bebeteHasard.getX() < 12);
		assertTrue("y revenu dans le champ, y = " + bebeteHasard.getY(),
				0 < bebeteHasard.getY() && bebeteHasard.getY() < 12);
	}

}
