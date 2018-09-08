package edu.colostate.cs.cs414.a1.larkwt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectTest {
	
	private String name;
	private ProjectSize size;
	private ProjectStatus status;
	private HashSet<Qualification> qs;
	private Qualification qual1;
	private Project p1;
	private Project p1same;
	private Project p1diff;
	
	@BeforeEach
	void initThings() throws NullPointerException, InvalidDescription, InvalidName, InvalidQualifications {
		name = "validname";
		size = ProjectSize.LARGE;
		status = ProjectStatus.ACTIVE;
		qs = new HashSet<Qualification>();
		qual1 = new Qualification("qual1");
		qs.add(qual1);
		qs.add(new Qualification("qual2"));
		p1 = new Project(name, size, status, qs);
		p1same = new Project(name, size, status, qs);
		p1diff = new Project(name+"diff", size, status, qs);
	}
	
	@Test
	void testProjectSetName() {
		assertEquals(name, p1.getName());
	}
	
	@Test
	void testProjectSize() {
		assertEquals(size, p1.getSize());
	}
	
	@Test
	void testProjectStatus() {
		assertEquals(status, p1.getStatus());
	}

	@Test
	void testProjectBadNameSpace() {
		assertThrows(InvalidName.class, () -> new Project(" \t  ", size, status, qs));
	}
	
	@Test
	void testProjectBadNameEmpty() {
		assertThrows(InvalidName.class, () -> new Project("", size, status, qs));
	}
	
	@Test
	void testProjectNullName() {
		assertThrows(NullPointerException.class, () -> new Project(null, size, status, qs));
	}
	
	@Test
	void testProjectNullQs() {
		assertThrows(NullPointerException.class, () -> new Project(name, size, status, null));
	}
	
	@Test
	void testProjectBadQs() {
		assertThrows(InvalidQualifications.class, () -> new Project(name, size, status, new HashSet<Qualification>()));
	}
	
	@Test
	void testEqualExact() {
		assertEquals(p1, p1);
	}
	
	@Test
	void testEqualNotNull() {
		assertNotEquals(p1, null);
	}
	
	@Test
	void testEqualsSame() {
		assertEquals(p1, p1same);
	}
	
	@Test
	void testEqualsDiff() {
		assertNotEquals(p1, p1diff);
	}
	
	@Test
	void testEqualsObj() {
		assertNotEquals(p1, name);
	}
	
	
	private Project base;
	private Project basesize;
	private Project basestat;
	private Project baseqs;

	@BeforeEach
	void buildDiffSet() throws NullPointerException, InvalidDescription, InvalidName, InvalidQualifications {
		HashSet<Qualification> qs1 = new HashSet<>();
		qs1.add(new Qualification("qs11"));
		qs1.add(new Qualification("qs12"));
		HashSet<Qualification> qs2 = new HashSet<>();
		qs2.add(new Qualification("qs21"));
		qs2.add(new Qualification("qs22"));
		base = new Project("base", ProjectSize.LARGE, ProjectStatus.ACTIVE, qs1);
		basesize = new Project("base", ProjectSize.SMALL, ProjectStatus.ACTIVE, qs1);
		basestat = new Project("base", ProjectSize.LARGE, ProjectStatus.PLANNED, qs1);
		baseqs = new Project("base", ProjectSize.LARGE, ProjectStatus.ACTIVE, qs2);
	}
	
	@Test
	void testEqualsDiffStat() {
		assertEquals(base, basestat);
	}
	
	@Test
	void testEqualsDiffSize() {
		assertEquals(base, basesize);
	}
	
	@Test
	void testEqualsDiffQual() {
		assertEquals(base, baseqs);
	}
	
	@Test
	void testHashCodeDiffStat() {
		assertEquals(base.hashCode(), basestat.hashCode());
	}
	
	@Test
	void testHashCodeDiffSize() {
		assertEquals(base.hashCode(), basesize.hashCode());
	}
	
	@Test
	void testHashCodeDiffQual() {
		assertEquals(base.hashCode(), baseqs.hashCode());
	}
	
	@Test
	void testHashCodeSame() {
		assertEquals(p1.hashCode(), p1same.hashCode());
	}
	
	@Test
	void testToString() {
		assertEquals("validname:0:ACTIVE", p1.toString());
	}
	
	@Test
	void testToStringChangeStat() {
		p1.setStatus(ProjectStatus.PLANNED);
		assertEquals(name+":"+p1.getWorkers().size()+":PLANNED", p1.toString());
	}

	@Test
	void testAddQualificationDupe() {
		assertFalse(p1.addQualification(qual1));
	}

	@Test
	void testAddQualificationNew() throws NullPointerException, InvalidDescription {
		assertTrue(p1.addQualification(new Qualification("one")));
	}
	
	@Test
	void testAddQualification() {
		assertThrows(NullPointerException.class, () -> p1.addQualification(null));
	}
	
	private Project missProj;
	private Qualification qualA1;
	private Qualification qualA2;
	private Qualification qualA3;
	private HashSet<Qualification> quals1;
	private HashSet<Qualification> quals2;
	private HashSet<Qualification> quals3;
	private HashSet<Qualification> quals12;
	private HashSet<Qualification> allquals;

	private Worker work1;
	private Worker work2;
	private Worker work3;

	@BeforeEach
	void buildQualWorks() throws NullPointerException, InvalidDescription, InvalidName, InvalidQualifications {
		qualA1 = new Qualification("A1");
		qualA2 = new Qualification("A2");
		qualA3 = new Qualification("A3");
		quals12 = new HashSet<>();
		quals12.add(qualA1);
		quals12.add(qualA2);
		missProj = new Project("missProj", ProjectSize.LARGE, ProjectStatus.PLANNED, quals12);

		allquals = new HashSet<>();
		allquals.add(qualA1);
		allquals.add(qualA2);
		allquals.add(qualA3);
		quals1 = new HashSet<>();
		quals1.add(qualA1);
		quals2 = new HashSet<>();
		quals2.add(qualA2);
		quals3 = new HashSet<>();
		quals3.add(qualA3);

		work1 = new Worker("w1", allquals);
		work2 = new Worker("w2", quals1);
		work3 = new Worker("w3", quals3);
	}
	
	@Test
	void testMissQualNoWork() {
		HashSet<Qualification> missQuals = missProj.missingQualifications();
		assertEquals(quals12, missQuals);
	}
	
	@Test
	void testMissQualSome() {
		missProj.getWorkers().add(work2);
		HashSet<Qualification> missQuals = missProj.missingQualifications();
		assertEquals(quals2, missQuals);
	}
	
	@Test
	void testMissQualAll() {
		missProj.getWorkers().add(work1);
		assertTrue(missProj.missingQualifications().isEmpty());
	}
	
	@Test
	void testIsHelpfulNull() {
		assertThrows(NullPointerException.class, () -> p1.isHelpful(null));
	}

	@Test
	void testIsHelpfulBadWork() {
		assertFalse(missProj.isHelpful(work3));
	}
	
	@Test
	void testIsHelpfulGoodWork() {
		assertTrue(missProj.isHelpful(work1));
	}
	
	@Test
	void testIsHelpfulGoodWorkButTaken() throws NullPointerException, InvalidName, InvalidQualifications {
		missProj.getWorkers().add(work2);
		Worker work1new = new Worker("w1 but new", quals1);
		assertFalse(missProj.isHelpful(work1new));
	}
	
	@Test
	void testIsHelpfulGoodWorkButTakenButHasOther() {
		missProj.getWorkers().add(work2);
		assertTrue(missProj.isHelpful(work1));
	}
}
