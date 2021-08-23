import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './compra.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompraDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const compraEntity = useAppSelector(state => state.compra.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="compraDetailsHeading">
          <Translate contentKey="reactDemoApp.compra.detail.title">Compra</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{compraEntity.id}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="reactDemoApp.compra.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>{compraEntity.fecha ? <TextFormat value={compraEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="total">
              <Translate contentKey="reactDemoApp.compra.total">Total</Translate>
            </span>
          </dt>
          <dd>{compraEntity.total}</dd>
        </dl>
        <Button tag={Link} to="/compra" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/compra/${compraEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompraDetail;
