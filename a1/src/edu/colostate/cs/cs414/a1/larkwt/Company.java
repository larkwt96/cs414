package edu.colostate.cs.cs414.a1.larkwt;

import java.util.ArrayList;
import java.util.HashSet;

public class Company {

	private String name;
	private HashSet<Worker> availableWorkers;
	private HashSet<Worker> assignedWorkers;
	private HashSet<Worker> unassignedWorkers;
	private HashSet<Project> projects;

	/**
	 * Creates a company instance and sets its name. A valid name is a non-empty
	 * string that consists of at least one non-blank character.
	 *
	 * @param name the name of the company
	 * @throws InvalidName if name is not a non-empty string that consists of at least one non-blank character.
	 * @throws NullPointerException if name is null
	 */
	public Company(String name) throws InvalidName, NullPointerException {
		if (name == null) {
			throw new NullPointerException("name must not be null");
		}
		if (!isValidName(name)) {
			throw new InvalidName("name must be non-empty with at least one non-blank character");
		}
		this.name = name;
		availableWorkers = new HashSet<Worker>();
		assignedWorkers = new HashSet<Worker>();
		unassignedWorkers = new HashSet<Worker>();
		projects = new HashSet<Project>();
	}

	/**
	 * Returns the name of the company
	 *
	 * @return the name of the company
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the set of available workers
	 *
	 * @return returns the set of available workers
	 */
	public HashSet<Worker> getAvailableWorkers() {
		return availableWorkers;
	}

	/**
	 * Returns the set of assigned workers
	 *
	 * @return returns the set of assigned workers
	 */
	public HashSet<Worker> getAssignedWorkers() {
		return assignedWorkers;
	}

	/**
	 * Returns the set of unassigned workers
	 *
	 * @return returns the set of unassigned workers
	 */
	public HashSet<Worker> getUnassignedWorkers() {
		return unassignedWorkers;
	}

	public HashSet<Project> getProjects() {
		return projects;
	}

	@Override
	/**
	 *
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		return result;
	}

	@Override
	/**
	 * This operation is needed by JUnit. Note that the parameter of this method
	 * is of type Object, i.e., not equals(c : Company), etc. Two Company
	 * instances are equal iff their names match. Note that it is good practice
	 * to override the hashCode method when equals is overridden.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	/**
	 * Returns a String concatenating company name, colon, number of available
	 * workers, colon, number of projects carried out. For example, a company
	 * named ABC that has 20 available workers and 10 projects will result in
	 * the string ABC:20:10.
	 */
	public String toString() {
		return name + ":" + availableWorkers.size() + ":" + projects.size();
	}

	/**
	 * Adds a worker w to the pool of available workers, if the worker is not
	 * already there and if the worker has no assigned projects.
	 *
	 * @param w the worker
	 * @return true if the worker was added, else false
	 * @throws NullPointerException if w is null
	 */
	public boolean addToAvailableWorkerPool(Worker worker) throws NullPointerException {
		if (worker == null) {
			throw new NullPointerException("worker must be non null");
		} else if (!worker.getProjects().isEmpty()) {
			return false;
		}
		return availableWorkers.add(worker);
	}

	/**
	 * Creates a new project with the given a valid name, qualifications and
	 * size, and marks the project as PLANNED. A valid name is a non-empty
	 * string that consists of at least one non-blank character. Adds the new
	 * project to the projects carried out by the company. Adds each
	 * qualification in qs to the project’s qualifications.
	 *
	 * @param n name of the project
	 * @param qs the set of qualifications required by the project
	 * @param size the size of the project
	 * @return the new project
	 * @throws NullPointerException if n or qs are null
	 * @throws InvalidName if n is a non-empty string that consists of at least
	 * one non-blank character.
	 * @throws InvalidQualifications if qs is empty
	 */
	public Project createProject(String n, HashSet<Qualification> qs, ProjectSize size)
			throws NullPointerException, InvalidName, InvalidQualifications {
		if (n == null) {
			throw new NullPointerException("n must be non-null");
		} else if (qs == null) {
			throw new NullPointerException("qs must be non-null");
		}
		Project project = new Project(n, size, ProjectStatus.PLANNED, qs);
		projects.add(project);
		return project;
	}

