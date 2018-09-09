package edu.colostate.cs.cs414.a1.larkwt;

import java.util.HashSet;

public class Qualification {

	private String description;
	private HashSet<Worker> workers;
	private HashSet<Project> projects;

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

	@Override
	public String toString() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + description.hashCode();
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
		Qualification other = (Qualification) obj;
		if (!description.equals(other.description))
			return false;
		return true;
	}

	/**
	 * Creates a new instance of qualification and sets its description, if the
	 * description is valid. A valid description is a non-empty string that
	 * consists of at least one non-blank character.
	 *
	 * @param description the description of the qualification
	 * @throws InvalidDescription if description is not a non-empty string that
	 * consists of at least one non-blank character.
	 * @throws NullPointerException if description is null
	 * @throws InvalidName
	 */
	public Qualification(String description) throws InvalidDescription, NullPointerException {
		if (description == null) {
			throw new NullPointerException("description can't be null");
		} else if (!isValidName(description)) {
			throw new InvalidDescription("invalid description");
		}
		this.description = description;
		projects = new HashSet<>();
		workers = new HashSet<>();
	}

	/**
	 * Adds the worker w to the Set of workers having the current qualification.
	 *
	 * @param worker the worker
	 * @throws NullPointerException if worker is null
	 */
	public void addWorker(Worker worker) throws NullPointerException {
		if (worker == null) {
			throw new NullPointerException("worker can't be null");
		}
		workers.add(worker);
	}

	/**
	 * Adds the project p to the Set of projects requiring the current
	 * qualification
	 *
	 * @param project the project
	 * @throws NullPointerException if project is null
	 */
	public void addProject(Project project) throws NullPointerException {
		if (project == null) {
			throw new NullPointerException("project can't be null");
		}
		projects.add(project);
	}
}
