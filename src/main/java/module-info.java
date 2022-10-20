import se.uu.ub.cora.storage.RecordStorageInstanceProvider;

/**
 * The storage module provides interfaces and access needed to store data in a Cora based system.
 */
module se.uu.ub.cora.storage {
	requires transitive se.uu.ub.cora.data;
	requires se.uu.ub.cora.initialize;

	uses RecordStorageInstanceProvider;

	exports se.uu.ub.cora.storage;
	exports se.uu.ub.cora.storage.archive;
	exports se.uu.ub.cora.storage.idgenerator;
}