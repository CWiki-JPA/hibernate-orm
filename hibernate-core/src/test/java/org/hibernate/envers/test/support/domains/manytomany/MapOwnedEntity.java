/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.test.support.domains.manytomany;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 * Many-to-many not-owning entity
 *
 * @author Adam Warski (adam at warski dot org)
 */
@Entity
@Table(name = "MapOwned")
public class MapOwnedEntity {
	@Id
	private Integer id;

	@Audited
	private String data;

	@Audited
	@ManyToMany(mappedBy = "references")
	private Set<MapOwningEntity> referencing;

	public MapOwnedEntity() {
	}

	public MapOwnedEntity(Integer id, String data) {
		this.id = id;
		this.data = data;
	}

	public MapOwnedEntity(String data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Set<MapOwningEntity> getReferencing() {
		return referencing;
	}

	public void setReferencing(Set<MapOwningEntity> referencing) {
		this.referencing = referencing;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		MapOwnedEntity that = (MapOwnedEntity) o;
		return Objects.equals( id, that.id ) &&
				Objects.equals( data, that.data );
	}

	@Override
	public int hashCode() {
		return Objects.hash( id, data );
	}

	@Override
	public String toString() {
		return "MapOwnedEntity{" +
				"id=" + id +
				", data='" + data + '\'' +
				'}';
	}
}