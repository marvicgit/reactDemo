import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './cliente.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClienteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clienteEntity = useAppSelector(state => state.cliente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clienteDetailsHeading">
          <Translate contentKey="reactDemoApp.cliente.detail.title">Cliente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.id}</dd>
          <dt>
            <span id="nombres">
              <Translate contentKey="reactDemoApp.cliente.nombres">Nombres</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.nombres}</dd>
          <dt>
            <span id="primerApellido">
              <Translate contentKey="reactDemoApp.cliente.primerApellido">Primer Apellido</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.primerApellido}</dd>
          <dt>
            <span id="segundoApellido">
              <Translate contentKey="reactDemoApp.cliente.segundoApellido">Segundo Apellido</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.segundoApellido}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="reactDemoApp.cliente.telefono">Telefono</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.telefono}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="reactDemoApp.cliente.email">Email</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.email}</dd>
          <dt>
            <span id="direccion">
              <Translate contentKey="reactDemoApp.cliente.direccion">Direccion</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.direccion}</dd>
          <dt>
            <span id="referencia">
              <Translate contentKey="reactDemoApp.cliente.referencia">Referencia</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.referencia}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="reactDemoApp.cliente.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="reactDemoApp.cliente.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.longitude}</dd>
        </dl>
        <Button tag={Link} to="/cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cliente/${clienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClienteDetail;
