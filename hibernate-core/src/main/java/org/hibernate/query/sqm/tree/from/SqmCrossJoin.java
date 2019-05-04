/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.query.sqm.tree.from;

import org.hibernate.metamodel.model.domain.spi.EntityTypeDescriptor;
import org.hibernate.query.criteria.PathException;
import org.hibernate.query.sqm.consume.spi.SemanticQueryWalker;
import org.hibernate.query.sqm.tree.SqmJoinType;
import org.hibernate.query.sqm.tree.domain.AbstractSqmFrom;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.domain.SqmTreatedCrossJoin;
import org.hibernate.type.descriptor.java.spi.JavaTypeDescriptor;

import static org.hibernate.query.sqm.produce.SqmCreationHelper.buildRootNavigablePath;

/**
 * @author Steve Ebersole
 */
public class SqmCrossJoin<T> extends AbstractSqmFrom<T,T> implements SqmJoin<T,T> {
	private final SqmRoot sqmRoot;

	public SqmCrossJoin(
			EntityTypeDescriptor<T> joinedEntityDescriptor,
			String alias,
			SqmRoot sqmRoot) {
		super(
				buildRootNavigablePath( alias, joinedEntityDescriptor.getEntityName() ),
				joinedEntityDescriptor,
				sqmRoot,
				alias,
				sqmRoot.nodeBuilder()
		);
		this.sqmRoot = sqmRoot;
	}

	public SqmRoot getRoot() {
		return sqmRoot;
	}

	@Override
	public SqmPath getLhs() {
		// a cross-join has no LHS
		return null;
	}

	@Override
	public EntityTypeDescriptor<T> getReferencedNavigable() {
		return (EntityTypeDescriptor<T>) super.getReferencedNavigable();
	}

	public String getEntityName() {
		return getReferencedNavigable().getEntityName();
	}

	@Override
	public SqmJoinType getSqmJoinType() {
		return SqmJoinType.CROSS;
	}

	@Override
	public <X> X accept(SemanticQueryWalker<X> walker) {
		return walker.visitCrossJoin( this );
	}

	@Override
	public JavaTypeDescriptor<T> getJavaTypeDescriptor() {
		return getReferencedNavigable().getJavaTypeDescriptor();
	}

	@Override
	public SqmRoot findRoot() {
		return getRoot();
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// JPA

	@Override
	public <S extends T> SqmTreatedCrossJoin<T,S> treatAs(Class<S> treatJavaType) throws PathException {
		final EntityTypeDescriptor<S> treatTarget = nodeBuilder().getDomainModel().entity( treatJavaType );
		return new SqmTreatedCrossJoin<>( this, null, treatTarget );
	}
}