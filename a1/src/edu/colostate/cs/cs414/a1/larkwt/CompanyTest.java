package edu.colostate.cs.cs414.a1.larkwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyTest {

	Company same1;
	Company same2;
	Company diff1;

	Worker w1;
	Worker w2;

	HashSet<Qualification> qs_valid;
	HashSet<Qualification> qs_empty;

	@BeforeEach
	void buildCompany() throws NullPointerException, InvalidName, InvalidDescription, InvalidQualifications {
		same1 = new Company("same");
		same2 = new Company("same");
		diff1 = new Company("diff");
		qs_valid = new HashSet<Qualification>();
		qs_valid.add(new Qualification("qual1"));
		qs_valid.add(new Qualification("qual2"));
		qs_empty = new HashSet<Qualification>();
		w1 = new Worker("bill", qs_valid);
		w2 = new Worker("tom", qs_valid);
	}

	@Test
	void testConstructorValidName() throws NullPointerException, InvalidName {
		String validName = "ValidName";
		Company company = null;
		company = new Company(validName);
		assertEquals(validName, company.getName());
	}

	@Test
	void testConstructorEmptyName() {
		assertThrows(InvalidName.class, () -> new Company(""));
	}

	@Test
	void testConstructorOnlySpacesName() {
		assertThrows(InvalidName.class, () -> new Company(" \t   "));
	}

	@Test
	void testConstructorNullName() {
		assertThrows(NullPointerException.class, () -> new Company(null));
	}

	@Test
	void testEquals() {
		assertEquals(same1, same2);
	}

	@Test
	void testEqualsFails() {
		assertNotEquals(same2, diff1);
	}

	@Test
	void testEqualsNull() {
		assertNotEquals(same1, null);
	}

	@Test
	void testEqualsSame() {
		assertEquals(same1, same1);
	}

	@Test
	void testEqualsDiffClass() {
		assertNotEquals(same1, "Not a company");
	}

	@Test
	void testHashCodeEquals() {
		assertEquals(same1.hashCode(), same2.hashCode());
	}

	@Test
	void testToStringEmptyCompany() {
		assertEquals("same:0:0", same1.toString());
	}

	@Test
	void testToStringWorkersAndFinishedProjects() {
		comp.addToAvailableWorkerPool(work1);
		comp.addToAvailableWorkerPool(work2);
		comp.addToAvailableWorkerPool(work3);
		comp.addToAvailableWorkerPool(work5);
		assertEquals("comp:4:6", comp.toString());
	}

	@Test
	void testAddToAvailableWorkerPoolReturnValueTrue() {
		assumeFalse(same1.getAvailableWorkers().contains(w1));
		assertTrue(same1.addToAvailableWorkerPool(w1));
	}

	@Test
	void testAddToAvailableWorkerPoolReturnValueFalse() {
		assumeFalse(same1.getAvailableWorkers().contains(w1));
		same1.addToAvailableWorkerPool(w1);
		assertFalse(same1.addToAvailableWorkerPool(w1));
	}

	@Test
	void testAddToAvailableWorkerPoolBase() {
		assumeFalse(same1.getAvailableWorkers().contains(w1));
		same1.addToAvailableWorkerPool(w1);
		assertTrue(same1.getAvailableWorkers().contains(w1));
	}

	@Test
	void testAddToAvailableWorkerPoolDuplicate() {
		same1.addToAvailableWorkerPool(w1);
		assertFalse(same1.addToAvailableWorkerPool(w1));
	}

	@Test
	void testAddToAvailableWorkerPoolAssigned() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		assertFalse(same1.addToAvailableWorkerPool(work1));
	}

	@Test
	void testAddToAvailableWorkerPoolNull() {
		assertThrows(NullPointerException.class, () -> same1.addToAvailableWorkerPool(null));
	}

	@Test
	void testCreateProjectInvalidName() {
		assertThrows(InvalidName.class, () -> same1.createProject("   ", qs_valid, ProjectSize.SMALL));
	}

	@Test
	void testCreateProjectInvalidQual() {
		assertThrows(InvalidQualifications.class, () -> same1.createProject("validName", qs_empty, ProjectSize.SMALL));
	}

	@Test
	void testCreateProjectPlannedFlag() throws NullPointerException, InvalidName, InvalidQualifications {
		Project p = same1.createProject("name", qs_valid, ProjectSize.SMALL);
		assertEquals(ProjectStatus.PLANNED, p.getStatus());
	}

	@Test
	void testCreateProjectNullName() {
		assertThrows(NullPointerException.class, () -> same1.createProject(null, qs_valid, ProjectSize.MEDIUM));
	}

	@Test
	void testCreateProjectNullQs() {
		assertThrows(NullPointerException.class, () -> same1.createProject("validName", null, ProjectSize.MEDIUM));
	}

	@Test
	void testCreateProjectSizeSetsSmall() throws NullPointerException, InvalidName, InvalidQualifications {
		same1.createProject("name", qs_valid, ProjectSize.SMALL);
		for (Project p : same1.getProjects()) {
			if (p.getName().equals("name")) {
				assertEquals(ProjectSize.SMALL, p.getSize());
				return;
			}
		}
		fail("never created project");
	}

	@Test
	void testCreateProjectSizeSetsLarge() throws NullPointerException, InvalidName, InvalidQualifications {
		same1.createProject("name", qs_valid, ProjectSize.LARGE);
		for (Project p : same1.getProjects()) {
			if (p.getName().equals("name")) {
				assertEquals(ProjectSize.LARGE, p.getSize());
				return;
			}
		}
		fail("never created project");
	}

	private Company comp;
	private Project projsmall1;
	private Project projsmall2;
	private Project projmedium1;
	private Project projlarge1;
	private Project projlarge2;
	private Project projlarge3;
	private Worker work1;
	private Worker work12;
	private Worker work2;
	private Worker work3;
	private Worker work5;
	private HashSet<Qualification> qs;

	@BeforeEach
	void setupAssignment() throws NullPointerException, InvalidName, InvalidQualifications, InvalidDescription {
		comp = new Company("comp");

		qs = new HashSet<>();
		Qualification q1 = new Qualification("q1");
		Qualification q2 = new Qualification("q2");
		Qualification q3 = new Qualification("q3");
		qs.add(q1);
		qs.add(q2);
		qs.add(q3);

		projsmall1 = comp.createProject("projsmall1", qs, ProjectSize.SMALL);
		projsmall2 = comp.createProject("projsmall2", qs, ProjectSize.SMALL);
		projmedium1 = comp.createProject("projmedium1", qs, ProjectSize.MEDIUM);
		projlarge1 = comp.createProject("projlarge1", qs, ProjectSize.LARGE);
		projlarge2 = comp.createProject("projlarge2", qs, ProjectSize.LARGE);
		projlarge3 = comp.createProject("projlarge3", qs, ProjectSize.LARGE);

		HashSet<Qualification> work1qs = new HashSet<Qualification>();
		work1qs.add(q1);
		work1 = new Worker("w1", work1qs);
		work12 = new Worker("w12", work1qs);


		HashSet<Qualification> work2qs = new HashSet<Qualification>();
		work2qs.add(q2);
		work2 = new Worker("w2", work2qs);

		HashSet<Qualification> work3qs = new HashSet<Qualification>();
		work3qs.add(q3);
		work3 = new Worker("w3", work3qs);

		work5 = new Worker("w5", qs);
	}

	@Test
	void testAssignWorkerNotInAssigned() {
		assertFalse(comp.assign(work1, projlarge1));
	}

	@Test
	void testAssignWorkerNull() {
		assertThrows(NullPointerException.class, () -> comp.assign(null, projsmall1));
	}

	@Test
	void testAssignProcjectNull() {
		comp.addToAvailableWorkerPool(work1);
		assertThrows(NullPointerException.class, () -> comp.assign(work1, null));
	}

	@Test
	void testAssignWorkerAlreadyAssignedToProj() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		assertFalse(comp.assign(work1, projsmall1));
	}

	@Test
	void testAssignProjectIsActive() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		comp.addToAvailableWorkerPool(work2);
		comp.assign(work2, projsmall1);
		comp.addToAvailableWorkerPool(work3);
		comp.assign(work3, projsmall1);
		comp.start(projsmall1);
		assertEquals(ProjectStatus.ACTIVE, projsmall1.getStatus());
	}

	@Test
	void testAssignAlreadyActive() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		comp.assign(work1, projsmall1);
	}

	@Test
	void testAssignAlreadyActiveManualSet() {
		comp.addToAvailableWorkerPool(work1);
		projsmall1.setStatus(ProjectStatus.ACTIVE);
		comp.assign(work1, projsmall1);
	}

	@Test
	void testAssignProjectIsFinished() {
		comp.addToAvailableWorkerPool(work1);
		projsmall1.setStatus(ProjectStatus.FINISHED);
		assertFalse(comp.assign(work1, projsmall1));
	}

	@Test
	void testAssignWorkerOverloads() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projlarge1); // 3
		comp.assign(work5, projlarge2); // 6
		comp.assign(work5, projlarge3); // 9
		comp.assign(work5, projmedium1); // 11
		comp.assign(work5, projsmall1); // 12
		assertFalse(comp.assign(work5, projsmall2));
	}

	@Test
	void testAssignWorkerNotHelpful() {
		comp.addToAvailableWorkerPool(work1);
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		assertFalse(comp.assign(work1, projsmall1));
	}

	@Test
	void testAssignWorkerNotHelpfulTaskDone() {
		comp.addToAvailableWorkerPool(work1);
		comp.addToAvailableWorkerPool(work12);
		comp.assign(work1, projsmall1);
		assertFalse(comp.assign(work12, projsmall1));
	}

	@Test
	void testAssignWorkerInAssigned() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		assertTrue(comp.getAssignedWorkers().contains(work1));
	}

	@Test
	void testAssignWorkerInAvailable() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		assertTrue(comp.getAvailableWorkers().contains(work1));
	}

	@Test
	void testAssignWorkerIsInProj() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		assertTrue(projsmall1.getWorkers().contains(work1));
	}

	@Test
	void testAssignMultiAddStillInAssigned() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projlarge1);
		comp.assign(work5, projlarge2);
		assertTrue(comp.getAssignedWorkers().contains(work5));
	}

	@Test
	void testAssignMultiWorkerHasBothProjs() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projlarge1);
		comp.assign(work5, projlarge2);
		assertTrue(work5.getProjects().contains(projlarge1));
		assertTrue(work5.getProjects().contains(projlarge2));
	}

	@Test
	void testAssignProjectToWorker() {
		// I'm not sure if this is required, but I'm considering it to be a
		// ramification of "assign worker to project"
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		assertTrue(work1.getProjects().contains(projsmall1));
	}

	@Test
	void testAssignMissingReqIsNowChanged() {
		comp.addToAvailableWorkerPool(work1);
		int before = projsmall1.missingQualifications().size();
		comp.assign(work1, projsmall1);
		assertNotEquals(before, projsmall1.missingQualifications().size());
	}

	@Test
	void testStartUmetQual() {
		assertFalse(comp.start(projsmall1));
	}

	@Test
	void testStartSuspended() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		projsmall1.setStatus(ProjectStatus.SUSPENDED);
		assertTrue(comp.start(projsmall1));
	}

	@Test
	void testStartActive() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		assertFalse(comp.start(projsmall1));
	}

	@Test
	void testStartSetsActive() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		assertEquals(ProjectStatus.ACTIVE, projsmall1.getStatus());
	}

	@Test
	void testStartFinished() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		projsmall1.setStatus(ProjectStatus.FINISHED);
		assertFalse(comp.start(projsmall1));
	}

	@Test
	void testStartUnmetStaysSuspended() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		projsmall1.setStatus(ProjectStatus.SUSPENDED);
		comp.start(projsmall1);
		assertEquals(ProjectStatus.SUSPENDED, projsmall1.getStatus());
	}

	@Test
	void testStartUnmetStaysPlanned() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		comp.start(projsmall1);
		assertEquals(ProjectStatus.PLANNED, projsmall1.getStatus());
	}

	@Test
	void testStartMetQual() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		assertTrue(comp.start(projsmall1));
	}

	@Test
	void testStartPartMetQual() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		assertFalse(comp.start(projsmall1));
	}

	@Test
	void testStartNull() {
		assertThrows(NullPointerException.class, () -> comp.start(null));
	}

	@Test
	void testUnassignPRemovedFromW() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.unassign(work5, projsmall1);
		assertFalse(work5.getProjects().contains(projsmall1));
	}

	@Test
	void testUnassignWRemovedFromP() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.unassign(work5, projsmall1);
		assertFalse(projsmall1.getWorkers().contains(work5));
	}

	@Test
	void testUnassignWNotAssignedToP() {
		comp.addToAvailableWorkerPool(work5);
		assertFalse(comp.unassign(work5, projsmall1));
	}

	@Test
	void testUnassignSoloWorker() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.unassign(work5, projsmall1);
		assertFalse(comp.getAssignedWorkers().contains(work5));
	}

	@Test
	void testUnassignMultiWorker() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.assign(work5, projsmall2);
		comp.unassign(work5, projsmall1);
		assertTrue(comp.getAssignedWorkers().contains(work5));
	}

	@Test
	void testUnassignPreserveActive() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		comp.unassign(work1,  projsmall1);
		assertEquals(ProjectStatus.ACTIVE, projsmall1.getStatus());
	}

	@Test
	void testUnassignLoseActive() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		comp.unassign(work5,  projsmall1);
		assertEquals(ProjectStatus.SUSPENDED, projsmall1.getStatus());
	}

	@Test
	void testUnassignSuspendPreserve() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		comp.unassign(work5,  projsmall1);
		comp.unassign(work1, projsmall1);
		assertEquals(ProjectStatus.SUSPENDED, projsmall1.getStatus());
	}

	@Test
	void testUnassignPlannedPreserve() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		comp.unassign(work1, projsmall1);
		assertEquals(ProjectStatus.PLANNED, projsmall1.getStatus());
	}

	@Test
	void testUnassignNullWork() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		assertThrows(NullPointerException.class, () -> comp.unassign(null, projsmall1));
	}

	@Test
	void testUnassignNullProj() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		assertThrows(NullPointerException.class, () -> comp.unassign(work1, null));
	}

	@Test
	void testUnassignAssginedFromDiffComp() throws NullPointerException, InvalidName, InvalidQualifications {
		Company comp2 = new Company("comp2");
		comp2.addToAvailableWorkerPool(work1);
		Project proj1 = comp2.createProject("proj1", qs, ProjectSize.LARGE);
		comp2.assign(work1, proj1);
		comp.unassign(work1, proj1);
		assertTrue(work1.getProjects().contains(proj1));
		assertTrue(proj1.getWorkers().contains(work1));
		assertTrue(comp2.getAssignedWorkers().contains(work1));
	}

	@Test
	void testFinishSuspendFail() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		comp.unassign(work5, projsmall1);
		assertFalse(comp.finish(projsmall1));
	}

	@Test
	void testFinishPlannedFail() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		assertFalse(comp.finish(projsmall1));
	}

	@Test
	void testFinishFinishedFail() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		comp.finish(projsmall1);
		assertFalse(comp.finish(projsmall1));
	}

	@Test
	void testFinishSuspendStaysAsIs() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		comp.unassign(work5, projsmall1);
		comp.finish(projsmall1);
		assertEquals(ProjectStatus.SUSPENDED, projsmall1.getStatus());
	}

	@Test
	void testFinishPlannedStaysAsIs() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.finish(projsmall1);
		assertEquals(ProjectStatus.PLANNED, projsmall1.getStatus());
	}

	@Test
	void testFinishAreAssignedWorkersRemoved() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		comp.finish(projsmall1);
		assertTrue(projsmall1.getWorkers().isEmpty());
	}

	@Test
	void testFinishSoloAssignedWorkersRemovedAssigned() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.assign(work5, projsmall2);
		comp.start(projsmall1);
		comp.finish(projsmall1);
		assertTrue(projsmall2.getWorkers().contains(work5));
		assertTrue(work5.getProjects().contains(projsmall2));
		assertTrue(comp.getAssignedWorkers().contains(work5));
	}

	@Test
	void testFinishNull() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		assertThrows(NullPointerException.class, () -> comp.finish(null));
	}

	@Test
	void testFinishSuccessTrue() {
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		assertTrue(comp.finish(projsmall1));
	}

	@Test
	void testUnassignAllWorkerNoProj() {
		comp.addToAvailableWorkerPool(work1);
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work1, projsmall1);
		comp.assign(work1, projsmall2);
		comp.assign(work1, projlarge1);
		comp.assign(work5, projsmall1);
		comp.unassignAll(work1);
		assertTrue(work1.getProjects().isEmpty());
	}

	@Test
	void testUnassignAllProjNoWorker() {
		comp.addToAvailableWorkerPool(work1);
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work1, projsmall1);
		comp.assign(work1, projsmall2);
		comp.assign(work1, projlarge1);
		comp.assign(work5, projsmall1);
		comp.unassignAll(work1);
		assertFalse(projsmall1.getWorkers().contains(work1));
		assertFalse(projsmall2.getWorkers().contains(work1));
	}

	@Test
	void testUnassignAllStillAssgined() {
		comp.addToAvailableWorkerPool(work1);
		comp.assign(work1, projsmall1);
		comp.unassignAll(work1);
		assertFalse(comp.getAssignedWorkers().contains(work1));
	}

	@Test
	void testUnassignedAllSuspActive() {
		comp.addToAvailableWorkerPool(work1);
		comp.addToAvailableWorkerPool(work2);
		comp.addToAvailableWorkerPool(work3);
		comp.addToAvailableWorkerPool(work5);
		comp.assign(work1, projsmall1);
		comp.assign(work1, projsmall2);
		comp.assign(work2, projsmall2);
		comp.assign(work3, projsmall2);
		comp.assign(work5, projsmall1);
		comp.start(projsmall1);
		comp.start(projsmall2);
		comp.unassignAll(work1);
		assertEquals(ProjectStatus.ACTIVE, projsmall1.getStatus());
		assertEquals(ProjectStatus.SUSPENDED, projsmall2.getStatus());
	}

	@Test
	void testUnassignAllNull() {
		assertThrows(NullPointerException.class, () -> comp.unassignAll(null));
	}
}
