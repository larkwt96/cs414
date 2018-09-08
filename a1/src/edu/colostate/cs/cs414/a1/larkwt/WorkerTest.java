package edu.colostate.cs.cs414.a1.larkwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkerTest {
	
	private HashSet<Qualification> validQs;
	private HashSet<Qualification> emptyQs;
	private String validName;
	private Worker worker;

	@BeforeEach
	void setUp() throws NullPointerException, InvalidDescription, InvalidName, InvalidQualifications {
		validQs = new HashSet<Qualification>();
		validQs.add(new Qualification("some qual"));
		validQs.add(new Qualification("some other qual"));
		emptyQs = new HashSet<Qualification>();
		
		validName = "A Valid Name";
		
		worker = new Worker(validName, validQs);
	}
	
	@Test
	void testWorkerNameSet() {
		assertEquals(validName, worker.getName());
	}
	
	@Test
	void testWorkerQsSet() {
		assertEquals(validQs, worker.getQualifications());
	}

	@Test
	void testWorkerInvalidNameEmpty() {
		assertThrows(InvalidName.class, () -> new Worker("", validQs));
	}
	
	@Test
	void testWorkerInvalidNameAllSpace() {
		assertThrows(InvalidName.class, () -> new Worker("     ", validQs));
	}
	
	@Test
	void testWorkerInvalidQual() {
		assertThrows(InvalidQualifications.class, () -> new Worker(validName, emptyQs));
	}

	@Test
	void testWorkerNullName() {
		assertThrows(NullPointerException.class, () -> new Worker(null, validQs));
	}
	@Test
	void testWorkerNullQs() {
		assertThrows(NullPointerException.class, () -> new Worker(validName, null));
	}
		
	@Test
	void testWorkerDefaultSalary() throws NullPointerException, InvalidName, InvalidQualifications {
		Worker w;
		w = new Worker(validName, validQs);
		assertEquals(0, w.getSalary());
	}
	
	@Test
	void testAddQualNull() {
		assertThrows(NullPointerException.class, () -> worker.addQualification(null));
	}

	@Test
	void testAddQualGoodAdd() {
		assertTrue(worker.addQualification(q1));
	}
	
	@Test
	void testAddQualBadAdd() {
		worker.addQualification(q1);
		assertFalse(worker.addQualification(q1));
	}
	
	@Test
	void testAddQualMulti() {
		worker.addQualification(q1);
		assertTrue(worker.addQualification(q2));
	}
	
	@Test
	void testAddQualMultiFail() {
		worker.addQualification(q1);
		worker.addQualification(q2);
		assertFalse(worker.addQualification(q2));
	}
	
	@Test
	void testAddQualVerify() {
		assumeTrue(worker.addQualification(q1));
		assertTrue(worker.getQualifications().contains(q1));
	}
	
	private Worker w1;
	private Worker w1same;
	private Worker w1diff;
	private Worker w1diffset;
	private Qualification q1;
	private Qualification q2;

	@BeforeEach
	void createWorkers() throws NullPointerException, InvalidName, InvalidQualifications, InvalidDescription {
		q1 = new Qualification("q1");
		q2 = new Qualification("q2");
		HashSet<Qualification> qs = new HashSet<>();
		qs.add(q1);
		qs.add(q2);
		HashSet<Qualification> qs2 = new HashSet<>();
		qs2.add(q1);
		w1 = new Worker("w1", qs);
		w1same = new Worker("w1", qs);
		w1diff = new Worker("w1diff", qs);
		w1diffset = new Worker("w1", qs2);
	}

	@Test
	void testEqualsExact() {
		assertEquals(w1, w1);
	}

	@Test
	void testEqualsSame() {
		assertEquals(w1, w1same);
	}

	@Test
	void testEqualsNull() {
		assertNotEquals(w1, null);
	}
	
	@Test
	void testEqualsDiffQual() {
		assertEquals(w1, w1diffset);
	}

	@Test
	void testEqualsDiff() {
		assertNotEquals(w1, w1diff);
	}

	@Test
	void testEqualsNonClass() {
		assertNotEquals(w1, "not w1");
	}

	@Test
	void testHashCodeExact() {
		assertEquals(w1.hashCode(), w1.hashCode());
	}

	@Test
	void testHashCodeSame() {
		assertEquals(w1.hashCode(), w1same.hashCode());
	}

	@Test
	void testHashCodeSameDiffQual() {
		assertEquals(w1.hashCode(), w1diffset.hashCode());
	}
	
	@Test
	void testToString() {
		assertEquals("w1:0:2:"+(float)0, w1.toString());
	}
	
	@Test
	void testToStringSalary() {
		w1.setSalary(100);
		assertEquals("w1:0:2:"+(float)100, w1.toString());
	}
	
	@Test
	void testToStringQuals() throws NullPointerException, InvalidDescription {
		w1.addQualification(new Qualification("new qual"));
		assertEquals("w1:0:3:"+(float)0, w1.toString());
	}
	
	@Test
	void testGetProjects() {
		w1.getProjects();
	}
	
	@Test
	void testAddProjectNull() {
		assertThrows(NullPointerException.class, () -> w1.addProject(null));
	}
	
	@Test
	void testAddProjectNoThrow() throws NullPointerException, InvalidName, InvalidQualifications {
		w1.addProject(new Project("proj", ProjectSize.LARGE, ProjectStatus.ACTIVE, validQs));
	}
	
	@Test
	void testSetCompany() throws NullPointerException, InvalidName {
		// don't have to do this, but I want 100 coverage..
		Company curr = w1.getCompany();
		if (curr == null) {
			curr = new Company("w1");
		}
		w1.setCompany(new Company(curr.getName() + "diff"));
		assertEquals(curr.getName()+"diff", w1.getCompany().getName());
	}
	
	private Worker workOver;
	private Project smallProj1;
	private Project mediumProj1;
	private Project mediumProj2;
	private Project largeProj1;
	private Project largeProj2;
	private Project largeProj3;
	private Project largeProj4;
	private Project largeProj5;

	@BeforeEach
	void buildProjs() throws NullPointerException, InvalidDescription, InvalidName, InvalidQualifications {
		HashSet<Qualification> qs = new HashSet<Qualification>();
		qs.add(new Qualification("q1"));
		qs.add(new Qualification("q2"));
		qs.add(new Qualification("q3"));
		workOver = new Worker("overworker", qs);
		smallProj1 = new Project("smallProj1", ProjectSize.SMALL, ProjectStatus.PLANNED, qs);
		mediumProj1 = new Project("mediumProj1", ProjectSize.MEDIUM, ProjectStatus.PLANNED, qs);
		mediumProj2 = new Project("mediumProj2", ProjectSize.MEDIUM, ProjectStatus.PLANNED, qs);
		largeProj1 = new Project("largeProj1", ProjectSize.LARGE, ProjectStatus.PLANNED, qs);
		largeProj2 = new Project("largeProj2", ProjectSize.LARGE, ProjectStatus.PLANNED, qs);
		largeProj3 = new Project("largeProj3", ProjectSize.LARGE, ProjectStatus.PLANNED, qs);
		largeProj4 = new Project("largeProj4", ProjectSize.LARGE, ProjectStatus.PLANNED, qs);
		largeProj5 = new Project("largeProj5", ProjectSize.LARGE, ProjectStatus.PLANNED, qs);
	}
	
	@Test
	void testWillOverloadFirst() {
		assertFalse(workOver.willOverload(largeProj1));
	}
	
	@Test
	void testWillOverloadWhenOverloaded() {
		workOver.addProject(largeProj1); // 3
		workOver.addProject(largeProj2); // 6
		workOver.addProject(largeProj3); // 9
		workOver.addProject(largeProj4); // 12
		workOver.addProject(largeProj5); // 15
		assertTrue(workOver.willOverload(smallProj1));
	}
	
	@Test
	void testWillOverloadSmallNo() {
		workOver.addProject(largeProj1); // 3
		workOver.addProject(largeProj2); // 6
		workOver.addProject(largeProj3); // 9
		workOver.addProject(mediumProj1); // 11
		assertFalse(workOver.willOverload(smallProj1)); // 12
	}

	@Test
	void testWillOverloadSmallYes() {
		workOver.addProject(largeProj1); // 3
		workOver.addProject(largeProj2); // 6
		workOver.addProject(largeProj3); // 9
		workOver.addProject(largeProj4); // 12
		assertTrue(workOver.willOverload(smallProj1)); // 13
	}
	
	@Test
	void testWillOverloadMediumNo() {
		workOver.addProject(largeProj1); // 3
		workOver.addProject(largeProj2); // 6
		workOver.addProject(largeProj3); // 9
		workOver.addProject(smallProj1); // 10
		assertFalse(workOver.willOverload(mediumProj1)); // 12
	}
	
	@Test
	void testWillOverloadMediumYes() {
		workOver.addProject(largeProj1); // 3
		workOver.addProject(largeProj2); // 6
		workOver.addProject(largeProj3); // 9
		workOver.addProject(mediumProj1); // 11
		assertTrue(workOver.willOverload(mediumProj2)); // 13
	}
	
	@Test
	void testWillOverloadLargeNo() {
		workOver.addProject(largeProj1); // 3
		workOver.addProject(largeProj2); // 6
		workOver.addProject(largeProj3); // 9
		assertFalse(workOver.willOverload(largeProj4)); // 12
	}
	
	@Test
	void testWillOverloadLargeYes() {
		workOver.addProject(largeProj1); // 3
		workOver.addProject(largeProj2); // 6
		workOver.addProject(largeProj3); // 9
		workOver.addProject(smallProj1); // 11
		assertTrue(workOver.willOverload(largeProj4)); // 13
	}
}
