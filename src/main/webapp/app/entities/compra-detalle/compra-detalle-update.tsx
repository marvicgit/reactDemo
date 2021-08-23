import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICompra } from 'app/shared/model/compra.model';
import { getEntities as getCompras } from 'app/entities/compra/compra.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { getEntity, updateEntity, createEntity, reset } from './compra-detalle.reducer';
import { ICompraDetalle } from 'app/shared/model/compra-detalle.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompraDetalleUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const compras = useAppSelector(state => state.compra.entities);
  const productos = useAppSelector(state => state.producto.entities);
  const compraDetalleEntity = useAppSelector(state => state.compraDetalle.entity);
  const loading = useAppSelector(state => state.compraDetalle.loading);
  const updating = useAppSelector(state => state.compraDetalle.updating);
  const updateSuccess = useAppSelector(state => state.compraDetalle.updateSuccess);

  const handleClose = () => {
    props.history.push('/compra-detalle' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCompras({}));
    dispatch(getProductos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...compraDetalleEntity,
      ...values,
      compra: compras.find(it => it.id.toString() === values.compraId.toString()),
      producto: productos.find(it => it.id.toString() === values.productoId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...compraDetalleEntity,
          compraId: compraDetalleEntity?.compra?.id,
          productoId: compraDetalleEntity?.producto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reactDemoApp.compraDetalle.home.createOrEditLabel" data-cy="CompraDetalleCreateUpdateHeading">
            <Translate contentKey="reactDemoApp.compraDetalle.home.createOrEditLabel">Create or edit a CompraDetalle</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="compra-detalle-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('reactDemoApp.compraDetalle.cantidad')}
                id="compra-detalle-cantidad"
                name="cantidad"
                data-cy="cantidad"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('reactDemoApp.compraDetalle.precio')}
                id="compra-detalle-precio"
                name="precio"
                data-cy="precio"
                type="text"
              />
              <ValidatedField
                label={translate('reactDemoApp.compraDetalle.subTotal')}
                id="compra-detalle-subTotal"
                name="subTotal"
                data-cy="subTotal"
                type="text"
              />
              <ValidatedField
                id="compra-detalle-compra"
                name="compraId"
                data-cy="compra"
                label={translate('reactDemoApp.compraDetalle.compra')}
                type="select"
              >
                <option value="" key="0" />
                {compras
                  ? compras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="compra-detalle-producto"
                name="productoId"
                data-cy="producto"
                label={translate('reactDemoApp.compraDetalle.producto')}
                type="select"
              >
                <option value="" key="0" />
                {productos
                  ? productos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/compra-detalle" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CompraDetalleUpdate;
