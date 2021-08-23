import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './variante.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VarianteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const varianteEntity = useAppSelector(state => state.variante.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="varianteDetailsHeading">
          <Translate contentKey="reactDemoApp.variante.detail.title">Variante</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{varianteEntity.id}</dd>
          <dt>
            <span id="sku">
              <Translate contentKey="reactDemoApp.variante.sku">Sku</Translate>
            </span>
          </dt>
          <dd>{varianteEntity.sku}</dd>
          <dt>
            <span id="talla">
              <Translate contentKey="reactDemoApp.variante.talla">Talla</Translate>
            </span>
          </dt>
          <dd>{varianteEntity.talla}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="reactDemoApp.variante.color">Color</Translate>
            </span>
          </dt>
          <dd>{varianteEntity.color}</dd>
          <dt>
            <span id="stock">
              <Translate contentKey="reactDemoApp.variante.stock">Stock</Translate>
            </span>
          </dt>
          <dd>{varianteEntity.stock}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="reactDemoApp.variante.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{varianteEntity.precio}</dd>
          <dt>
            <Translate contentKey="reactDemoApp.variante.producto">Producto</Translate>
          </dt>
          <dd>{varianteEntity.producto ? varianteEntity.producto.id : ''}</dd>
          <dt>
            <Translate contentKey="reactDemoApp.variante.imagen">Imagen</Translate>
          </dt>
          <dd>{varianteEntity.imagen ? varianteEntity.imagen.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/variante" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/variante/${varianteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VarianteDetail;
