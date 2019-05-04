/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.query.sqm.tree.domain;

import org.hibernate.metamodel.model.domain.spi.Navigable;
import org.hibernate.query.NavigablePath;
import org.hibernate.query.sqm.NodeBuilder;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractSqmSimplePath<T> extends AbstractSqmPath<T> implements SqmNavigableReference<T> {
	private final NavigablePath navigablePath;

	public AbstractSqmSimplePath(
			NavigablePath navigablePath,
			Navigable<T> referencedNavigable,
			SqmPath lhs,
			NodeBuilder nodeBuilder) {
		this( navigablePath, referencedNavigable, lhs, null, nodeBuilder );
	}

	public AbstractSqmSimplePath(
			NavigablePath navigablePath,
			Navigable<T> referencedNavigable,
			SqmPath lhs,
			String explicitAlias,
			NodeBuilder nodeBuilder) {
		super( referencedNavigable, lhs, nodeBuilder );
		this.navigablePath = navigablePath;

		setExplicitAlias( explicitAlias );
	}

	@Override
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}
}