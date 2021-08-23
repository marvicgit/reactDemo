import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './imagen.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ImagenDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const imagenEntity = useAppSelector(state => state.imagen.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="imagenDetailsHeading">
          <Translate contentKey="reactDemoApp.imagen.detail.title">Imagen</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{imagenEntity.id}</dd>
          <dt>
            <span id="indice">
              <Translate contentKey="reactDemoApp.imagen.indice">Indice</Translate>
            </span>
          </dt>
          <dd>{imagenEntity.indice}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="reactDemoApp.imagen.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{imagenEntity.nombre}</dd>
          <dt>
            <span id="peso">
              <Translate contentKey="reactDemoApp.imagen.peso">Peso</Translate>
            </span>
          </dt>
          <dd>{imagenEntity.peso}</dd>
          <dt>
            <span id="extension">
              <Translate contentKey="reactDemoApp.imagen.extension">Extension</Translate>
            </span>
          </dt>
          <dd>{imagenEntity.extension}</dd>
          <dt>
            <span id="imagen">
              <Translate contentKey="reactDemoApp.imagen.imagen">Imagen</Translate>
            </span>
          </dt>
          <dd>
            {imagenEntity.imagen ? (
              <div>
                {imagenEntity.imagenContentType ? (
                  <a onClick={openFile(imagenEntity.imagenContentType, imagenEntity.imagen)}>
                    <img src={`data:${imagenEntity.imagenContentType};base64,${imagenEntity.imagen}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {imagenEntity.imagenContentType}, {byteSize(imagenEntity.imagen)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="reactDemoApp.imagen.producto">Producto</Translate>
          </dt>
          <dd>{imagenEntity.producto ? imagenEntity.producto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/imagen" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/imagen/${imagenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImagenDetail;
