package com.sap.mentors.lemonaid.odata;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPATransaction;

public class NoJPATransaction implements ODataJPATransaction {

	@Override
	public void begin() {
		// Don't do anything
	}

	@Override
	public void commit() {
		// Don't do anything
	}

	@Override
	public void rollback() {
		// Don't do anything
	}

	@Override
	public boolean isActive() {
		return false;
	}

}
