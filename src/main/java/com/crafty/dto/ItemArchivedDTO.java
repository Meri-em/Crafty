package com.crafty.dto;

import java.io.Serializable;

public class ItemArchivedDTO implements Serializable {

	private static final long serialVersionUID = 7337710914023082135L;

	private boolean archived;

	public boolean getArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
}
