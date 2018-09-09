package edu.colostate.cs.cs414.a1.larkwt;

import java.util.HashSet;

public class Worker {

	private String name;
	private double salary;
	private HashSet<Qualification> qs;
	private HashSet<Project> projects;
	private Company company;

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

	/**
	 * Creates a new worker with the given a valid name and qualifications. A
	 * name is valid if it is a non-empty string that consists of at least one
	 * non-blank character. Default salary is 0.
	 *
	 * @param name the name of the worker
	 * @param qs the worker's qualifications
	 * @throws NullPointerException if name or qs are null
	 * @throws InvalidName if name is not a non-empty string that consists of at least one non-blank character.
	 * @throws InvalidQualifications if qs is empty
	 */
	public Worker(String name, HashSet<Qualification> qs) throws
			NullPointerException,
			InvalidName,
			InvalidQualifications {
		if (name == null) {
			throw new NullPointerException("name must be non-null");
		} else if (!isValidName(name)) {
			throw new InvalidName("name is invalid");
		} else if (qs == null) {
			throw new NullPointerException("qs must be non-null");
		} else if (qs.isEmpty()) {
			throw new InvalidQualifications("qs can't be empty");
		}
		this.name = name;
		this.qs = new HashSet<Qualification>(qs);
		this.projects = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public HashSet<Qualification> getQualifications() {
		return qs;
	}

	/**
	 * Adds the qualification q to the set of qualifications of the worker
	 *
	 * @param q the qualification to be added
	 * @return true if the qualification was added; false otherwise
	 * @throws NullPointerException if q is null
	 */
	public boolean addQualification(Qualification q) throws NullPointerException {
		if (q == null) {
			throw new NullPointerException("q can't be null");
		}
		return qs.add(q);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		return result;
	}

	@Override
	/**
	 * This operation is needed by JUnit. Note that the parameter of this method
	 * is of type Object, i.e., not equals(c : Company), etc. Two Worker
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
		Worker other = (Worker) obj;
		if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	/**
	 * Returns a String that concatenates the name, colon, number of projects,
	 * colon, number of qualifications, colon, salary. For example, a worker
	 * named "Nick", working on 2 projects, and having 10 qualifications and a
	 * salary of 10000 will result in the string Nick:2:10:10000.
	 */
	public String toString() {
		return name + ":" + projects.size() + ":" + qs.size() + ":" + salary;
	}

	private int computeCurrentLoad() {
		int size = 0;
		for (Project p : getProjects()) {
			size += getProjectLoad(p.getSize());
		}
		return size;
	}

	private int getProjectLoad(ProjectSize size) {
		switch(size) {
		case LARGE: return 3;
		case MEDIUM: return 2;
		case SMALL: return 1;
		default: return 0; // null case
		}
	}

	/**
	 * Verifies if the worker will overload by assigning him to a project p. A
	 * constraint for the entire system is that no worker should ever be
	 * overloaded. To determine overloading, consider all the ACTIVE projects of
	 * the worker. If adding a new project p to the current project set of the
	 * worker makes (3*numberOfLargeProjects + 2*numberOfMediumProjects +
	 * numberOfSmall Projects) greater than 12 when p becomes active, then the
	 * worker will be overloaded.
	 *
	 * It's not clear whether "the current project set" from "adding a new
	 * project p to the current project set" is referring to active project set.
	 * If it's not, then we can overwork a worker by assigning it to 5 large
	 * projects and starting them all. Since none were "active" during
	 * assignment, overworked isn't triggered. Starting them doesn't perform any
	 * checks for overworking. As such, I will assume that the project set
	 * refers to all projects active and not.
	 *
	 * @param p the project
	 * @return true if a worker will be overloaded when assigned to the project
	 * p; false otherwise
	 */
	public boolean willOverload(Project p) {
		ProjectSize pSize = (p == null ? null : p.getSize());
		return getProjectLoad(pSize) + computeCurrentLoad() > 12;
	}

	public HashSet<Project> getProjects() {
		return projects;
	}

	/**
	 * Adds the project p to the set of projects of the worker.
	 *
	 * @param p the project
	 * @throws NullPointerException if p is null
	 */
	public void addProject(Project p) throws NullPointerException {
		if (p == null) {
			throw new NullPointerException("p can't be null");
		}
		projects.add(p);
	}

	/**
	 * Sets the Company field
	 *
	 * @param c the company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Returns the company
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}
}
