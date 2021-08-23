import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './compra-detalle.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompraDetalleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const compraDetalleEntity = useAppSelector(state => state.compraDetalle.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="compraDetalleDetailsHeading">
          <Translate contentKey="reactDemoApp.compraDetalle.detail.title">CompraDetalle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{compraDetalleEntity.id}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="reactDemoApp.compraDetalle.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{compraDetalleEntity.cantidad}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="reactDemoApp.compraDetalle.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{compraDetalleEntity.precio}</dd>
          <dt>
            <span id="subTotal">
              <Translate contentKey="reactDemoApp.compraDetalle.subTotal">Sub Total</Translate>
            </span>
          </dt>
          <dd>{compraDetalleEntity.subTotal}</dd>
          <dt>
            <Translate contentKey="reactDemoApp.compraDetalle.compra">Compra</Translate>
          </dt>
          <dd>{compraDetalleEntity.compra ? compraDetalleEntity.compra.id : ''}</dd>
          <dt>
            <Translate contentKey="reactDemoApp.compraDetalle.producto">Producto</Translate>
          </dt>
          <dd>{compraDetalleEntity.producto ? compraDetalleEntity.producto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/compra-detalle" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/compra-detalle/${compraDetalleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompraDetalleDetail;
