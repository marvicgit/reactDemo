import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './producto.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoEntity = useAppSelector(state => state.producto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoDetailsHeading">
          <Translate contentKey="reactDemoApp.producto.detail.title">Producto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="reactDemoApp.producto.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{productoEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="reactDemoApp.producto.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{productoEntity.descripcion}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="reactDemoApp.producto.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{productoEntity.precio}</dd>
          <dt>
            <span id="stock">
              <Translate contentKey="reactDemoApp.producto.stock">Stock</Translate>
            </span>
          </dt>
          <dd>{productoEntity.stock}</dd>
          <dt>
            <span id="tallas">
              <Translate contentKey="reactDemoApp.producto.tallas">Tallas</Translate>
            </span>
          </dt>
          <dd>{productoEntity.tallas}</dd>
          <dt>
            <span id="colores">
              <Translate contentKey="reactDemoApp.producto.colores">Colores</Translate>
            </span>
          </dt>
          <dd>{productoEntity.colores}</dd>
          <dt>
            <span id="tags">
              <Translate contentKey="reactDemoApp.producto.tags">Tags</Translate>
            </span>
          </dt>
          <dd>{productoEntity.tags}</dd>
          <dt>
            <span id="venta">
              <Translate contentKey="reactDemoApp.producto.venta">Venta</Translate>
            </span>
          </dt>
          <dd>{productoEntity.venta ? 'true' : 'false'}</dd>
          <dt>
            <span id="descuento">
              <Translate contentKey="reactDemoApp.producto.descuento">Descuento</Translate>
            </span>
          </dt>
          <dd>{productoEntity.descuento}</dd>
          <dt>
            <span id="nuevo">
              <Translate contentKey="reactDemoApp.producto.nuevo">Nuevo</Translate>
            </span>
          </dt>
          <dd>{productoEntity.nuevo ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="reactDemoApp.producto.subCategoria">Sub Categoria</Translate>
          </dt>
          <dd>{productoEntity.subCategoria ? productoEntity.subCategoria.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto/${productoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoDetail;
