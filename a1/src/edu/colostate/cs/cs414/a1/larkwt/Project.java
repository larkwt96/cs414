package edu.colostate.cs.cs414.a1.larkwt;

import java.util.HashSet;

public class Project {
	
	private String name;
	private ProjectSize size;
	private ProjectStatus status;
	private HashSet<Qualification> qs;
	private HashSet<Worker> workers;

	/**
	 * Creates an instance of a project given a valid name, size, status, and
	 * qualifications. A name is valid if it is a non-empty string that consists
	 * of at least one non-blank character.
	 * 
	 * @param name the name of the project
	 * @param size the size of the project
	 * @param status the status of the project
	 * @param qs the qualifications required by the project
	 * @throws NullPointerException if name or qs are null
	 * @throws InvalidName if name is not a non-empty string that consists of at
	 * least one non-blank character
	 * @throws InvalidQualifications if qs is empty
	 */
	public Project(String name, ProjectSize size, ProjectStatus status, HashSet<Qualification> qs) 
			throws NullPointerException, InvalidName, InvalidQualifications {
		if (name == null) {
			throw new NullPointerException("name must be non-null");
		} else if (!Company.isValidName(name)) {
			throw new InvalidName("name is invalid");
		}
		if (qs == null) {
			throw new NullPointerException("qs must be non-null");
		} else if (qs.isEmpty()) {
			throw new InvalidQualifications("qs is empty");
		}
		this.name = name;
		this.size = size;
		this.status = status;
		this.qs = new HashSet<Qualification>(qs);
		this.workers = new HashSet<Worker>();
	}

	public String getName() {
		return name;
	}

	public ProjectSize getSize() {
		return size;
	}

	public ProjectStatus getStatus() {
		return status;
	}
	
	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name+":"+workers.size()+":"+status;
	}
	
	/**
	 * Returns the qualifications that are not met by comparing the
	 * qualifications required by the project and those that are met by the
	 * workers who are assigned to the project. An empty set (not a null set) is
	 * returned when all the qualification requirements are met.
	 * 
	 * @return the set of project qualifications not met by its assigned workers
	 */
	public HashSet<Qualification> missingQualifications() {
		HashSet<Qualification> required = new HashSet<Qualification>();
		for (Qualification q : qs) {
			boolean found = false;
			for (Worker w : workers) {
				if (w.getQualifications().contains(q)) {
					found = true;
					break;
				}
			}
			if (!found) {
				required.add(q);
			}
		}
		return required;
	}
	
	/**
	 * Verifies that at least one of the missing qualification requirements of a
	 * project is satisfied by the worker w.
	 * 
	 * @param w the worker to be analyzed
	 * @return true if at least one of the missing qualification requirements of
	 * a project is satisfied by the worker, false otherwise
	 * @throws NullPointerException if w is null
	 */
	public boolean isHelpful(Worker w) throws NullPointerException {
		if (w == null) {
			throw new NullPointerException("w can't be null");
		}
		for (Qualification q : missingQualifications()) {
			if (w.getQualifications().contains(q)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns the workers assigned to the project
	 * 
	 * @return the workers assigned to the project
	 */
	public HashSet<Worker> getWorkers() {
		return workers;
	}
	
	/**
	 * Adds a qualification q to the set of required qualifications of the
	 * project.
	 * 
	 * @param q the qualification to be added
	 * @return true if the qualification is added; false otherwise
	 * @throws NullPointerException if q is null
	 */
	public boolean addQualification(Qualification q) throws NullPointerException {
		if (q == null) {
			throw new NullPointerException("q can't be null");
		}
		return qs.add(q);
	}
}

