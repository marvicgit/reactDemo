import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './categoria.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CategoriaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const categoriaEntity = useAppSelector(state => state.categoria.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoriaDetailsHeading">
          <Translate contentKey="reactDemoApp.categoria.detail.title">Categoria</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoriaEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="reactDemoApp.categoria.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{categoriaEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="reactDemoApp.categoria.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{categoriaEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/categoria" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/categoria/${categoriaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoriaDetail;
