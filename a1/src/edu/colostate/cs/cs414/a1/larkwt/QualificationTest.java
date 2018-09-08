package edu.colostate.cs.cs414.a1.larkwt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QualificationTest {
	
	Qualification q1;
	Qualification q1same;
	Qualification q1diff;
	
	@BeforeEach
	void setUp() throws NullPointerException, InvalidDescription {
		q1 = new Qualification("q1");
		q1same = new Qualification("q1");
		q1diff = new Qualification("q1diff");
	}

	@Test
	void testToString() {
		assertEquals("q1", q1.toString());
	}
	
	@Test
	void testQualificationInvalidDescEmtpy() {
		assertThrows(InvalidDescription.class, () -> new Qualification("  \t "));
	}
	
	@Test
	void testQualificationInvalidDesc() {
		assertThrows(InvalidDescription.class, () -> new Qualification(""));
	}
	
	@Test
	void testQualificationNullDesc() {
		assertThrows(NullPointerException.class, () -> new Qualification(null));
	}

	@Test
	void testEqualsItself() {
		assertEquals(q1, q1);
	}
	
	@Test
	void testEqualsNotNull() {
		assertNotEquals(q1, null);
	}
	
	@Test
	void testEqualsSame() {
		assertEquals(q1, q1same);
	}
	
	@Test
	void testEqualsNotSame() {
		assertNotEquals(q1, q1diff);
	}
	
	@Test
	void testEqualsNotSameClass() {
		assertNotEquals(q1, "not qual");
	}

	@Test
	void testHashCodeExact() {
		assertEquals(q1.hashCode(), q1same.hashCode());
	}
	
	@Test
	void testHashCodeSame() {
		assertEquals(q1.hashCode(), q1.hashCode());
	}
	
	@Test
	void testAddWorkerNull() {
		assertThrows(NullPointerException.class, () -> q1.addWorker(null));
	}
	
	@Test
	void testAddWorkerDidAdd() throws NullPointerException, InvalidName, InvalidQualifications {
		// TODO Actually validate this
		HashSet<Qualification> qs = new HashSet<>();
		qs.add(q1);
		qs.add(q1diff);
		Worker worker = null;
		worker = new Worker("name", qs);
		q1.addWorker(worker);
	}

	@Test
	void testAddProjectNull() {
		assertThrows(NullPointerException.class, () -> q1.addProject(null));
	}

	@Test
	void testAddProjectDidAdd() throws NullPointerException, InvalidName, InvalidQualifications {
		// TODO Actually validate this
		HashSet<Qualification> qs = new HashSet<>();
		qs.add(q1);
		qs.add(q1diff);
		Project project = null;
		project = new Project("name", ProjectSize.LARGE, ProjectStatus.ACTIVE, qs);
		q1.addProject(project);
	}
}
