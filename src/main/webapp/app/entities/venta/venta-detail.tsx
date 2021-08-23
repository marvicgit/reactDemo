import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './venta.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VentaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ventaEntity = useAppSelector(state => state.venta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ventaDetailsHeading">
          <Translate contentKey="reactDemoApp.venta.detail.title">Venta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.id}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="reactDemoApp.venta.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.fecha ? <TextFormat value={ventaEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="descuento">
              <Translate contentKey="reactDemoApp.venta.descuento">Descuento</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.descuento}</dd>
          <dt>
            <span id="total">
              <Translate contentKey="reactDemoApp.venta.total">Total</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.total}</dd>
          <dt>
            <Translate contentKey="reactDemoApp.venta.cliente">Cliente</Translate>
          </dt>
          <dd>{ventaEntity.cliente ? ventaEntity.cliente.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/venta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/venta/${ventaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VentaDetail;