	/**
	 * Assigns a worker w to the project p. Only a worker from the pool of
	 * available workers can be assigned to a project, as long as the worker is
	 * not already assigned to the same project. The project must not be in the
	 * ACTIVE or FINISHED state. The worker should not get overload by the
	 * assignment. The worker can be added iff the worker is helpful to the
	 * project. If all the conditions are satisfied, (i) the assigned worker is
	 * added to the pool of assigned workers of the company unless the worker is
	 * already present in that pool, and (ii) the worker is also added to the
	 * project. This results in at least one previously unmet required
	 * qualification of the project being met. Note that the same worker can be
	 * in both the available pool and assigned pool of workers at the same time.
	 * However, the worker cannot be in the assigned pool if the worker is not
	 * in the available pool. Think of the available pool as the pool of
	 * employed workers.
	 *
	 * @param w the worker to be assigned
	 * @param p the project to assign
	 * @return true if the worker was assigned to the project; false otherwise
	 * @throws NullPointerException if w or p are null
	 */
	public boolean assign(Worker w, Project p) throws NullPointerException {
		if (w == null) {
			throw new NullPointerException("w can't be null");
		} else if (p == null) {
			throw new NullPointerException("w can't be null");
		} else if (!getAvailableWorkers().contains(w)
				|| p.getWorkers().contains(w)
				|| p.getStatus() == ProjectStatus.ACTIVE
				|| p.getStatus() == ProjectStatus.FINISHED
				|| !p.isHelpful(w)
				|| w.willOverload(p)) {
			return false;
		}
		getUnassignedWorkers().remove(w);
		assignedWorkers.add(w);
		p.getWorkers().add(w);
		w.addProject(p);
		return true;
	}

	/**
	 * Removes a worker w from the project p. A worker must have been assigned
	 * to a project to be unassigned from it. If this was the only project for
	 * the worker, then this worker needs to be removed from the pool of
	 * assigned workers of the company. If the qualification requirements of an
	 * ACTIVE project are no longer met, that project is marked as SUSPENDED. A
	 * PLANNED or SUSPENDED project remains in that state.
	 *
	 * @param w the worker to be unassigned
	 * @param p the project to unassign
	 * @return true if the worker was unassigned from the project; false otherwise
	 * @throws NullPointerException if w or p are null
	 */
	public boolean unassign(Worker w, Project p) throws NullPointerException {
		if (w == null) {
			throw new NullPointerException("w can't be null");
		} else if (p == null) {
			throw new NullPointerException("p can't be null");
		} else if (!p.getWorkers().contains(w)
				|| !w.getProjects().contains(p)
				|| !getAssignedWorkers().contains(w)) {
			return false;
		}
		p.getWorkers().remove(w);
		w.getProjects().remove(p);
		if (w.getProjects().isEmpty()) {
			getAssignedWorkers().remove(w);
			getUnassignedWorkers().add(w);
		}
		if (p.getStatus() == ProjectStatus.ACTIVE && !p.missingQualifications().isEmpty()) {
			p.setStatus(ProjectStatus.SUSPENDED);
		}
		return true;
	}

	/**
	 * Removes a worker w from all the projects that are assigned to the worker.
	 * It also removes the worker from the pool of assigned workers of the
	 * company. The state of the affected projects must be changed as needed.
	 *
	 * @param w the worker to be unassigned
	 * @throws NullPointerException if w is null
	 */
	public void unassignAll(Worker w) throws NullPointerException {
		if (w == null) {
			throw new NullPointerException("w can't be null");
		}
		ArrayList<Project> ps = new ArrayList<>(w.getProjects());
		for (Project p : ps) {
			unassign(w, p);
		}
	}

	/**
	 * Starts a PLANNED or SUSPENDED project as long as the project's
	 * qualification requirements are all satisfied. The project shifts to an
	 * ACTIVE status. Otherwise, the project remains PLANNED or SUSPENDED (i.e.,
	 * as it was before the method was called).
	 *
	 * @param p the project to be started
	 * @return true if the project is successfully started; false otherwise
	 * @throws NullPointerException if p is null
	 */
	public boolean start(Project p) throws NullPointerException {
		if (p == null) {
			throw new NullPointerException("p can't be null");
		} else if (p.getStatus() != ProjectStatus.PLANNED && p.getStatus() != ProjectStatus.SUSPENDED) {
			return false;
		} else if (p.missingQualifications().size() != 0) {
			return false;
		}
		p.setStatus(ProjectStatus.ACTIVE);
		return true;
	}

	/**
	 * Marks an ACTIVE project as FINISHED. The project no longer has any
	 * assigned workers, so if a worker was only involved in this project, the
	 * worker must be removed from the pool of assigned workers of the company.
	 * A SUSPENDED or PLANNED project remains as it was.
	 *
	 * @param p the project to be finished
	 * @return true if the project is successfully finished; false otherwise
	 * @throws NullPointerException if p is null
	 */
	public boolean finish(Project p) throws NullPointerException {
		if (p == null) {
			throw new NullPointerException("p can't be null");
		} else if (p.getStatus() != ProjectStatus.ACTIVE) {
			return false;
		}
		ArrayList<Worker> al = new ArrayList<>(p.getWorkers());
		for (Worker w : al) {
			unassign(w, p);
		}
		p.setStatus(ProjectStatus.FINISHED);
		return true;
	}

	/**
	 * A valid name is non-empty and contains at least one non-whitespace
	 * character.
	 *
	 * @param name the name to check. Assumes is non-null.
	 * @return true if name is valid.
	 */
	private static boolean isValidName(String name) {
		if (name.length() == 0) {
			return false;
		}
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isWhitespace(name.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}