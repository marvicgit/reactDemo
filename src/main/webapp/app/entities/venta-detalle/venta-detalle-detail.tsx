import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './venta-detalle.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VentaDetalleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ventaDetalleEntity = useAppSelector(state => state.ventaDetalle.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ventaDetalleDetailsHeading">
          <Translate contentKey="reactDemoApp.ventaDetalle.detail.title">VentaDetalle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ventaDetalleEntity.id}</dd>
          <dt>
            <span id="talla">
              <Translate contentKey="reactDemoApp.ventaDetalle.talla">Talla</Translate>
            </span>
          </dt>
          <dd>{ventaDetalleEntity.talla}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="reactDemoApp.ventaDetalle.color">Color</Translate>
            </span>
          </dt>
          <dd>{ventaDetalleEntity.color}</dd>
          <dt>
            <span id="urlImagen">
              <Translate contentKey="reactDemoApp.ventaDetalle.urlImagen">Url Imagen</Translate>
            </span>
          </dt>
          <dd>{ventaDetalleEntity.urlImagen}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="reactDemoApp.ventaDetalle.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{ventaDetalleEntity.precio}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="reactDemoApp.ventaDetalle.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{ventaDetalleEntity.cantidad}</dd>
          <dt>
            <span id="subTotal">
              <Translate contentKey="reactDemoApp.ventaDetalle.subTotal">Sub Total</Translate>
            </span>
          </dt>
          <dd>{ventaDetalleEntity.subTotal}</dd>
          <dt>
            <Translate contentKey="reactDemoApp.ventaDetalle.venta">Venta</Translate>
          </dt>
          <dd>{ventaDetalleEntity.venta ? ventaDetalleEntity.venta.id : ''}</dd>
          <dt>
            <Translate contentKey="reactDemoApp.ventaDetalle.producto">Producto</Translate>
          </dt>
          <dd>{ventaDetalleEntity.producto ? ventaDetalleEntity.producto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/venta-detalle" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/venta-detalle/${ventaDetalleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VentaDetalleDetail;
